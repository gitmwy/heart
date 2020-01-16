package com.ksh.heart.common.jwt;

import com.ksh.heart.common.enumeration.RetEnum;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.common.utils.RenderUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录时返回给前端的数据
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        RenderUtil.renderJson(response, R.fail(RetEnum.LOGIN_EXPIRED.getRet(),RetEnum.LOGIN_EXPIRED.getMsg()));
    }
}
