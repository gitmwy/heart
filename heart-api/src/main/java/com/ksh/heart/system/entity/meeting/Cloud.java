package com.ksh.heart.system.entity.meeting;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ksh.heart.common.base.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 会议文件
 */
@Data
@NoArgsConstructor
@ApiModel("会议文件")
@TableName("meeting_cloud")
public class Cloud extends AbstractEntity {

    @TableId
    private Long id;

    @ApiModelProperty(value = "会议ID", example = "1")
    @TableField(value = "meeting_id")
    private Long meetingId;

    @ApiModelProperty(value = "会议编号")
    @NotBlank(message = "会议编号不能为空")
    @TableField(value = "meeting_code")
    private String  meetingCode;

    @ApiModelProperty(value = "文件类型（必填）：01视频 02会议照片 03劳务报销单 04费用发票")
    @NotBlank(message = "文件类型不能为空")
    @TableField(value = "type")
    private String type;

    @ApiModelProperty(value = "上传时间")
    @TableField(value = "upload_time")
    private Date uploadTime;

    @ApiModelProperty(value = "上传者ID", example = "1")
    @TableField(value = "user_id")
    private Long userId;

    @ApiModelProperty(value = "文件地址")
    @TableField(value = "url")
    private String url;

    @ApiModelProperty(value = "文件名")
    @TableField(value = "name")
    private String name;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
