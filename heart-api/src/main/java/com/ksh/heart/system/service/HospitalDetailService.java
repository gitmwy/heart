package com.ksh.heart.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.hospital.Hospital;

/**
 * 医院列表
 */
public interface HospitalDetailService extends IService<Hospital> {

    /**
     * 查询医院
     */
    R selectPageList(Hospital hospital);
}
