package com.ksh.heart.system.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ksh.heart.common.base.BaseWrapper;
import com.ksh.heart.common.utils.SpringContextHolder;
import com.ksh.heart.system.dao.MeetingDetailMapper;
import com.ksh.heart.system.entity.hospital.Hospital;
import com.ksh.heart.system.entity.meeting.Meeting;

import java.util.Map;

public class HospitalDetailWrapper extends BaseWrapper {

    private MeetingDetailMapper meetingDetailMapper = SpringContextHolder.getBean(MeetingDetailMapper.class);

    public HospitalDetailWrapper(IPage<Hospital> page) {
        super(page);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("meetingTimes", meetingDetailMapper.selectCount(new QueryWrapper<Meeting>().eq("hospital_id", map.get("id"))));
    }
}
