package com.ksh.heart.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.dto.LoginForm;
import com.ksh.heart.system.dto.PageForm;
import com.ksh.heart.system.entity.user.User;

/**
 * 个人中心
 */
public interface UserDetailService extends IService<User> {

    /**
     * 保存微信信息
     */
    Boolean saveWeChat(User user, LoginForm loginForm);

    /**
     * 用户信息
     */
    R selectInfo();

    /**
     * 员工管理
     */
    R selectUserList(PageForm page);

    /**
     * 员工管理详情
     */
    R selectUserInfo(Long id);

    /**
     * 讲者管理
     */
    R selectDoctorList(PageForm page);

    /**
     * 讲者管理详情
     */
    R selectDoctorInfo(Long id);

    /**
     * 医院管理
     */
    R selectHospitalList(PageForm page);

    /**
     * 医院管理详情
     */
    R selectHospitalInfo(Long id);
}
