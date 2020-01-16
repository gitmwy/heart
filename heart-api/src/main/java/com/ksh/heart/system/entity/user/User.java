package com.ksh.heart.system.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ksh.heart.common.base.entity.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户列表
 */
@Data
@NoArgsConstructor
@TableName("user_detail")
public class User extends AbstractEntity {

    @TableId
    private Long id;

    @TableField(value = "job_code")
    private String jobCode;

    @TableField(value = "openid")
    private String openid;

    @TableField(value = "avatar")
    private String avatar;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "username")
    private String username;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "password")
    private String password;

    @TableField(value = "salt")
    private String salt;

    @TableField(value = "area_id")
    private Long areaId;

    @TableField(value = "role_id")
    private Long roleId;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "sex")
    private Integer sex;

    @TableField(value = "bound_time")
    private Date boundTime;

    @TableField(exist = false)
    private Integer level;

    @TableField(exist = false)
    private String sexName;

    @TableField(exist = false)
    private String roleName;

    @TableField(exist = false)
    private String areaName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
