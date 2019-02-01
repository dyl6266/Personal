package com.dy.domain;

import java.util.Date;

public class BoardVO {

	/* PK */
	private Integer idx;

	/* 제목 */
	private String title;

	/* 내용 */
	private String content;

	/* 작성자 */
	private String writer;

	/* 사용 여부 */
	private String useYn;

	/* 공지 여부 */
	private String noticeYn;

	/* 비밀글 여부 */
	private String secretYn;

	/* 등록일 */
	private Date insertTime;

	/* 수정일 */
	private Date updateTime;

	/* 조회수 */
	private int viewCnt;

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		if (idx != null && idx < 1) {
			this.idx = 1;
			return;
		}
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getNoticeYn() {
		return noticeYn;
	}

	public void setNoticeYn(String noticeYn) {
		this.noticeYn = noticeYn;
	}

	public String getSecretYn() {
		return secretYn;
	}

	public void setSecretYn(String secretYn) {
		this.secretYn = secretYn;
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

	public int getViewCnt() {
		return viewCnt;
	}

	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

}
