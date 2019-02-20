package com.dy.common.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dy.common.dao.CommonDAO;
import com.dy.common.domain.YesNo;
import com.dy.common.utils.CommonUtils;
import com.dy.common.utils.MailUtils;
import com.dy.domain.AttachVO;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private JavaMailSender mailSender;

	/**
	 * 첨부 파일 상세 조회
	 * 
	 * @param idx - PK
	 * @return AttachVO - VO 클래스
	 */
	@Override
	public AttachVO selectAttachDetail(Integer idx) {
		return commonDAO.selectAttachDetail(idx);
	}

	/**
	 * 첨부 파일 삭제 (사용 여부 변경)
	 * 
	 * @param idx
	 */
	@Override
	public int deleteAttach(Integer idx) {
		return commonDAO.deleteAttach(idx);
	}

	/**
	 * DB에 인증번호를 등록하고, 해당 인증번호를 이메일로 발송
	 * 
	 * @param memberId - 회원가입 아이디
	 * @return boolean - (true or false)
	 */
	@Override
	public boolean registerAuthKeyAndSendMail(String memberId) {

		boolean status = false;

		if (StringUtils.isEmpty(memberId) == false) {
			/* 인증 키 */
			String authKey = CommonUtils.getRandomNumber(8, YesNo.N);

			HashMap<String, Object> params = new HashMap<>();
			params.put("memberId", memberId);
			params.put("authKey", authKey);

			/* 인증 키 저장 */
			int queryCnt = commonDAO.insertAuthkey(params);
			if (queryCnt > 0) {
				/* 이메일 발송 */
				try {
					MailUtils sendMail = new MailUtils(mailSender);
					/* 내용 */
					StringBuffer sb = new StringBuffer();
					sb.append("<h2>본인 확인을 위한 인증번호입니다.</h2>")
					.append("<span>계정 : </span><b>" + memberId + "</b><br />")
					.append("<span>인증번호 : </span><b>" + authKey + "</b>");

					sendMail.setSubject("회원가입 인증번호");
					sendMail.setText(sb.toString());
					sendMail.setFrom("congsong2436@gmail.com", "도영");
					sendMail.setTo(memberId);
					sendMail.send();

				} catch (MessagingException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				/* 상태 변경 */
				status = true;
			}
		}
		// end of first if

		return status;
	}

}
