package com.ksh.heart.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ksh.heart.common.constant.Constant;
import com.ksh.heart.common.factory.impl.ConstantFactory;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.common.utils.SecurityUtils;
import com.ksh.heart.common.utils.ToolUtil;
import com.ksh.heart.system.dao.MeetingAuditorMapper;
import com.ksh.heart.system.dao.MeetingCloudMapper;
import com.ksh.heart.system.dao.MeetingDetailMapper;
import com.ksh.heart.system.entity.meeting.Auditor;
import com.ksh.heart.system.entity.meeting.Cloud;
import com.ksh.heart.system.entity.meeting.Meeting;
import com.ksh.heart.system.entity.user.User;
import com.ksh.heart.system.service.MeetingDetailService;
import com.ksh.heart.system.wrapper.MeetingDetailWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 会议列表
 */
@Service
public class MeetingDetailServiceImpl extends ServiceImpl<MeetingDetailMapper, Meeting> implements MeetingDetailService {

    @Autowired
    private MeetingCloudMapper meetingCloudMapper;

    @Autowired
    private MeetingAuditorMapper meetingAuditorMapper;

    /**
     * 查询会议
     */
    @Override
    public R selectPageList(Meeting meeting) {
        List<Long> userIds;
        if(null != meeting.getApplicantId()){
            userIds = Collections.singletonList(meeting.getApplicantId());
        }else{
            userIds = ConstantFactory.me().getUserIds();
        }
        IPage<Meeting> page = baseMapper.selectPageList(new Page<Meeting>(meeting.getCurrentPage(), meeting.getPageSize()), meeting, userIds);
        return R.ok(new MeetingDetailWrapper(page).wrap());
    }

    /**
     * 会议详细信息
     */
    @Override
    public R getInfo(Long id) {
        Map<String, Object> map = baseMapper.getInfo(id);
        Assert.notNull(map, "该会议已被删除");

        map.put("meetingType", ConstantFactory.me().getDictsByCode("meeting_type", map.get("meetingType") + ""));
        map.put("meetingStatusName", ConstantFactory.me().getDictsByCode("meeting_status", map.get("meetingStatus") + ""));
        map.put("meetingVideo", ConstantFactory.me().getCloud(id, "01"));
        map.put("meetingImg", ConstantFactory.me().getCloud(id, "02"));
        map.put("laborImg", ConstantFactory.me().getCloud(id, "03"));
        map.put("invoiceImg", ConstantFactory.me().getCloud(id, "04"));
        map.put("laborCost", map.get("laborCost") + "");
        map.put("activityCost", map.get("activityCost") + "");
        return R.ok(map);
    }

    /**
     * 新增会议
     */
    @Override
    public R saveMeeting(Meeting meeting) {
        if (Constant.ADMIN.equals(SecurityUtils.getUserRole())) {
            return R.fail("管理人员禁止新增会议");
        }
        meeting.setCode(ConstantFactory.me().getSequence("M"));
        meeting.setApplicantTime(new Date());
        meeting.setApplicantId(SecurityUtils.getUserId());
        meeting.setMeetingStatus("01");
        meeting.setSource("小程序");
        this.save(meeting);
        return R.ok();
    }

    /**
     * 更新会议信息
     */
    @Override
    public R updateMeetingInfo(Meeting meeting) {
        Meeting detail = baseMapper.selectOne(new QueryWrapper<Meeting>().eq("id", meeting.getId()).eq("del_flag", 1));
        Assert.notNull(detail, "该会议已被删除");
        Asserts.check(!"03".equals(detail.getMeetingStatus()), "审核已通过不允许继续修改");

        meeting.setMeetingStatus("01");
        baseMapper.updateById(meeting);
        return R.ok();
    }

