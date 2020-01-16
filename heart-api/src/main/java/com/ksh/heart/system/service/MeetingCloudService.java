package com.ksh.heart.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.system.entity.meeting.Cloud;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 会议文件
 */
public interface MeetingCloudService extends IService<Cloud> {

    /**
     * 上传会议文件
     */
    R saveCloud(MultipartFile[] multipartFiles, Cloud cloud);

    /**
     * 下载会议文件
     */
    void download(String meetingColde, HttpServletResponse response);
}
