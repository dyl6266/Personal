package com.dy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dy.common.dao.AbstractDAO;
import com.dy.domain.MemberVO;

@Repository("memberDAO")
public class MemberDAO extends AbstractDAO {

	private static final String namespace = "member";

	/**
	 * 회원 등록
	 * 
	 * @param member - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 */
	public int insertMember(MemberVO params) {
		return insert(namespace + ".insertMember", params);
	}

	/**
	 * 회원 상세 정보 조회
	 * 
	 * @param memberId - PK
	 * @return MemberVO - VO 클래스
	 */
	public MemberVO selectMemberDetail(String memberId) {
		return (MemberVO) selectOne(namespace + ".selectMemberDetail", memberId);
	}

	/**
	 * 회원 정보 수정
	 * 
	 * @param member - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 */
	public int updateMember(MemberVO params) {
		return update(namespace + ".updateMember", params);
	}

	/**
	 * 회원 정보 삭제 (사용 여부 변경)
	 * 
	 * @param member - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 */
	public int deleteMember(Integer idx) {
		return update(namespace + ".deleteMember", idx);
	}

	/**
	 * 회원 리스트 조회
	 * 
	 * @param params - VO 클래스
	 * @return List - 회원 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<MemberVO> selectMemberList(MemberVO params) {
		return (List<MemberVO>) selectList(namespace + ".selectMemberList", params);
	}

	/**
	 * 전체 회원수 조회
	 * 
	 * @param params - VO 클래스
	 * @return int - 회원수
	 */
	public int selectTotalCount(MemberVO params) {
		return (int) selectOne(namespace + ".selectTotalCount", params);
	}

}
