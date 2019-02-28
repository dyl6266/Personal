package com.dy.common.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.StringUtils;

import com.dy.common.domain.Result;
import com.dy.common.utils.MessageUtils;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	/* TODO와 연관되는 변수 */
	private static int errorCnt;

	/**
	 * 로그인 실패 시 실행되는 메서드
	 * 
	 * TODO : ajax 처리 TODO : 로그인 실패 n번 이상일 때, 계정 disabled > 이메일 인증 or 관리자 요청하기 메시지 등
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		String errorMsg = "";

		if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
			errorMsg = MessageUtils.getMessage("error.BadCredentials");
		} else if (exception instanceof CredentialsExpiredException) {
			errorMsg = MessageUtils.getMessage("error.CredentialsExpired");
		} else if (exception instanceof DisabledException) {
			errorMsg = MessageUtils.getMessage("error.Disabled");
		}

		request.setAttribute("userId", (String) request.getParameter("userId"));
		request.setAttribute("userPw", (String) request.getParameter("userPw"));
		request.setAttribute("errorMsg", errorMsg);

		/* Ajax 호출 확인용 변수 */
		String ajaxHeader = request.getHeader("X-Ajax-call");

//		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//		response.setCharacterEncoding("UTF-8");

		if (StringUtils.isEmpty(ajaxHeader)) {
			super.onAuthenticationFailure(request, response, exception);
		} else {
			String resultStr = "";

			if ( "true".equals(ajaxHeader) ) {
				resultStr = "{\"result\" : \"" + Result.FAIL + "\", \"message\" : \"" + errorMsg + "\"}";
			} else {
				resultStr = "{\"result\" : \"" + Result.FAIL + "\", \"message\" : \"Unknown error)\"}";
			}
			response.getWriter().print(resultStr);
			response.getWriter().flush();
		}

	}
	// end of method

}
