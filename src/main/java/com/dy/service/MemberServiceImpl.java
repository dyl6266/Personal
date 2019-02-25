package com.dy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dy.common.dao.CommonDAO;
import com.dy.dao.MemberDAO;
import com.dy.domain.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 회원 등록
	 * 
	 * @param params - VO 클래스
	 * @return boolean - true or false
	 */
	@Transactional
	@Override
	public boolean registerMember(MemberVO params) {

		boolean status = false;

		/* 등록의 경우 */
		if (params.getIdx() == null) {
			/* 패스워드 암호화 */
			String encodedPw = passwordEncoder.encode(params.getMemberPw());
			params.setMemberPw(encodedPw);

			/* 쿼리 실행 횟수 */
			int queryCnt = memberDAO.insertMember(params);
			if (queryCnt > 0) {
				/* 정상 처리된 인증 키 삭제 */
				int resultNum = commonDAO.changeStatusOfAuthKey(params.getAuthKey());
				if (resultNum > 0) {
					status = true;
				}
			}

			/* 수정의 경우 */
		} else {
			if (StringUtils.isEmpty(params.getMemberPw()) == false) {
				/* 패스워드 암호화 */
				String encodedPw = passwordEncoder.encode(params.getMemberPw());
				params.setMemberPw(encodedPw);
			}

			/* 쿼리 실행 횟수 */
			int queryCnt = memberDAO.updateMember(params);
			if (queryCnt > 0) {
				status = true;
			}
		}

		return status;
	}

	/**
	 * 회원 상세 정보 조회
	 * 
	 * @param memberId - PK
	 * @return MemberVO - VO 클래스
	 */
	@Override
	public MemberVO selectMemberDetail(String memberId) {
		return memberDAO.selectMemberDetail(memberId);
	}

	/**
	 * 회원 정보 삭제 (사용 여부 변경)
	 * 
	 * @param memberId - 회원 아이디
	 * @return boolean - true or false
	 */
	@Override
	public boolean deleteMember(String memberId) {

		boolean result = false;

		int queryCnt = memberDAO.deleteMember(memberId);
		if (queryCnt > 0) {
			result = true;
		}

		return result;
	}

	/**
	 * 회원 리스트 조회
	 * 
	 * @param params - VO 클래스
	 * @return List - 회원 리스트
	 */
	@Override
	public List<MemberVO> selectMemberList(MemberVO params) {

		List<MemberVO> memberList = null;

		int totalCount = memberDAO.selectTotalCount(params);
		if (totalCount > 1) {
			memberList = memberDAO.selectMemberList(params);
		}

		return memberList;
	}

}