    /**
     * 更新会议现场
     */
    @Override
    public R updateMeetingScene(Meeting meeting) {
        Meeting detail = baseMapper.selectOne(new QueryWrapper<Meeting>().eq("id", meeting.getId()).eq("del_flag", 1));
        Assert.notNull(detail, "该会议已被删除");
        Asserts.check(!StringUtils.isNotBlank(detail.getAddress()), "已经提交不允许继续修改");

        //会议视频
        if (null != meeting.getVideoId()) {
            List<Cloud> clouds = ConstantFactory.me().getCloud(meeting.getId(), "01");
            clouds.forEach(cloud -> meetingCloudMapper.deleteById(cloud.getId()));
//            if(null != clouds && clouds.size() > 0){
//                for(Cloud cloud : clouds){
//                    meetingCloudMapper.deleteById(cloud.getId());
//                }
//            }
            Cloud cloud = meetingCloudMapper.selectById(meeting.getVideoId());
            cloud.setMeetingId(detail.getId());
            cloud.setMeetingCode(detail.getCode());
            meetingCloudMapper.updateById(cloud);
        }

        //现场照片
        if (null != meeting.getSceneIds()) {
            updateCloud(meeting.getId(), meeting.getSceneIds(), detail, "02");
        }
        baseMapper.updateById(meeting);
        return R.ok();
    }

    /**
     * 更新会议总结
     */
    @Override
    public R updateMeetingConclusion(Meeting meeting) {
        Meeting detail = baseMapper.selectOne(new QueryWrapper<Meeting>().eq("id", meeting.getId()).eq("del_flag", 1));
        Assert.notNull(detail, "该会议已被删除");
        Asserts.check(!StringUtils.isNotBlank(detail.getConclusion()), "已经提交不允许继续修改");

        //劳务报销单
        if (null != meeting.getLaborIds()) {
            updateCloud(meeting.getId(), meeting.getLaborIds(), detail, "03");
        }
        //费用发票
        if (null != meeting.getInvoiceIds()) {
            updateCloud(meeting.getId(), meeting.getInvoiceIds(), detail, "04");
        }
        baseMapper.updateById(meeting);
        return R.ok();
    }

    //更新会议文件
    private void updateCloud(Long meetingId, List<Long> ids, Meeting meeting, String type) {
        List<Cloud> oldClouds = ConstantFactory.me().getCloud(meetingId, type);
        oldClouds.forEach(cloud -> {
            if (!ToolUtil.contains(ids, cloud.getId())) {
                meetingCloudMapper.deleteById(cloud.getId());
            }
        });
//        if(null != oldClouds && oldClouds.size() > 0){
//            for(Cloud cloud : oldClouds){
//                if(!ToolUtil.contains(ids, cloud.getId())){
//                    meetingCloudMapper.deleteById(cloud.getId());
//                }
//            }
//        }
        List<Cloud> newClouds = meetingCloudMapper.selectBatchIds(ids);
        for (Cloud cloud : newClouds) {
            cloud.setMeetingId(meeting.getId());
            cloud.setMeetingCode(meeting.getCode());
            meetingCloudMapper.updateById(cloud);
        }
    }

    /**
     * 删除会议
     */
    public R deleteMeeting(Long[] ids) {
        List<Meeting> meetings = baseMapper.selectBatchIds(Arrays.asList(ids));
        for (Meeting meeting : meetings) {
            meeting.setDelFlag(0);
            baseMapper.updateById(meeting);
        }
        return R.ok();
    }

    /**
     * 审核会议
     */
    public R saveAuditor(Meeting meeting) {
        Meeting detail = baseMapper.selectOne(new QueryWrapper<Meeting>().eq("id", meeting.getId()).eq("del_flag", 1));
        Assert.notNull(detail, "该会议已被删除");

        detail.setAuditorId(SecurityUtils.getUserId());
        detail.setAuditorTime(new Date());
        detail.setMeetingStatus(meeting.getMeetingStatus());
        detail.setAuditorText(meeting.getAuditorText());
        detail.setAuditorChannel("heart");
        baseMapper.updateById(detail);

        //审核记录
        User user = ConstantFactory.me().getUserByAccount(SecurityUtils.getUsername());
        meetingAuditorMapper.insert(new Auditor()
                .setMeetingId(meeting.getId())
                .setAuditorStatus("03".equals(meeting.getMeetingStatus()) ? "通过" : "未通过")
                .setAuditorText(meeting.getAuditorText())
                .setAuditorTime(new Date())
                .setAuditorChannel("heart")
                .setAuditorName(user.getUsername())
        );
        return R.ok();
    }
}
