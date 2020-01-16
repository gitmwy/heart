package com.ksh.heart.common.jwt;

import com.ksh.heart.common.enumeration.RetEnum;
import com.ksh.heart.common.utils.JwtTokenUtils;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.common.utils.RedisUtil;
import com.ksh.heart.common.utils.RenderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出
 */
@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {


    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 获取请求携带的令牌
        String token = JwtTokenUtils.getToken(request);
        //禁止访问
        if(StringUtils.isBlank(token)){
            RenderUtil.renderJson(response, R.fail(RetEnum.FORBID.getRet(),RetEnum.FORBID.getMsg()));
            return;
        }
        if(StringUtils.isNotBlank(redisUtil.get(token))){
            redisUtil.delete(token);
        }
        RenderUtil.renderJson(response, R.fail(RetEnum.SUCCESS.getRet(),RetEnum.SUCCESS.getMsg()));
    }
}
