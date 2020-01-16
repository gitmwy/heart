package com.ksh.heart.common.wechat;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 微信配置
 */
@Data
public class WeChatConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    //小程序appId
    @NotBlank(message="小程序appId")
    private String appId;
    //小程序密钥
    @NotBlank(message="小程序密钥")
    private String secret;
    //微信请求地址
    @NotBlank(message="微信请求地址")
    private String jsCode2Session;
    //授权类型
    @NotBlank(message="授权类型")
    private String grant_type;
}
