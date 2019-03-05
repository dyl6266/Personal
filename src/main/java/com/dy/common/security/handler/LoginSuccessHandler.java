package com.dy.common.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.ObjectUtils;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
//	private UserService userService;

	/* 세션 타임아웃 시간 */
	private static final int TIME = 60 * 10;

	/**
	 * 로그인 성공 시 실행되는 메서드 TODO : 권한에 따라 다른 Redirect 페이지 설정해보기
	 * TODO : 로그인 실패 카운트 업데이트 처리
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		if (ObjectUtils.isEmpty(authentication.getPrincipal()) == false) {
			request.getSession().setMaxInactiveInterval(TIME);
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

}
