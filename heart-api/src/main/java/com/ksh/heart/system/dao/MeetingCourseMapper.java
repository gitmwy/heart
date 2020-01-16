package com.ksh.heart.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ksh.heart.system.entity.meeting.Course;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 会议课件
 */
public interface MeetingCourseMapper extends BaseMapper<Course> {

    /**
     * 查询课件
     */
    IPage<Course> selectPageList(Page page, @Param("course") Course course);
}
