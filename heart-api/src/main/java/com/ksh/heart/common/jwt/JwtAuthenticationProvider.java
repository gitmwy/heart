package com.ksh.heart.common.jwt;

import com.ksh.heart.common.exception.HeartException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 身份验证提供者
 */
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

    public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
    }

    @Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			throw new HeartException("认证失败");
		}

		// 覆写密码验证逻辑
		String presentedPassword = authentication.getCredentials().toString();
		if (!userDetails.getPassword().equals(presentedPassword)) {
			throw new HeartException("密码不正确");
		}
	}
}