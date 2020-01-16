package com.ksh.heart.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登陆返回信息
 */
@Data
@Accessors(chain = true)
public class JwtUser {

    private Long id;

    private String openid;

    private String username;

    private String phone;

    private String roleName;

    private String token;
}
