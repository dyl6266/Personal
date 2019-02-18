package com.dy.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.dy.common.dao.CommonDAO;
import com.dy.common.domain.YesNo;
import com.dy.common.utils.CommonUtils;
import com.dy.common.utils.MailUtils;
import com.dy.dao.MemberDAO;
import com.dy.domain.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private MemberDAO memberDAO;

	@Autowired
	private JavaMailSender mailSender;

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
				/* 회원가입 인증키 저장 & insert */
				String authKey = CommonUtils.getRandomNumber(8, YesNo.Y);
				params.setAuthKey(authKey);
				queryCnt = memberDAO.insertMember(params);

				/* 이메일 발송 */
				try {
					MailUtils sendMail = new MailUtils(mailSender);
					sendMail.setSubject("회원가입 인증 이메일");
					StringBuffer sb = new StringBuffer();
					sb.append("<h2[이메일 인증]</h2>").append("<p>아래 링크를 클릭하시면 인증이 완료됩니다.</p>")
							.append("<a href=\"http://127.0.0.1:8080/member/joinOK.do")
							.append("?memberId=" + params.getMemberId()).append("&authKey=" + authKey)
							.append("\"target=_blank\">인증 확인</a>");
					sendMail.setText(sb.toString());
					sendMail.setFrom("dyl6266", "관리자명");
					sendMail.setTo(params.getMemberId());
					sendMail.send();

				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

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
