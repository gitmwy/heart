package com.ksh.heart.system.controller.meeting;

import com.ksh.heart.common.annotion.SysLog;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.meeting.Cloud;
import com.ksh.heart.system.service.MeetingCloudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 会议文件
 */
@Api(value = "MeetingCloudController", tags = {"会议文件"})
@RestController
@RequestMapping("/meeting/cloud")
public class MeetingCloudController {

    @Autowired
    private MeetingCloudService meetingCloudService;

    @SysLog(value = "上传会议文件")
    @ApiOperation("上传会议文件")
    @PostMapping(value = "/upload")
    public R upload(
            @ApiParam(name = "file", value = "上传的文件", required = true) @RequestParam("file") MultipartFile[] multipartFiles,
            @Valid Cloud cloud
    ) {
        Assert.notEmpty(multipartFiles, "请选择要上传的资源");
        return meetingCloudService.saveCloud(multipartFiles, cloud);
    }

    @SysLog(value = "下载会议文件")
    @ApiOperation("下载会议文件")
    @GetMapping(value = "/download")
    public void download(
            @ApiParam(name = "meetingCode", value = "会议编号", required = true) @RequestParam(required = false) String meetingCode,
            HttpServletResponse response
    ) {
        Asserts.notBlank(meetingCode, "会议编号不能为空");
        meetingCloudService.download(meetingCode, response);
    }
}
