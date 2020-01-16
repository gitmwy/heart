package com.ksh.heart.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ksh.heart.system.entity.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户管理
 */
@Repository
public interface UserDetailMapper extends BaseMapper<User> {

    /**
     * 查询用户的所有权限
     */
    int getRoleByLevel();

    /**
     * 查询用户
     */
    User getUserByAccount(String account);

    /**
     * 根据用户区域ID获取其下属所有区域ID
     */
    List<Long> selectAllAreaByAreaId(Long id);

    /**
     * 根据用户角色等级获取下属所有员工
     */
    List<User> selectUserTotal(@Param("level")Integer level, @Param("areaIds") List<Long> areaIds);

    /**
     * 员工管理
     */
    IPage<User> selectUserList(Page page, @Param("level") Integer level, @Param("areaIds") List<Long> areaIds);

    /**
     * 员工管理详情
     */
    User selectUserInfo(Long id);

    /**
     * 员工统计
     */
    int selectUserCount(Integer level);
}
