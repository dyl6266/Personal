package com.dy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dy.domain.MemberVO;
import com.dy.service.MemberService;

@Controller
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	/**
	 * 회원가입 페이지
	 * 
	 * @return
	 */
	@RequestMapping(value = "/member/join.do")
	public String openJoinPage(Model model) {

		return "/member/join";
	}

	@RequestMapping(value = "/member/processing.do", method = RequestMethod.POST)
	public String processingMember(@RequestParam(value = "type", required = false) String type, // 프로세스 구분
								   @RequestParam(value = "memberId", required = false) String memberId, // 삭제 처리용 파라미터
								   MemberVO params) { // 등록 & 수정 처리용 파라미터

		if (StringUtils.isEmpty(type)) {
			// 리다이렉트 처리
		} else if ("register".equals(type) && ObjectUtils.isEmpty(params)) {
			// 리다이렉트 처리
		} else if ("delete".equals(type) && StringUtils.isEmpty(memberId)) {
			// 리다이렉트 처리
		}

		boolean result = false;
		try {
			/* 등록의 경우 */
			if ("register".equals(type)) {
				result = memberService.registerMember(params);
				/* 삭제의 경우 */
			} else if ("delete".equals(type)) {
				result = memberService.deleteMember(memberId);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			// 리다이렉트
		} catch (Exception e) {
			e.printStackTrace();
			// 리다이렉트
		}

		return "redirect:/member/list.do";
	}

}
