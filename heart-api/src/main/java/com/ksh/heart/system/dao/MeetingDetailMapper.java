package com.ksh.heart.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ksh.heart.system.entity.meeting.Meeting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 会议列表
 */
@Repository
public interface MeetingDetailMapper extends BaseMapper<Meeting> {

    IPage<Meeting> selectPageList(Page page, @Param("meeting") Meeting meeting, @Param("applicantIds") List<Long> applicantIds);

    Map<String, Object> getInfo(Long meetingId);
}
