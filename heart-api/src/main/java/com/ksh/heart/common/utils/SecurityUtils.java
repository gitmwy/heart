package com.ksh.heart.common.utils;

import com.ksh.heart.common.jwt.JwtAuthenticatioToken;
import com.ksh.heart.system.entity.user.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security相关操作
 */
public class SecurityUtils {

	/**
	 * 系统登录认证
	 */
	public static JwtAuthenticatioToken login(User user, AuthenticationManager authenticationManager) {
		JwtAuthenticatioToken token = new JwtAuthenticatioToken(user.getPhone(), user.getPassword());
        // 执行登录认证过程
	    Authentication authentication = authenticationManager.authenticate(token);
	    // 认证成功存储认证信息到上下文
	    SecurityContextHolder.getContext().setAuthentication(authentication);
		// 生成令牌并返回给客户端
		token.setId(user.getId());
	    token.setToken(JwtTokenUtils.generateToken(user));
		return token;
	}

	/**
	 * 获取用户名
	 */
	public static String getUsername() {
		Authentication authentication = getAuthentication();
		if(null != authentication) {
			Object principal = authentication.getPrincipal();
			return ToolUtil.valueOf(principal);
		}
		return "";
	}

	/**
	 * 获取用户ID
	 */
	public static Long getUserId() {
		Authentication authentication = getAuthentication();
		if(null != authentication) {
			if(authentication instanceof JwtAuthenticatioToken){
				return ((JwtAuthenticatioToken)authentication).getId();
			}
		}
		return null;
	}

	/**
	 * 获取用户角色
	 */
	public static String getUserRole() {
		Claims claims = JwtTokenUtils.getClaimsFromToken(SecurityUtils.getUserToken());
		if(null != claims){
			return ToolUtil.valueOf(claims.get(JwtTokenUtils.ROLE_NAME));
		}
		return "";
	}

	/**
	 * 获取用户角色等级
	 */
	public static Integer getUserRoleLevel() {
		Claims claims = JwtTokenUtils.getClaimsFromToken(SecurityUtils.getUserToken());
		if(null != claims){
			return Integer.parseInt(ToolUtil.valueOf(claims.get(JwtTokenUtils.ROLE_LEVEL)));
		}
		return null;
	}

	/**
	 * 获取用户区域ID
	 */
	public static Long getUserAreaId() {
		Claims claims = JwtTokenUtils.getClaimsFromToken(SecurityUtils.getUserToken());
		if(null != claims){
			return Long.parseLong(ToolUtil.valueOf(claims.get(JwtTokenUtils.AREA_ID)));
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public static String getUserToken() {
		Authentication authentication = getAuthentication();
		if(null != authentication) {
			if(authentication instanceof JwtAuthenticatioToken){
				return ((JwtAuthenticatioToken)authentication).getToken();
			}
		}
		return null;
	}

	/**
	 * 获取当前登录信息
	 */
	public static Authentication getAuthentication() {
		if(null == SecurityContextHolder.getContext()) {
			return null;
		}
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
