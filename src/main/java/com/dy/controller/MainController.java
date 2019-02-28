package com.dy.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	/**
	 * 인덱스 페이지
	 * 
	 * @return String - 페이지
	 */
	@RequestMapping(value = "/index.do")
	public String openIndexPage(Model model) {

//		String memberId = CommonUtils.getLoginId();
//		model.addAttribute("memberId", memberId);
		return "/index";
	}

	/**
	 * 회원가입 페이지
	 * 
	 * @return String - 페이지
	 */
	@RequestMapping(value = "/join.do")
	public String openJoinPage(Model model) {

		return "/join";
	}

	/**
	 * 로그인 페이지
	 * TODO : principal, authentication은 비슷한(?) 형태의 오브젝트인데 어떤걸 사용해야 할지
	 * @param status - 상태 (로그인 실패, 로그아웃 등)
	 * @return
	 */
	@RequestMapping(value = "/login.do")
	public String openLoginPage(Principal principal, Authentication authentication,
			@RequestParam(value = "status", required = false) String status, Model model) {

		if (StringUtils.isEmpty(status) == false) {
			model.addAttribute("status", status);
		}

		return "/login";
	}

	@RequestMapping(value = "/admin/test.do")
	public String testAdmin() {

		return "/admin/test";
	}

	@RequestMapping(value = "/member/test.do")
	public String testMember() {

		return "/member/test";
	}

	@RequestMapping(value = "/board/test.do")
	public String testGuest() {

		return "/board/test";
	}

	@RequestMapping(value = "/test.do")
	public String testLogin() {

		return "/test";
	}

}
