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

	/**
	 * 첨부 파일 삭제 (사용 여부 변경)
	 * 
	 * @param idx
	 */
	public int deleteAttach(Integer idx);

	/**
	 * DB에 인증 키를 등록하고, 해당 인증 키를 이메일로 발송
	 * 
	 * @param memberId - 회원가입 아이디
	 * @return boolean - (true or false)
	 */
	public boolean registerAuthKeyAndSendMail(String memberId);

	/**
	 * 메일로 발송한 인증 키 유효성 체크
	 * 
	 * @param authKey  - 인증 키
	 * @param memberId - 회원가입 아이디
	 * @return boolean - (true or false)
	 */
	public boolean checkAuthKeyValidation(String authKey, String memberId);

}
