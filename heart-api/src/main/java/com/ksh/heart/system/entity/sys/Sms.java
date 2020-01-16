package com.ksh.heart.system.entity.sys;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ksh.heart.common.base.entity.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 手机短信
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_sms")
public class Sms extends AbstractEntity {

    @TableId
    private Long id;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "code")
    private String code;

    @TableField(value = "text")
    private String text;

    @TableField(value = "type")
    private String type;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "ip")
    private String ip;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
