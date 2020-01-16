package com.ksh.heart.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ksh.heart.common.file.FileUtil;
import com.ksh.heart.common.utils.SecurityUtils;
import com.ksh.heart.common.util.OSSFactory;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.common.utils.ToolUtil;
import com.ksh.heart.system.dao.MeetingCloudMapper;
import com.ksh.heart.system.entity.meeting.Cloud;
import com.ksh.heart.system.service.MeetingCloudService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 会议文件
 */
@Service
public class MeetingCloudServiceImpl extends ServiceImpl<MeetingCloudMapper, Cloud> implements MeetingCloudService {

    /**
     * 上传会议文件
     */
    @Override
    public R saveCloud(MultipartFile[] multipartFiles, Cloud cloud) {
        String fileName = "";
        try {
            for(MultipartFile file : multipartFiles){
                fileName = cloud.getMeetingCode() + "/" + UUID.randomUUID().toString() + "." + ToolUtil.getFileSuffix(file.getOriginalFilename());
                String url = Objects.requireNonNull(OSSFactory.buildCloud()).upload(file.getBytes(), fileName);
                cloud.setUploadTime(new Date());
                cloud.setUserId(SecurityUtils.getUserId());
                cloud.setUrl(url);
                cloud.setName(file.getOriginalFilename());
                cloud.setMeetingCode("");
                if (!this.save(cloud)) {
                    Objects.requireNonNull(OSSFactory.buildCloud()).delete(fileName);
                }
            }
        } catch (Exception e) {
            Objects.requireNonNull(OSSFactory.buildCloud()).delete(fileName);
            e.printStackTrace();
            return R.fail("上传图片失败");
        }
        return R.ok(cloud.getId());
    }

    /**
     * 下载会议文件
     */
    @Override
    public void download(String meetingCode, HttpServletResponse response) {
        QueryWrapper<Cloud> qw = new QueryWrapper<>();
        qw.eq("meeting_code", meetingCode);
        List<Cloud> clouds = baseMapper.selectList(qw);
        List<String> fileNames = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        for(Cloud cloud : clouds){
            urls.add(Objects.requireNonNull(OSSFactory.buildCloud()).getDownloadUrl(cloud.getUrl()));
            fileNames.add(ToolUtil.substringUrl(cloud.getUrl()));
        }
        FileUtil.urlDownloadZip(fileNames, urls, meetingCode + ".zip", response);
    }
}
