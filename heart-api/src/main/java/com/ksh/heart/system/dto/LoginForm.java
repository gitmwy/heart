package com.ksh.heart.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登陆
 **/
@Data
@ApiModel("登陆")
public class LoginForm {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "图文验证码")
    private String captcha;

    @ApiModelProperty(value = "手机验证码")
    private String phoneCode;

    @NotBlank(message = "登陆类型不能为空")
    @ApiModelProperty(value = "登录类型（01 帐号密码登录 02 短信验证码登录）")
    private String loginType;

    @ApiModelProperty(value = "小程序js_code")
    private String weChatCode;

    @ApiModelProperty(value = "密文")
    private String encryptedData;

    @ApiModelProperty(value = "偏移量")
    private String iv;
}
