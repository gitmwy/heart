package com.ksh.heart.system.wrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ksh.heart.common.base.BaseWrapper;
import com.ksh.heart.common.factory.impl.ConstantFactory;
import com.ksh.heart.system.entity.meeting.Meeting;

import java.util.Map;

public class MeetingDetailWrapper extends BaseWrapper {

    public MeetingDetailWrapper(IPage<Meeting> page) {
        super(page);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("meetingStatusName", ConstantFactory.me().getDictsByCode("meeting_status",map.get("meetingStatus")+""));
    }
}
