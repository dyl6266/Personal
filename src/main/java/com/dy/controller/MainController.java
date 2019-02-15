package com.dy.controller;

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

		return "/index";
	}

	/**
	 * 로그인 페이지
	 * 
	 * @param status - 상태 (로그인 실패, 로그아웃 등)
	 * @return
	 */
	@RequestMapping(value = "/login.do")
	public String openLoginPage(@RequestParam(value = "status", required = false) String status, Model model) {

		if (StringUtils.isEmpty(status) == false) {
			model.addAttribute("status", status);
		}

		return "/login";
	}

}
