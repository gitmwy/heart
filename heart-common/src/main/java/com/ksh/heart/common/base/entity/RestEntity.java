package com.ksh.heart.common.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 分页Entity类
 */
public abstract class RestEntity extends AbstractEntity {

	@TableField(exist = false)
	private long currentPage = 1L;

	@TableField(exist = false)
	private long pageSize = 10L;

	public RestEntity() {
		super();
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}
}
