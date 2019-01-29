package com.dy.domain;

import java.util.Date;

public class AttachVO {

	/* PK */
	private Integer idx;

	/* 게시글 PK */
	private Integer boardIdx;

	/* 원본 파일명 */
	private String originalName;

	/* 저장 파일명 */
	private String storedName;

	/* 파일 사이즈 */
	private long fileSize;

	/* 사용 여부 */
	private String useYn;

	/* 등록일 */
	private Date insertTime;

	/* 수정일 */
	private Date updateTime;

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}

	public Integer getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(Integer boardIdx) {
		this.boardIdx = boardIdx;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getStoredName() {
		return storedName;
	}

	public void setStoredName(String storedName) {
		this.storedName = storedName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
