package com.dy.controller;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.common.domain.Result;
import com.dy.common.service.CommonService;
import com.dy.common.utils.CommonUtils;
import com.dy.domain.CustomUserDetails;
import com.dy.service.UserService;

@Controller
public class UserController extends CommonUtils {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private CommonService commonService;

	@Autowired
	private UserService userService;

	/**
	 * 회원가입 인증 메일 발송
	 * 
	 * @param memberId - 회원가입 아이디 (이메일을 발송할 주소)
	 * @return JSON - 결과 / 메시지
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/sendAuthMail.do")
	@ResponseBody
	public ResponseEntity<JSONObject> sendAuthenticationMail(@RequestParam(value = "userId", required = false) String userId) {

		ResponseEntity<JSONObject> entity = null;

		/* 오류 및 예외의 경우 리턴할 인스턴스(객체) */
		JSONObject json = new JSONObject();
		json.put("result", Result.FAIL);
		json.put("message", "오류가 발생했습니다. 다시 시도해 주세요.");

		if (StringUtils.isEmpty(userId)) {
			entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
		} else {
			try {
				/* 아이디 중복 체크 */
				CustomUserDetails userDetails = userService.selectUserDetail(userId);
				if (ObjectUtils.isEmpty(userDetails) == false) {
					json = new JSONObject();
					json.put("result", Result.FAIL);
					json.put("message", "이미 가입된 아이디입니다.");
					entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
				} else {
					/* 인증키 저장 & 이메일 발송 */
					boolean status = commonService.registerAuthKeyAndSendMail(userId);

					if (status == false) {
						entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
					} else {
						json = new JSONObject();
						json.put("result", Result.SUCCESS);
						json.put("message", "메일로 발송된 인증번호를 입력해 주세요.");
						entity = new ResponseEntity<>(json, HttpStatus.OK);
					}
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
				entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
			}
		}

		return entity;
	}

	/**
	 * 인증 키 유효성 체크
	 * 
	 * @param params - MemberVO 인스턴스 (객체)
	 * @return JSON - 결과 / 메시지
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/checkAuthKey.do")
	@ResponseBody
	public ResponseEntity<JSONObject> checkAuthKeyValidation(@RequestBody CustomUserDetails params) {

		ResponseEntity<JSONObject> entity = null;

		/* 오류 및 예외의 경우 리턴할 인스턴스(객체) */
		JSONObject json = new JSONObject();
		json.put("result", Result.FAIL);
		json.put("message", "오류가 발생했습니다. 다시 시도해 주세요.");

		if (ObjectUtils.isEmpty(params) || StringUtils.isEmpty(params.getAuthKey()) || StringUtils.isEmpty(params.getUserId())) {
			entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
		} else {
			try {
				/* 인증 키, 회원가입 아이디 */
				String authKey = params.getAuthKey();
				String userId = params.getUserId();

				/* 인증번호 유효성 체크 */
				boolean status = commonService.checkAuthKeyValidation(authKey, userId);
				if (status == false) {
					json = new JSONObject();
					json.put("result", Result.FAIL);
					json.put("message", "유효하지 않은 인증번호입니다.");
					entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
				} else {
					/* 회원 등록 */
					status = userService.registerUser(params);
					if (status == true) {
						json = new JSONObject();
						json.put("result", Result.SUCCESS);
						json.put("message", "가입이 완료되었습니다.");
						entity = new ResponseEntity<>(json, HttpStatus.OK);
					}
				}

			} catch (NullPointerException e) {
				e.printStackTrace();
				entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				e.printStackTrace();
				entity = new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
			}
		}

		return entity;
	}

	/**
	 * 타입에 따른 프로세스 수행
	 * 
	 * @param type     - 처리할 프로세스 구분
	 * @param memberId - 삭제에 사용할 PK
	 * @param params   - 등록 & 수정에 사용할 파라미터
	 * @return String - 페이지
	 */
//	@RequestMapping(value = "/member/processing.do", method = RequestMethod.POST)
//	public String processingMember(@RequestParam(value = "type", required = false) String type,
//			@RequestParam(value = "memberId", required = false) String memberId,
//			@RequestParam(value = "params", required = false) MemberVO params, Model model) {
//
//		String failureResult = showMessageAndRedirect("오류가 발생했습니다. 다시 시도해 주세요.", "/member/list.do", null, model);
//
//		if (StringUtils.isEmpty(type)) {
//			return failureResult;
//		} else if ("register".equals(type) && ObjectUtils.isEmpty(params)) {
//			return failureResult;
//		} else if ("delete".equals(type) && StringUtils.isEmpty(memberId)) {
//			return failureResult;
//		}
//
//		boolean result = false;
//		try {
//			switch (type) {
//			/* 등록 */
//			case "register":
//				result = userService.registerUser(params);
//				break;
//
//			/* 삭제 */
//			case "delete":
//				result = userService.deleteUser(memberId);
//				break;
//
//			default:
//				break;
//			}
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//			return failureResult;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return failureResult;
//		}
//
//		HashMap<String, Object> postParams = null;
//		if (result == true) {
//			/*
//			 * 페이징 & 검색 파라미터 등등
//			 */
//			postParams = new HashMap<>();
//		}
//
//		return showMessageAndRedirect("정상적으로 처리되었습니다.", "/member/list.do", postParams, model);
//	}

	@RequestMapping(value = "/member/accessDenied.do")
	public String openAccessDeniedPage() {

		return "/member/accessDenied";
	}

}
