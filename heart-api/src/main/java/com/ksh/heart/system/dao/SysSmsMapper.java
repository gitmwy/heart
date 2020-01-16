package com.ksh.heart.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ksh.heart.system.entity.sys.Sms;
import org.apache.ibatis.annotations.Param;

/**
 * 手机短信
 */
public interface SysSmsMapper extends BaseMapper<Sms> {

    /**
     * 60秒发送一个验证码
     */
    int selectMinutesCode(@Param("phone")String phone, @Param("type")String  type);

    /**
     * 每天验证码次数
     */
    int selectDayCode(@Param("phone")String phone, @Param("type")String  type);

    /**
     * 每小时验证码次数
     */
    int selectHoursCode(@Param("phone")String phone, @Param("type")String  type);

    /**
     * 每半个时验证码次数
     */
    int selectHalfHoursCode(@Param("phone")String phone, @Param("type")String  type);

    /**
     * 验证码是否有效
     */
    Sms selectVerifyCode(@Param("code")String code, @Param("type")String  type);
}
