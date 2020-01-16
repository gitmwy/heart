package com.ksh.heart.system.controller.meeting;

import com.ksh.heart.common.annotion.SysLog;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.meeting.Meeting;
import com.ksh.heart.system.service.MeetingDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 会议管理
 */
@Api(value = "MeetingDetailController", tags = {"会议管理"})
@RequestMapping("/meeting/detail")
@RestController
public class MeetingDetailController {

    @Autowired
    private MeetingDetailService meetingDetailService;

    @ApiOperation("会议列表")
    @GetMapping(value = "/page/list")
    public R pageList(Meeting meeting) {
        return meetingDetailService.selectPageList(meeting);
    }

    @ApiOperation("会议详细信息")
    @GetMapping(value = "/info")
    public R info(@ApiParam(name = "id", value = "会议ID（必填）", example = "1") @RequestParam(required = false) Long id) {
        Assert.notNull(id, "会议ID不能为空");
        return meetingDetailService.getInfo(id);
    }

    @SysLog(value = "新增会议")
    @ApiOperation("新增会议")
    @PostMapping(value = "/add")
    public R add(@RequestBody @Valid Meeting meeting) {
        return meetingDetailService.saveMeeting(meeting);
    }


    @SysLog(value = "更新会议信息")
    @ApiOperation("更新会议信息")
    @PostMapping(value = "/updateInfo")
    public R updateInfo(@RequestBody Meeting meeting) {
        Assert.notNull(meeting.getId(), "会议ID不能为空");

        return meetingDetailService.updateMeetingInfo(meeting);
    }

    @SysLog(value = "更新会议现场")
    @ApiOperation("更新会议现场")
    @PostMapping(value = "/updateScene")
    public R updateScene(@RequestBody Meeting meeting) {
        Assert.notNull(meeting.getId(), "会议ID不能为空");
        Assert.notNull(meeting.getRealPersons(), "现场人数不能为空");
        Asserts.notBlank(meeting.getAddress(), "所在位置不能空");
        Assert.notNull(meeting.getVideoId(), "现场视频不能为空");
        Assert.notEmpty(meeting.getSceneIds(), "现场照片不能为空");

        return meetingDetailService.updateMeetingScene(meeting);
    }

    @SysLog(value = "更新会议总结")
    @ApiOperation("更新会议总结")
    @PostMapping(value = "/updateConclusion")
    public R updateConclusion(@RequestBody Meeting meeting) {
        Assert.notNull(meeting.getId(), "会议ID不能为空");
        Assert.notEmpty(meeting.getLaborIds(), "劳务报销单不能为空");
        Assert.notEmpty(meeting.getInvoiceIds(), "费用发票不能为空");
        Asserts.notBlank(meeting.getConclusion(), "会议总结不能为空");

        return meetingDetailService.updateMeetingConclusion(meeting);
    }

    @SysLog(value = "删除会议")
    @ApiOperation("删除会议")
    @PostMapping(value = "/del")
    public R del(@ApiParam(name = "ids", value = "会议ID（必填）", example = "[1,2,3]") @RequestBody Long[] ids) {
        Assert.notEmpty(ids, "会议ID不能为空");
        return meetingDetailService.deleteMeeting(ids);
    }

    @SysLog(value = "审核会议")
    @ApiOperation("审核会议")
    @PostMapping(value = "/auditor")
    public R auditor(@RequestBody Meeting meeting) {
        Assert.notNull(meeting.getId(), "会议ID不能为空");
        Asserts.notBlank(meeting.getMeetingStatus(), "审核状态不能为空");
        return meetingDetailService.saveAuditor(meeting);
    }
}
