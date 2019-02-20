package com.dy.controller;

import java.util.HashMap;

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

import com.dy.common.utils.CommonUtils;
import com.dy.domain.MemberVO;
import com.dy.service.MemberService;

@Controller
public class MemberController extends CommonUtils {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	/**
	 * 회원가입 페이지
	 * 
	 * @return String - 페이지
	 */
	@RequestMapping(value = "/member/write.do")
	public String openJoinPage(@RequestParam(value = "memberId", required = false) String memberId, Model model) {

		if (StringUtils.isEmpty(memberId) == false) {
			MemberVO member = memberService.selectMemberDetail(memberId);

			if (ObjectUtils.isEmpty(member)) {
				return showMessageAndRedirect("잘못된 접근입니다.", "/member/list.do", null, model);
			}

			model.addAttribute("member", member);
		}

		return "/member/write";
	}

	/**
	 * 타입에 따른 프로세스 수행
	 * 
	 * @param type     - 처리할 프로세스 구분
	 * @param memberId - 삭제에 사용할 PK
	 * @param params   - 등록 & 수정에 사용할 파라미터
	 * @return String - 페이지
	 */
	@RequestMapping(value = "/member/processing.do", method = RequestMethod.POST)
	public String processingMember(@RequestParam(value = "type", required = false) String type,
								   @RequestParam(value = "memberId", required = false) String memberId,
								   @RequestParam(value = "params", required = false) MemberVO params,
								   Model model) {

		String failureResult = showMessageAndRedirect("오류가 발생했습니다. 다시 시도해 주세요.", "/member/list.do", null, model);

		if (StringUtils.isEmpty(type)) {
			return failureResult;
		} else if ("register".equals(type) && ObjectUtils.isEmpty(params)) {
			return failureResult;
		} else if ("delete".equals(type) && StringUtils.isEmpty(memberId)) {
			return failureResult;
		}

		boolean result = false;
		try {
			switch (type) {
			/* 등록 */
			case "register":
				result = memberService.registerMember(params);
				break;

			/* 삭제 */
			case "delete":
				result = memberService.deleteMember(memberId);
				break;

			default:
				break;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return failureResult;
		} catch (Exception e) {
			e.printStackTrace();
			return failureResult;
		}

		HashMap<String, Object> postParams = null;
		if (result == true) {
			/*
			 * 페이징 & 검색 파라미터 등등
			 */
			postParams = new HashMap<>();
		}

		return showMessageAndRedirect("정상적으로 처리되었습니다.", "/member/list.do", postParams, model);
	}

}
