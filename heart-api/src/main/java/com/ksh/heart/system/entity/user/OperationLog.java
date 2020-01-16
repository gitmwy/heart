package com.ksh.heart.system.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ksh.heart.common.base.entity.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 操作日志
 */
@Data
@NoArgsConstructor
@TableName("user_operation_log")
public class OperationLog extends AbstractEntity {

    //主键id
    @TableId
    private Long id;
    // 日志类型
    @TableField(value = "log_type")
    private String logType;
    // 日志名称
    @TableField(value = "log_name")
    private String logName;
    // 用户ID
    @TableField(value = "user_id")
    private Long userId;
    // 类名称
    @TableField(value = "class_name")
    private String className;
    // 方法名称
    @TableField(value = "method")
    private String method;
    // 是否成功
    @TableField(value = "succeed")
    private String succeed;
    // 备注
    @TableField(value = "message")
    private String message;
    // 执行时间
    @TableField(value = "execute_time")
    private String executeTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}