package com.ksh.heart.common.jwt;

import com.ksh.heart.common.enumeration.RetEnum;
import com.ksh.heart.common.utils.JwtTokenUtils;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.common.utils.RedisUtil;
import com.ksh.heart.common.utils.RenderUtil;
import com.ksh.heart.common.utils.SecurityUtils;
import com.ksh.heart.common.utils.ToolUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 *  登录认证过滤器
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    /**
     *获取令牌进行认证
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Authentication authentication;
        // 获取请求携带的令牌
        String token = JwtTokenUtils.getToken(request);
        //用户未登录
        if(StringUtils.isBlank(token)){
            chain.doFilter(request, response);
            return;
        }
        //token过期
        if(JwtTokenUtils.isTokenExpired(token) || StringUtils.isBlank(redisUtil.get(token))){
            RenderUtil.renderJson(response, R.fail(RetEnum.LOGIN_EXPIRED.getRet(),RetEnum.LOGIN_EXPIRED.getMsg()));
            return;
        }
        //更新token过期时间
        redisUtil.expire(token, JwtTokenUtils.EXPIRE_REMEMBER);
        //通过token从新获取登陆认证
        if(SecurityUtils.getAuthentication() == null) {
            Claims claims = JwtTokenUtils.getClaimsFromToken(token);
            authentication = new JwtAuthenticatioToken(
                    claims.getSubject(),
                    null,
                    Collections.singleton(new SimpleGrantedAuthority(ToolUtil.valueOf(claims.get(JwtTokenUtils.ROLE_NAME)))),
                    token, Long.parseLong(claims.getId()));
        }else{
            authentication = SecurityUtils.getAuthentication();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}
