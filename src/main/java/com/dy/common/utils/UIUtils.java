package com.dy.common.utils;

import java.util.HashMap;

import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 화면(View) 관련 공통 메서드
 * 
 * @author for 도영
 *
 */
public class UIUtils {

	/**
	 * 메시지를 보여주고 원하는 URI로 리다이렉트
	 * 
	 * @param message     - alert로 보여줄 메시지
	 * @param redirectURI - 리다이렉트할 URI
	 * @param params      - POST 방식으로 넘길 파라미터
	 * @return String - 페이지
	 */
	public String showMessageAndRedirect(@RequestParam(value = "message", required = false) String message,
										 @RequestParam(value = "redirectURI", required = false) String redirectURI,
										 @RequestParam(value = "params", required = false) HashMap<String, Object> params,
										 Model model) {

		if (StringUtils.isEmpty(message) == false) {
			model.addAttribute("message", message);
		}
		if (StringUtils.isEmpty(redirectURI) == false) {
			model.addAttribute("redirectURI", redirectURI);
		}
		if (ObjectUtils.isEmpty(params) == false) {
			model.addAttribute("params", params);
		}

		return "/common/messageRedirect";
	}

}
