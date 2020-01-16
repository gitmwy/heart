package com.ksh.heart.common.sms;

import com.ksh.heart.common.utils.ToolUtil;

import java.util.Random;

/**
 * 云短信
 */
public abstract class SmsService {

    /** 云短信配置信息 */
    SmsConfig config;

    /**
     * 发送短信
     */
    public abstract SmsResponse sendSms(String telephone);

    /**
     * 获取短信详细信息
     */
    public abstract String querySendDetails(String bizId, String telephone);

    /**
     * 生成数字验证码
     */
    public static String getRandomNumCode(int number){
        StringBuilder codeNum = new StringBuilder();
        int [] numbers = {0,1,2,3,4,5,6,7,8,9};
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            //目的是产生足够随机的数，避免产生的数字重复率高的问题
            int next = random.nextInt(10000);
            codeNum.append(numbers[next % 10]);
        }
        return ToolUtil.valueOf(codeNum);
    }

    /**
     * 生成数字+字母的验证码
     */
    public static String getRandomCode(int number){
        StringBuilder codeNum = new StringBuilder();
        int [] code = new int[3];
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int num = random.nextInt(10) + 48;
            int uppercase = random.nextInt(26) + 65;
            int lowercase = random.nextInt(26) + 97;
            code[0] = num;
            code[1] = uppercase;
            code[2] = lowercase;
            codeNum.append((char) code[random.nextInt(3)]);
        }
        return ToolUtil.valueOf(codeNum);
    }
}
