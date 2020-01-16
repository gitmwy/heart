package com.ksh.heart.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ksh.heart.system.entity.hospital.Hospital;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 医院列表
 */
@Repository
public interface HospitalDetailMapper extends BaseMapper<Hospital> {

    IPage<Hospital> selectPageList(Page page, @Param("hospital") Hospital hospital, @Param("managersIds") List<Long> managersIds);

    /**
     * 医院管理详情
     */
    Hospital selectHospitalInfo(Long id);
}
