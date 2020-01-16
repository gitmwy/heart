package com.ksh.heart.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.hospital.Doctor;

/**
 * 医生列表
 */
public interface HospitalDoctorService extends IService<Doctor> {

    /**
     * 查询医生
     */
    R selectPageList(Doctor doctor);

    /**
     * 获取医院下讲者信息
     */
    R getDoctorByHospitalId(Long hospitalId);
}
