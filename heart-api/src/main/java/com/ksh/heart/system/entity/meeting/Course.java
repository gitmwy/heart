package com.ksh.heart.system.entity.meeting;

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
 * 会议课件
 */
@Data
@NoArgsConstructor
@ApiModel("会议课件")
@TableName("meeting_course")
public class Course extends RestEntity {

    @TableId
    private Long id;

    @ApiModelProperty(value = "课件名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "url地址")
    @TableField(value = "url")
    private String url;

    @ApiModelProperty(value = "文件大小")
    @TableField(value = "size")
    private String size;

    @ApiModelProperty(value = "下载次数")
    @TableField(value = "times")
    private String times;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
