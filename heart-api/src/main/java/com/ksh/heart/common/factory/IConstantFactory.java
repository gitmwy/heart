package com.ksh.heart.common.factory;

import com.ksh.heart.system.entity.meeting.Cloud;
import com.ksh.heart.system.entity.user.User;

import java.util.List;

public interface IConstantFactory {

    //获取用户
    User getUserByAccount(String account);

    //根据父级字典code和获取字典名称
    String getDictsByCode(String pcode, String code);

     //获取会议相关视频、照片
    List<Cloud> getCloud(Long meetingId, String type);

    //获取会议新增编号
    String getSequence(String type);

    //根据用户角色等级和所属区域获取其下属员工ID及本身ID的集合
    List<Long> getUserIds();
}
