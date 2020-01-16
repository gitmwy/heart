package com.ksh.heart.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ksh.heart.system.entity.user.OperationLog;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志
 */
public interface UserOperationLogMapper extends BaseMapper<OperationLog> {

    IPage<OperationLog> selectPageList(Page page, @Param("operationLog") OperationLog operationLog);
}
