package com.ksh.heart.common.constant;

/**
 * 正则常量
 */
public interface RegexConstant {

    //手机号格式校验
    String PHONE_REGEX = "^[1][3456789][0-9]{9}$";

    //邮箱格式校验
    String EMAIL_REGEX = "^([a-zA-Z0-9]+[-_.]?)+@[a-zA-Z0-9]+\\.[a-z]+$";

    //日期格式yyyy-MM-dd
    String DAY_REGEX = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
}
