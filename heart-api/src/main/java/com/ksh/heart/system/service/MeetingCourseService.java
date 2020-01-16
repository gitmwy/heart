package com.ksh.heart.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.meeting.Course;

/**
 * 会议课件
 */
public interface MeetingCourseService extends IService<Course> {

    /**
     * 查询课件
     */
    R selectPageList(Course course);
}
