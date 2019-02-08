package com.dy.domain;

import java.util.Date;

import com.dy.common.domain.Status;

public class CommonVO {

	/* 상태 (Y : 사용 / N : 미사용) */
	private Status status;

	/* 등록일 */
	private Date insertTime;

	/* 수정일 */
	private Date updateTime;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
