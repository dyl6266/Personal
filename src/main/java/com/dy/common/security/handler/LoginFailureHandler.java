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
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.dy.common.utils.MessageUtils;

public class LoginFailureHandler implements AuthenticationFailureHandler {

	/* 로그인 요청 아이디 */
	private String userId;

	/* 로그인 요청 패스워드 */
	private String userPw;

	/* 로그인 요청 실패 시 반환할 메시지 */
	private String errorMsg;

	/* 로그인 요청 실패 시 리다이렉트 할 URI */
//	private String failureUri;

	private static int errorCnt = 0;

	/**
	 * 로그인 실패 시 실행되는 메서드
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		String responseMsg = "";
		/* 로그인 실패 시, 리다이렉트 할 URI */
		final String failureUri = request.getContextPath() + "/login.do?status=fail";

		if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
			responseMsg = MessageUtils.getMessage("error.BadCredentials");
		} else if (exception instanceof CredentialsExpiredException) {
			responseMsg = MessageUtils.getMessage("error.CredentialsExpired");
		} else if (exception instanceof DisabledException) {
			responseMsg = MessageUtils.getMessage("error.Disabled");
		}

		request.setAttribute(userId, (String) request.getParameter("userId"));
		request.setAttribute(userPw, (String) request.getParameter("userPw"));
		request.setAttribute(errorMsg, responseMsg);

		request.getRequestDispatcher(request.getContextPath() + failureUri).forward(request, response);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
