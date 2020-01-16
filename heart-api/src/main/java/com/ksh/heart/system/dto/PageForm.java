package com.ksh.heart.system.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 分页
 **/
@Data
@ApiModel("分页")
public class PageForm {

    @TableField(exist = false)
    private long currentPage = 1L;

    @TableField(exist = false)
    private long pageSize = 10L;
}
