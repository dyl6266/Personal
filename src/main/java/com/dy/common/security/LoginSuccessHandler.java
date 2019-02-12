package com.dy.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.ObjectUtils;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	/* 세션 타임아웃 시간 */
	private static final int TIME = 60 * 60;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		/* 로그인 정보 */
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (ObjectUtils.isEmpty(user) == false) {
			request.getSession().setMaxInactiveInterval(TIME);
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

}
