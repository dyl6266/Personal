package com.dy.domain;

import java.util.Date;

import com.dy.common.domain.YesNo;

public class CommonVO {

	/* 사용 여부 */
	private YesNo useYn;

	/* 상태 */
	private YesNo status;

	/* 등록일 */
	private Date insertTime;

	/* 수정일 */
	private Date updateTime;

	public YesNo getUseYn() {
		return useYn;
	}

	public void setUseYn(YesNo useYn) {
		this.useYn = useYn;
	}

	public YesNo getStatus() {
		return status;
	}

	public void setStatus(YesNo status) {
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
