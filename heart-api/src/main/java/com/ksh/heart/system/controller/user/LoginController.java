package com.ksh.heart.system.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.ksh.heart.common.base.BaseController;
import com.ksh.heart.common.factory.impl.ConstantFactory;
import com.ksh.heart.common.jwt.JwtAuthenticatioToken;
import com.ksh.heart.common.log.LogManager;
import com.ksh.heart.common.log.factory.LogTaskFactory;
import com.ksh.heart.common.utils.JwtTokenUtils;
import com.ksh.heart.common.utils.PasswordUtils;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.common.utils.RedisUtil;
import com.ksh.heart.common.utils.SecurityUtils;
import com.ksh.heart.config.properties.HeartApiProperties;
import com.ksh.heart.system.dto.JwtUser;
import com.ksh.heart.system.dto.LoginForm;
import com.ksh.heart.system.entity.user.User;
import com.ksh.heart.system.service.SysSmsService;
import com.ksh.heart.system.service.UserDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.ksh.heart.common.support.HttpKit.getIp;

@Api(value = "LoginController", tags = {"登陆接口"})
@RestController
public class LoginController extends BaseController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HeartApiProperties heartApiProperties;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysSmsService sysSmsService;

    @Autowired
    private UserDetailService userDetailService;

    /**
     * 生成验证码
     */
    @ApiOperation("验证码生成")
    @GetMapping(value = "captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 生产验证码字符串并保存到session中
        String capText = defaultKaptcha.createText();
        HttpSession session = request.getSession();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);

        // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
        BufferedImage bi = defaultKaptcha.createImage(capText);
        try (ServletOutputStream out = response.getOutputStream()) {
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("获取短信验证码")
    @PostMapping(value = "/sendSmsCode")
    public Object sendSmsCode(@RequestBody String jsonParam) {
        JSONObject jsonObject = JSON.parseObject(jsonParam);
        String telephone = jsonObject.getString("telephone");

        Asserts.notBlank(telephone, "手机号不能为空");
        return sysSmsService.sendSmsCode(getIp(), telephone);
    }

    @ApiOperation("登陆")
    @PostMapping(value = "/login")
    public Object login(@RequestBody @Valid LoginForm loginForm) {
        //登录验证
        User user = ConstantFactory.me().getUserByAccount(loginForm.getUsername());
        if (null == user) {
            return R.fail("账号不存在");
        }
        if("01".equals(loginForm.getLoginType())){
            //账号密码登陆
            if (heartApiProperties.getCaptchaOpen()) {
                Object captcha = getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
                Assert.notNull(captcha, "验证码已失效，请点击图片重新刷新");
                if (!loginForm.getCaptcha().equals(captcha)) {
                    return R.fail("验证码不正确");
                }
            }
            if (!PasswordUtils.matches(user.getSalt(), loginForm.getPassword(), user.getPassword())) {
                return R.fail("密码不正确");
            }
        }else if("02".equals(loginForm.getLoginType())){
            //短信验证码登陆
            Assert.notNull(loginForm.getPhoneCode(), "验证码不能为空");
            if(!sysSmsService.checkPhoneCode(loginForm.getPhoneCode())){
                   return R.fail("验证码已失效，请重新获取");
            }
        }else{
            return R.fail("请选择正确的登陆方式");
        }
        if (0 == user.getStatus()) {
            return R.fail("账号已被锁定，请联系管理员");
        }

        //获取微信openid、头像、昵称等
        if(StringUtils.isNotBlank(loginForm.getWeChatCode())){
            if(!userDetailService.saveWeChat(user, loginForm)){
                return R.fail("授权失败");
            }
        }

        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtils.login(user, authenticationManager);
        if (StringUtils.isBlank(token.getToken())){
            return R.fail();
        }

        //返回信息
        JwtUser jwtUser = new JwtUser();
        jwtUser.setId(user.getId())
            .setOpenid(user.getOpenid())
            .setPhone(user.getPhone())
            .setRoleName(user.getRoleName())
            .setUsername(user.getUsername())
            .setToken(token.getToken());
        redisUtil.set(token.getToken(), JSON.toJSONString(jwtUser), JwtTokenUtils.EXPIRE_REMEMBER);

        LogManager.me().executeLog(LogTaskFactory.loginSuccessLog(JwtTokenUtils.getUserIdFromToken(token.getToken()), getIp()));
        return R.ok(jwtUser);
    }

    @ApiOperation("自动登陆")
    @GetMapping(value = "/autoLogin")
    public R autoLogin() {
        String token = JwtTokenUtils.getToken(getHttpServletRequest());
        JwtUser jwtUser = JSON.parseObject(redisUtil.get(token), JwtUser.class);
        return R.ok(jwtUser);
    }

    /**
     *退出
     */
    @ApiOperation("退出")
    @GetMapping(value = "/logout")
    public Object logout() {
        return R.ok("退出成功");
    }
}
