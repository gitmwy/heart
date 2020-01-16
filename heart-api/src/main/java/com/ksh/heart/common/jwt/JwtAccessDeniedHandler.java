package com.ksh.heart.common.jwt;

import com.ksh.heart.common.enumeration.RetEnum;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.common.utils.RenderUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拒绝访问
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        RenderUtil.renderJson(response, R.fail(RetEnum.FORBID.getRet(),RetEnum.FORBID.getMsg()));
    }
}
