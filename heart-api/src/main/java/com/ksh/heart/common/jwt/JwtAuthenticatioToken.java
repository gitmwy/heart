package com.ksh.heart.common.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义令牌对象
 */
public class JwtAuthenticatioToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String token;

    public JwtAuthenticatioToken(Object principal, Object credentials){
        super(principal, credentials);
    }
    
    public JwtAuthenticatioToken(Object principal, Object credentials, String token){
    	super(principal, credentials);
    	this.token = token;
    }

    JwtAuthenticatioToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String token, Long id) {
    	super(principal, credentials, authorities);
    	this.token = token;
    	this.id = id;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
