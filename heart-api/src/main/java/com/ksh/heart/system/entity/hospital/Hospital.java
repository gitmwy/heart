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

/**
 * 医院列表
 */
@Data
@NoArgsConstructor
@ApiModel("医院")
@TableName("hospital_detail")
public class Hospital extends RestEntity {

    @TableId
    private Long id;

    @ApiModelProperty(value = "编号")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "医院名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "地址")
    @TableField(value = "address")
    private String address;

    @ApiModelProperty(value = "级别")
    @TableField(value = "level")
    private String level;

    @ApiModelProperty(value = "所在省")
    @TableField(value = "province_name")
    private String provinceName;

    @ApiModelProperty(value = "省行政编号")
    @TableField(value = "province_code")
    private String provinceCode;

    @ApiModelProperty(value = "所在市")
    @TableField(value = "city_name")
    private String cityName;

    @ApiModelProperty(value = "市行政编号")
    @TableField(value = "city_code")
    private String cityCode;

    @ApiModelProperty(value = "所在区/县")
    @TableField(value = "county_name")
    private String countyName;

    @ApiModelProperty(value = "区/县行政编号")
    @TableField(value = "county_code")
    private String countyCode;

    @ApiModelProperty(value = "负责人ID", example = "1")
    @TableField(value = "managers_id")
    private Long managersId;

    @ApiModelProperty(value = "区域ID", example = "1")
    @TableField(value = "area_id")
    private Long areaId;

    @ApiModelProperty(value = "所在区域")
    @TableField(exist = false)
    private String areaName;

    @ApiModelProperty(value = "开场次数")
    @TableField(exist = false)
    private String meetingTimes;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
