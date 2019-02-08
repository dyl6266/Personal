package com.dy.service;

import java.util.HashMap;
import java.util.List;

import com.dy.domain.MemberVO;

public interface MemberService {

	/**
	 * 회원 등록
	 * 
	 * @param params - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 */
	public int registerMember(MemberVO params);

	/**
	 * 회원 상세 정보 조회
	 * 
	 * @param idx - PK
	 * @return MemberVO - VO 클래스
	 */
	public MemberVO selectMemberDetail(Integer idx);

	/**
	 * 회원 정보 삭제 (사용 여부 변경)
	 * 
	 * @param idxs - PK를 '|'로 구분한 스트링
	 * @return int - 쿼리 실행 횟수
	 */
	public int deleteMember(String idxs);

	/**
	 * 회원 리스트 조회
	 * 
	 * @param params - VO 클래스
	 * @return List - 회원 리스트
	 */
	public List<MemberVO> selectMemberList(MemberVO params);

}
