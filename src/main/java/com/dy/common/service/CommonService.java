package com.dy.common.service;

import com.dy.domain.AttachVO;

public interface CommonService {

	/**
	 * 첨부 파일 상세 조회
	 * 
	 * @param idx - PK
	 * @return AttachVO - VO 클래스
	 */
	public AttachVO selectAttachDetail(Integer idx);

}
