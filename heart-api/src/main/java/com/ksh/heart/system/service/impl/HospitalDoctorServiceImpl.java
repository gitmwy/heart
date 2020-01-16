package com.ksh.heart.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.common.utils.ToolUtil;
import com.ksh.heart.system.dao.HospitalDoctorMapper;
import com.ksh.heart.system.entity.hospital.Doctor;
import com.ksh.heart.system.service.HospitalDoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医生列表
 */
@Service
public class HospitalDoctorServiceImpl extends ServiceImpl<HospitalDoctorMapper, Doctor> implements HospitalDoctorService {

    /**
     * 查询医生
     */
    @Override
    public R selectPageList(Doctor doctor) {
        IPage<Doctor> page = baseMapper.selectPageList(new Page<Doctor>(doctor.getCurrentPage(), doctor.getPageSize()),doctor);
        return R.ok(page);
    }

    /**
     * 获取医院下讲者信息
     */
    @Override
    public R getDoctorByHospitalId(Long hospitalId) {
        QueryWrapper<Doctor> qw = new QueryWrapper<>();
        qw.eq("hospital_id", hospitalId);
        qw.eq("flag", 1);
        List<Doctor> doctors = baseMapper.selectList(qw);
        if(ToolUtil.isEmpty(doctors)){
            return R.fail("该医院下还未录入医生信息");
        }
        return R.ok(doctors);
    }
}
