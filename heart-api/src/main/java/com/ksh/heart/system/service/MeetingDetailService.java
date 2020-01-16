package com.ksh.heart.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.meeting.Meeting;

/**
 * 会议列表
 */
public interface MeetingDetailService extends IService<Meeting> {

    /**
     * 查询会议
     */
    R selectPageList(Meeting meeting);

    /**
     * 会议详细信息
     */
    R getInfo(Long id);

    /**
     *保存会议
     */
    R saveMeeting(Meeting meeting);

    /**
     *更新会议信息
     */
    R updateMeetingInfo(Meeting meeting);

    /**
     *更新会议现场
     */
    R updateMeetingScene(Meeting meeting);

    /**
     *更新会议总结
     */
    R updateMeetingConclusion(Meeting meeting);

    /**
     *删除会议
     */
    R deleteMeeting(Long[] ids);

    /**
     *审核会议
     */
    R saveAuditor(Meeting meeting);
}
