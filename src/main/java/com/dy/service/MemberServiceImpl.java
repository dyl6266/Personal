package com.dy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.dy.dao.MemberDAO;
import com.dy.domain.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDAO;

	/**
	 * 회원 등록
	 * 
	 * @param params - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 */
	@Override
	public int registerMember(MemberVO params) {
		
		int queryCnt = 0;

		if ( !ObjectUtils.isEmpty(params) ) {
			
		}
		if ( params.getIdx() == null ) {
			memberDAO.insertMember(params);
		}
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 회원 상세 정보 조회
	 * 
	 * @param idx - PK
	 * @return MemberVO - VO 클래스
	 */
	@Override
	public MemberVO selectMemberDetail(Integer idx) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 회원 정보 삭제 (사용 여부 변경)
	 * 
	 * @param idxs - PK를 '|'로 구분한 스트링
	 * @return int - 쿼리 실행 횟수
	 */
	@Override
	public int deleteMember(String idxs) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 회원 리스트 조회
	 * 
	 * @param params - VO 클래스
	 * @return List - 회원 리스트
	 */
	@Override
	public List<MemberVO> selectMemberList(MemberVO params) {
		// TODO Auto-generated method stub
		return null;
	}

}
