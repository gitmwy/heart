package com.ksh.heart.common.utils;

import com.ksh.heart.system.entity.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtTokenUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	//用户ID
	private static final String USER_ID = Claims.ID;
	//用户名称
	private static final String USER_NAME = Claims.SUBJECT;
	//用户角色名等级
	public static final String ROLE_LEVEL = "role_level";
	//用户角色名
	public static final String ROLE_NAME = "role_name";
	//用户区域ID
	public static final String AREA_ID = "area_id";
	//创建时间
	private static final String CREATED = "created";
	//密钥
    private static final String SECRET = "heart";
	// 过期时间为7天
	public static final long EXPIRE_REMEMBER = 7 * 24 * 60 * 60 * 1000;

    /**
	 * 生成令牌
	 */
	static String generateToken(User user) {
	    Map<String, Object> claims = new HashMap<>();
		claims.put(USER_ID, user.getId());
		claims.put(USER_NAME, user.getPhone());
	    claims.put(CREATED, new Date());
	    claims.put(ROLE_NAME, user.getRoleName());
	    claims.put(ROLE_LEVEL, user.getLevel());
	    claims.put(AREA_ID, user.getAreaId());
	    return generateToken(claims);
	}

	/**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_REMEMBER))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
    }

    /**
	 * 从令牌中获取用户Id
	 */
	public static Long getUserIdFromToken(String token) {
	    return Long.parseLong(getClaimsFromToken(token).getId());
	}

	/**
     * 从令牌中获取数据声明
     */
	public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
				.setSigningKey(SECRET)
				.parseClaimsJws(token)
				.getBody();
    }

	/**
	 * 刷新令牌
	 */
	public static String refreshToken(String token) {
		Claims claims = getClaimsFromToken(token);
		claims.put(CREATED, new Date());
		return generateToken(claims);
	}

	/**
     * 判断令牌是否过期
     */
    public static Boolean isTokenExpired(String token) {
		return getClaimsFromToken(token)
				.getExpiration()
				.before(new Date());
    }

    /**
     * 获取请求token
     */
    public static String getToken(HttpServletRequest request) {
    	String token = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if(token == null) {
        	token = request.getHeader("token");
        } else if(token.contains(tokenHead)){
        	token = token.substring(tokenHead.length());
        }
		return token;
    }
}