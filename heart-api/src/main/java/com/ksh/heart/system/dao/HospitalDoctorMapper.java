package com.ksh.heart.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ksh.heart.system.entity.hospital.Doctor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 医生列表
 */
@Repository
public interface HospitalDoctorMapper extends BaseMapper<Doctor> {

    IPage<Doctor> selectPageList(Page page, @Param("doctor") Doctor doctor);

    /**
     *讲者管理
     */
    IPage<Doctor> selectDoctorList(Page page, @Param("managersIds") List<Long> managersIds);

    /**
     *讲者管理详情
     */
    List<Doctor> selectDoctorInfo(Long id);

    /**
     *讲者统计
     */
    int selectDoctorCount(@Param("managersIds") List<Long> managersIds);
}
