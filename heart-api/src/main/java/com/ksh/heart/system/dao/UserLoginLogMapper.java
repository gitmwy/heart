package com.ksh.heart.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ksh.heart.system.entity.user.LoginLog;
import org.apache.ibatis.annotations.Param;

/**
 * 登陆日志
 */
public interface UserLoginLogMapper extends BaseMapper<LoginLog> {

    IPage<LoginLog> selectPageList(Page page, @Param("loginLog") LoginLog loginLog);
}
