package com.ksh.heart.system.entity.meeting;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ksh.heart.common.base.entity.RestEntity;
import com.ksh.heart.common.constant.RegexConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 会议列表
 */
@Data
@NoArgsConstructor
@ApiModel("会议")
@TableName("meeting_detail")
public class Meeting extends RestEntity {

    @TableId
    private Long id;

    @ApiModelProperty(value = "编号")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "会议时间（必填）：yyyy-MM-dd")
    @NotBlank(message = "会议时间不能为空")
    @Pattern(regexp = RegexConstant.DAY_REGEX, message = "会议时间格式不正确")
    @TableField(value = "meeting_time")
    private String meetingTime;

    @ApiModelProperty(value = "会议类型（必填）：1科室会 2学术会 3推广会", example = "1")
    @NotNull(message = "会议类型不能为空")
    @TableField(value = "meeting_type")
    private Integer meetingType;

    @ApiModelProperty(value = "医院ID（必填）", example = "1")
    @NotNull(message = "医院不能为空")
    @TableField(value = "hospital_id")
    private Long hospitalId;

    @ApiModelProperty(value = "讲者ID（必填）", example = "1")
    @NotNull(message = "讲者不能为空")
    @TableField(value = "speakers_id")
    private Long speakersId;

    @ApiModelProperty(value = "申请人ID", example = "1")
    @TableField(value = "applicant_id")
    private Long applicantId;

    @ApiModelProperty(value = "申请日期")
    @TableField(value = "applicant_time")
    private Date applicantTime;

    @ApiModelProperty(value = "审核人ID", example = "1")
    @TableField(value = "auditor_id")
    private Long auditorId;

    @ApiModelProperty(value = "劳务费用（必填）", example = "100.00")
    @NotNull(message = "劳务费用不能为空")
    @TableField(value = "labor_cost")
    private BigDecimal laborCost;

    @ApiModelProperty(value = "活动费用（必填）", example = "100.00")
    @NotNull(message = "活动费用不能为空")
    @TableField(value = "activity_cost")
    private BigDecimal activityCost;

    @ApiModelProperty(value = "预估人数（必填）", example = "100")
    @NotNull(message = "预估人数不能为空")
    @TableField(value = "pre_persons")
    private Integer prePersons;

    @ApiModelProperty(value = "实际人数", example = "200")
    @TableField(value = "real_persons")
    private Integer realPersons;

    @ApiModelProperty(value = "来源")
    @TableField(value = "source")
    private String source;

    @ApiModelProperty(value = "会议状态：01待审核 02待修改 03待开展 04开展中 05已结束")
    @TableField(value = "meeting_status")
    private String meetingStatus;

    @ApiModelProperty(value = "定位地址")
    @TableField(value = "address")
    private String address;

    @ApiModelProperty(value = "经度")
    @TableField(value = "longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField(value = "latitude")
    private String latitude;

    @ApiModelProperty(value = "主题（必填）", example = "1")
    @NotNull(message = "主题不能为空")
    @TableField(value = "topic_id")
    private Long topicId;

    @ApiModelProperty(value = "审核日期")
    @TableField(value = "auditor_time")
    private Date auditorTime;

    @ApiModelProperty(value = "审核内容")
    @TableField(value = "auditor_text")
    private String auditorText;

    @ApiModelProperty(value = "审核渠道")
    @TableField(value = "auditor_channel")
    private String auditorChannel;

    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    private Integer delFlag;

    @ApiModelProperty(value = "会议总结")
    @TableField(value = "conclusion")
    private String conclusion;

    @ApiModelProperty(value = "医院名")
    @TableField(exist = false)
    private String hospitalName;

    @ApiModelProperty(value = "医院地址")
    @TableField(exist = false)
    private String hospitalAddress;

    @ApiModelProperty(value = "讲者名")
    @TableField(exist = false)
    private String speakersName;

    @ApiModelProperty(value = "审核人名")
    @TableField(exist = false)
    private String auditorName;

    @ApiModelProperty(value = "主题名")
    @TableField(exist = false)
    private String topicName;

    @ApiModelProperty(value = "会议状态：01待审核 02待修改 03待开展 04开展中 05已结束")
    @TableField(exist = false)
    private String meetingStatusName;

    @ApiModelProperty(value = "会议视频", example = "1")
    @TableField(exist = false)
    private Long videoId;

    @ApiModelProperty(value = "现场图片", example = "[1,2,3]")
    @TableField(exist = false)
    private List<Long> sceneIds;

    @ApiModelProperty(value = "劳务报销单", example = "[1,2,3]")
    @TableField(exist = false)
    private List<Long> laborIds;

    @ApiModelProperty(value = "费用发票", example = "[1,2,3]")
    @TableField(exist = false)
    private List<Long> invoiceIds;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
