package com.dy.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
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

	/**
	 * 회원 등록
	 * 
	 * @param params - VO 클래스
	 * @return boolean - true or false
	 */
	@Override
	public boolean registerMember(MemberVO params) {

		boolean result = true;

		if (ObjectUtils.isEmpty(params)) {
			result = false;
		} else {
			int queryCnt = 0;

			/* 등록/수정 구분 */
			if (params.getIdx() == null) {
				queryCnt = memberDAO.insertMember(params);

				/* 시퀀스 테이블 업데이트 처리 */
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("tableName", String.valueOf("member"));
				hashMap.put("nextIdx", params.getIdx() + 1);
				commonDAO.updateNextIdx(hashMap);
			} else {
				queryCnt = memberDAO.updateMember(params);
			}

			/* 결과 체크 */
			if (queryCnt < 1) {
				result = false;
			}
		}

		return result;
	}

	/**
	 * 회원 상세 정보 조회
	 * 
	 * @param memberId - PK
	 * @return MemberVO - VO 클래스
	 */
	@Override
	public MemberVO selectMemberDetail(String memberId) {

		MemberVO member = null;

		if (!StringUtils.isEmpty(memberId)) {
			member = memberDAO.selectMemberDetail(memberId);
		}

		return member;
	}

	/**
	 * 회원 정보 삭제 (사용 여부 변경)
	 * 
	 * @param idxs - PK를 '|'로 구분한 스트링
	 * @return boolean - true or false
	 */
	@Override
	public boolean deleteMember(String idxs) {

		boolean result = true;

		if (StringUtils.isEmpty(idxs)) {
			result = false;
		} else {
			/* 쿼리 실행 횟수 */
			int queryCnt = 0;

			/* idxArray만큼 foreach 실행 */
			String[] idxArray = idxs.split("|");
			for (String idx : idxArray) {
				queryCnt += memberDAO.deleteMember(Integer.parseInt(idx));
			}

			/* 결과 체크 */
			if (queryCnt < 1) {
				result = false;
			}
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
