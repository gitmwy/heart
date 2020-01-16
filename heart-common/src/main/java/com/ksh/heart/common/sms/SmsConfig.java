package com.ksh.heart.common.sms;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 短信配置
 */
@Data
public class SmsConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    //短信API产品名称
    @NotBlank(message="短信API产品名称不能为空")
    private String product;
    //短信API产品域名
    @NotBlank(message="短信API产品域名不能为空")
    private String domain;
    //阿里云AccessKeyId
    @NotBlank(message="阿里云AccessKeyId不能为空")
    private String accessKeyId;
    //阿里云AccessKeySecret
    @NotBlank(message="阿里云AccessKeySecret不能为空")
    private String accessKeySecret;
    //短信签名
    @NotBlank(message="短信签名不能为空")
    private String signName;
    //短信模板
    @NotBlank(message="短信模板不能为空")
    private String templateCode;
    //阿里云regionId
    private String regionId = "cn-hangzhou";
}
