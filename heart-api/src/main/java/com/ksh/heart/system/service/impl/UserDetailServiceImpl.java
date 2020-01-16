package com.ksh.heart.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ksh.heart.common.constant.Constant;
import com.ksh.heart.common.factory.impl.ConstantFactory;
import com.ksh.heart.common.util.OSSFactory;
import com.ksh.heart.common.utils.R;
import com.ksh.heart.common.utils.SecurityUtils;
import com.ksh.heart.common.utils.ToolUtil;
import com.ksh.heart.system.dao.HospitalDetailMapper;
import com.ksh.heart.system.dao.HospitalDoctorMapper;
import com.ksh.heart.system.dao.MeetingDetailMapper;
import com.ksh.heart.system.dao.UserDetailMapper;
import com.ksh.heart.system.dto.LoginForm;
import com.ksh.heart.system.dto.PageForm;
import com.ksh.heart.system.entity.hospital.Doctor;
import com.ksh.heart.system.entity.hospital.Hospital;
import com.ksh.heart.system.entity.meeting.Meeting;
import com.ksh.heart.system.entity.user.User;
import com.ksh.heart.system.service.UserDetailService;
import com.ksh.heart.system.wrapper.HospitalDetailWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 个人中心
 */
@Service
public class UserDetailServiceImpl extends ServiceImpl<UserDetailMapper, User> implements UserDetailService {

    @Autowired
    private HospitalDoctorMapper hospitalDoctorMapper;

    @Autowired
    private HospitalDetailMapper hospitalDetailMapper;

    @Autowired
    private MeetingDetailMapper meetingDetailMapper;

    /**
     * 保存微信信息
     */
    @Override
    public Boolean saveWeChat(User user, LoginForm loginForm) {
        try {
            Map<String, String> map = OSSFactory.buildWeChat().JsCode2Session(loginForm.getWeChatCode(), loginForm.getEncryptedData(), loginForm.getIv());
            user.setOpenid(map.get("openid"));
            user.setNickname(map.get("nickName"));
            user.setAvatar(map.get("avatarUrl"));
            user.setBoundTime(new Date());
            baseMapper.updateById(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 用户信息
     */
    @Override
    public R selectInfo() {
        Map<String, Object> map = new HashMap<>();
        String userRole = SecurityUtils.getUserRole();
        User user = ConstantFactory.me().getUserByAccount(SecurityUtils.getUsername());
        //员工基本信息
        map.put("username", user.getUsername());
        map.put("jobCode", user.getJobCode());
        map.put("roleName", userRole);
        map.put("areaName", user.getAreaName());
        map.put("phone", user.getPhone());

        List<Long> userIds = ConstantFactory.me().getUserIds();
        //员工统计
        if (Constant.ADMIN.equals(userRole)) {
            map.put("userCount", baseMapper.selectUserCount(user.getLevel()));
        } else {
            map.put("userCount", 0);
        }
        //讲者统计
        map.put("doctorCount", hospitalDoctorMapper.selectDoctorCount(userIds));
        //医院统计
        map.put("hospitalCount", hospitalDetailMapper.selectCount(new QueryWrapper<Hospital>().in("managers_id", userIds)));
        //会议统计
        map.put("departmentCount", meetingDetailMapper.selectCount(
                new QueryWrapper<Meeting>()
                        .in("applicant_id", userIds)
                        .eq("meeting_type", 1)
        ));
        map.put("academicCount", meetingDetailMapper.selectCount(
                new QueryWrapper<Meeting>()
                        .in("applicant_id", userIds)
                        .eq("meeting_type", 2)
        ));
        map.put("generalizeCount", meetingDetailMapper.selectCount(
                new QueryWrapper<Meeting>()
                        .in("applicant_id", userIds)
                        .eq("meeting_type", 3)
        ));
        return R.ok(map);
    }

    /**
     * 员工管理
     */
    @Override
    public R selectUserList(PageForm page) {
        if (!Constant.ADMIN.equals(SecurityUtils.getUserRole())) {
            return R.fail("非管理人员禁用");
        }
        Long areaId = SecurityUtils.getUserAreaId();
        List<Long> areaIds = baseMapper.selectAllAreaByAreaId(areaId);
        areaIds.add(areaId);

        IPage<User> iPage = baseMapper.selectUserList(new Page<User>(page.getCurrentPage(), page.getPageSize()), SecurityUtils.getUserRoleLevel(), areaIds);
        return R.ok(iPage);
    }

    /**
     * 员工管理详情
     */
    @Override
    public R selectUserInfo(Long id) {
        User user = baseMapper.selectUserInfo(id);
        user.setRoleName(SecurityUtils.getUserRole());
        user.setSexName(ConstantFactory.me().getDictsByCode("sex", user.getSex()+""));
        return R.ok(user);
    }

    /**
     * 讲者管理
     */
    @Override
    public R selectDoctorList(PageForm page) {
        List<Long> userIds = ConstantFactory.me().getUserIds();
        IPage<Doctor> iPage = hospitalDoctorMapper.selectDoctorList(new Page<Doctor>(page.getCurrentPage(), page.getPageSize()), userIds);
        return R.ok(iPage);
    }

    /**
     * 讲者管理详情
     */
    @Override
    public R selectDoctorInfo(Long id) {
        List<Doctor> doctors = hospitalDoctorMapper.selectDoctorInfo(id);

        //费用统计
        List<Doctor> doctorsTemp = new ArrayList<>();
        for (Doctor doctor : doctors) {
            List<Meeting> meetings = meetingDetailMapper.selectList(
                    new QueryWrapper<Meeting>()
                            .eq("del_flag", 1)
                            .eq("speakers_id", doctor.getId())
                            .eq("meeting_status", "05"));
            List<Map<String, String>> list = new ArrayList<>();
            for (Meeting meeting : meetings) {
                Map<String, String> map = new HashMap<>();
                map.put("meetingType", ConstantFactory.me().getDictsByCode("meeting_type",meeting.getMeetingType()+""));
                map.put("meetingCode", meeting.getCode());
                map.put("total", ToolUtil.valueOf(meeting.getLaborCost().add(meeting.getActivityCost())));
                map.put("meetingStatus", ConstantFactory.me().getDictsByCode("meeting_status",meeting.getMeetingStatus()+""));
                list.add(map);
            }
            doctor.setMeetings(list);
            doctor.setSexName(ConstantFactory.me().getDictsByCode("sex", doctor.getSex()+""));
            doctorsTemp.add(doctor);
        }
        return R.ok(doctorsTemp);
    }

    /**
     * 医院管理
     */
    @Override
    public R selectHospitalList(PageForm page) {
        List<Long> userIds = ConstantFactory.me().getUserIds();
        IPage<Hospital> iPage = hospitalDetailMapper.selectPage(new Page<>(page.getCurrentPage(), page.getPageSize()), new QueryWrapper<Hospital>().in("managers_id", userIds));
        return R.ok(new HospitalDetailWrapper(iPage).wrap());
    }

    /**
     * 医院管理详情
     */
    @Override
    public R selectHospitalInfo(Long id) {
        return R.ok(hospitalDetailMapper.selectHospitalInfo(id));
    }
}
