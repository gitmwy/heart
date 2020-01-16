package com.ksh.heart.system.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ksh.heart.common.base.entity.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登陆日志
 */
@Data
@NoArgsConstructor
@TableName("user_login_log")
public class LoginLog extends AbstractEntity {

    //主键id
    @TableId
    private Long id;
    // 日志名称
    @TableField(value = "log_name")
    private String logName;
    // 用户ID
    @TableField(value = "user_id")
    private Long userId;
    // 是否成功
    @TableField(value = "succeed")
    private String succeed;
    // 消息
    @TableField(value = "message")
    private String message;
    // ip
    @TableField(value = "ip_address")
    private String ipAddress;

    @TableField(exist = false)
    private String userName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}