package com.ksh.heart.system.controller.meeting;

import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.meeting.Course;
import com.ksh.heart.system.service.MeetingCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会议课件
 */
@Api(value = "MeetingCourseController", tags = {"会议课件"})
@RestController
@RequestMapping("/meeting/course")
public class MeetingCourseController {

    @Autowired
    private MeetingCourseService meetingCourseService;

    @ApiOperation("课件列表")
    @GetMapping(value = "/page/list")
    public R pageList(Course course) {
        return meetingCourseService.selectPageList(course);
    }
}
