package com.ksh.heart.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ksh.heart.common.factory.impl.ConstantFactory;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.dao.HospitalDetailMapper;
import com.ksh.heart.system.entity.hospital.Hospital;
import com.ksh.heart.system.service.HospitalDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 医院列表
 */
@Service
public class HospitalDetailServiceImpl extends ServiceImpl<HospitalDetailMapper, Hospital> implements HospitalDetailService {

    /**
     * 查询医院
     */
    @Override
    public R selectPageList(Hospital hospital) {
        List<Long> userIds = ConstantFactory.me().getUserIds();
        IPage<Hospital> page = baseMapper.selectPageList(new Page<Hospital>(hospital.getCurrentPage(), hospital.getPageSize()), hospital, userIds);
        return R.ok(page);
    }
}
