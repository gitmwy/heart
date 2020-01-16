package com.ksh.heart.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.sys.Sms;

/**
 * 手机短信
 */
public interface SysSmsService extends IService<Sms> {

    /**
     * 获取短信验证码
     */
    R sendSmsCode(String ip, String telephone);

    /**
     * 验证登陆验证码
     */
    Boolean checkPhoneCode(String code);
}
