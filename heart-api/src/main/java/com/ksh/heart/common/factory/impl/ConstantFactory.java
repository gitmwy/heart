package com.ksh.heart.common.factory.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ksh.heart.common.constant.Constant;
import com.ksh.heart.common.factory.IConstantFactory;
import com.ksh.heart.common.utils.SecurityUtils;
import com.ksh.heart.common.utils.SpringContextHolder;
import com.ksh.heart.system.dao.MeetingCloudMapper;
import com.ksh.heart.system.dao.SysDictMapper;
import com.ksh.heart.system.dao.SysSequenceMapper;
import com.ksh.heart.system.dao.UserDetailMapper;
import com.ksh.heart.system.entity.meeting.Cloud;
import com.ksh.heart.system.entity.sys.Dict;
import com.ksh.heart.system.entity.user.User;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 常量的生产工厂
 */
@Component
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ConstantFactory implements IConstantFactory {

    private SysSequenceMapper sysSequenceMapper = SpringContextHolder.getBean(SysSequenceMapper.class);
    private UserDetailMapper userDetailMapper = SpringContextHolder.getBean(UserDetailMapper.class);
    private SysDictMapper sysDictMapper = SpringContextHolder.getBean(SysDictMapper.class);
    private MeetingCloudMapper meetingCloudMapper = SpringContextHolder.getBean(MeetingCloudMapper.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }

    /**
     * 获取用户
     */
    @Override
    public User getUserByAccount(String account){
        User user = userDetailMapper.getUserByAccount(account);
        if(null == user){
            return null;
        }

        //判断用户角色
        if(user.getLevel() < userDetailMapper.getRoleByLevel()){
            user.setRoleName(Constant.ADMIN);
        }else{
            user.setRoleName(Constant.REPRESENT);
        }
        return user;
    }

    /**
     * 根据父级字典code和获取字典名称
     */
    @Override
    public String getDictsByCode(String pcode, String code) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().eq("code", pcode);
        Dict dict = sysDictMapper.selectOne(queryWrapper);
        if (null != dict) {
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper = wrapper.eq("pid", dict.getId());
            List<Dict> dicts = sysDictMapper.selectList(wrapper);
            for (Dict item : dicts) {
                if (item.getCode() != null && item.getCode().equals(code)) {
                    return item.getName();
                }
            }
        }
        return "";
    }

    /**
     *获取会议相关视频、照片
     */
    @Override
    public List<Cloud> getCloud(Long meetingId, String type) {
        QueryWrapper<Cloud> qw = new QueryWrapper<>();
        qw.eq("meeting_id", meetingId);
        qw.eq("type", type);
        return meetingCloudMapper.selectList(qw);
    }

    /**
     * 获取会议新增编号
     */
    @Override
    public String getSequence(String type) {
        if("M".equals(type)){
            return sysSequenceMapper.getMeetingNum();
        }
        return "";
    }

    /**
     * 根据用户角色等级和所属区域获取其下属员工ID及本身ID的集合
     */
    @Override
    public List<Long> getUserIds() {
        List<Long> userIds;
        Long userId = SecurityUtils.getUserId();
        if (Constant.ADMIN.equals(SecurityUtils.getUserRole())) {
            Long areaId = SecurityUtils.getUserAreaId();
            List<Long> areaIds = userDetailMapper.selectAllAreaByAreaId(areaId);
            areaIds.add(areaId);

            userIds = userDetailMapper.selectUserTotal(SecurityUtils.getUserRoleLevel(),areaIds).stream().map(User::getId).collect(Collectors.toList());
            userIds.add(userId);
        }else{
            userIds = Collections.singletonList(userId);
        }
        return userIds;
    }
}
