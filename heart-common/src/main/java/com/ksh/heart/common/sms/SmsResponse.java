package com.ksh.heart.common.sms;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 短信信息
 */
@Data
@Accessors(chain = true)
public class SmsResponse {

    //验证码
    private String code;
    //短信内容
    private String content;
}
