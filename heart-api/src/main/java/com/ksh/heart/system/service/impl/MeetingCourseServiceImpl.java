package com.ksh.heart.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.dao.MeetingCourseMapper;
import com.ksh.heart.system.entity.meeting.Course;
import com.ksh.heart.system.service.MeetingCourseService;
import org.springframework.stereotype.Service;

/**
 * 会议课件
 */
@Service
public class MeetingCourseServiceImpl extends ServiceImpl<MeetingCourseMapper, Course> implements MeetingCourseService {

    /**
     * 查询课件
     */
    @Override
    public R selectPageList(Course course) {
        IPage<Course> page = baseMapper.selectPageList(new Page<Course>(course.getCurrentPage(), course.getPageSize()), course);
        return R.ok(page);
    }
}
