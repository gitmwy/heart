package com.ksh.heart.system.entity.hospital;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ksh.heart.common.base.entity.RestEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 医生列表
 */
@Data
@NoArgsConstructor
@ApiModel("医生")
@TableName("hospital_doctor")
public class Doctor extends RestEntity {

    @TableId
    private Long id;

    @ApiModelProperty(value = "医生名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "手机号")
    @TableField(value = "phone")
    private String phone;

    @ApiModelProperty(value = "医院ID", example = "1")
    @TableField(value = "hospital_id")
    private Long hospitalId;

    @ApiModelProperty(value = "科室", example = "1")
    @TableField(value = "department")
    private String department;

    @ApiModelProperty(value = "职位", example = "1")
    @TableField(value = "position")
    private String position;

    @ApiModelProperty(value = "职称", example = "1")
    @TableField(value = "title")
    private String title;

    @ApiModelProperty(value = "讲者标识 1是 0否", example = "1")
    @TableField(value = "flag")
    private Integer flag;

    @ApiModelProperty(value = "性别 1男 2女", example = "1")
    @TableField(value = "sex")
    private Integer sex;

    @ApiModelProperty(value = "性别")
    @TableField(exist = false)
    private String sexName;

    @ApiModelProperty(value = "所属医院")
    @TableField(exist = false)
    private String hospitalName;

    @ApiModelProperty(value = "所属区域")
    @TableField(exist = false)
    private String areaName;

    @ApiModelProperty(value = "所属省市")
    @TableField(exist = false)
    private String address;

    @ApiModelProperty(value = "费用统计")
    @TableField(exist = false)
    private List<Map<String, String>> meetings;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
