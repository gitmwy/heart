package com.ksh.heart.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ksh.heart.common.constant.Constant;
import com.ksh.heart.common.factory.impl.ConstantFactory;
import com.ksh.heart.common.sms.SmsResponse;
import com.ksh.heart.common.util.OSSFactory;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.dao.SysSmsMapper;
import com.ksh.heart.system.entity.sys.Sms;
import com.ksh.heart.system.service.SysSmsService;
import org.springframework.stereotype.Service;

/**
 * 手机短信
 */
@Service
public class SysSmsServiceImpl extends ServiceImpl<SysSmsMapper, Sms> implements SysSmsService {

    /**
     * 获取短信验证码
     */
    @Override
    public R sendSmsCode(String ip, String telephone) {
        if(baseMapper.selectMinutesCode(telephone, Constant.LOGIN_VERIFY_CODE) > 0){
            return R.fail("请等待60秒再获取");
        }

        //校验手机号是否已超过发送频率限制
        if(!checkSmsCodeLimit(telephone)){
            this.save(new Sms()
                    .setPhone(telephone)
                    .setIp(ip)
                    .setType(Constant.LOGIN_VERIFY_CODE)
                    .setText("该手机号已超过发送频率限制")
            );
            return R.fail("该手机号已超过发送频率限制");
        }

        //获取验证码
        SmsResponse smsResponse = OSSFactory.buildSms().sendSms(telephone);
        if (null == smsResponse) {
            return R.fail("获取验证码失败");
        }
        this.save(new Sms()
                .setPhone(telephone)
                .setCode(smsResponse.getCode())
                .setStatus(1)
                .setIp(ip)
                .setType(Constant.LOGIN_VERIFY_CODE)
                .setText(smsResponse.getContent())
        );
        return R.ok(smsResponse.getCode());
    }

    /**
     * 验证登陆验证码
     */
    @Override
    public Boolean checkPhoneCode(String code) {
        Sms sms = baseMapper.selectVerifyCode(code, Constant.LOGIN_VERIFY_CODE);
        if(null == sms){
            return false;
        }
        sms.setStatus(0);
        baseMapper.updateById(sms);
        return true;
    }

    /**
     * 校验手机号是否已超过发送频率限制
     */
    private Boolean checkSmsCodeLimit(String phone) {
        int dayTimes = Integer.parseInt(ConstantFactory.me().getDictsByCode("sms_code_limit", "day"));
        int hoursTimes = Integer.parseInt(ConstantFactory.me().getDictsByCode("sms_code_limit", "hours"));
        int halfHoursTimes = Integer.parseInt(ConstantFactory.me().getDictsByCode("sms_code_limit", "halfHours"));
        return baseMapper.selectDayCode(phone, Constant.LOGIN_VERIFY_CODE) <= dayTimes
                && baseMapper.selectHoursCode(phone, Constant.LOGIN_VERIFY_CODE) <= hoursTimes
                && baseMapper.selectHalfHoursCode(phone, Constant.LOGIN_VERIFY_CODE) <= halfHoursTimes;
    }
}
