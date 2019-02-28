package com.dy.common.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.StringUtils;

import com.dy.common.domain.Result;

/**
 * TODO : AccessDeniedHandlerImpl 클래스를 extends하여 처리해보기
 * TODO : AccessDeniedHandlerImpl 클래스를 extends하여 처리해보기
 * TODO : AccessDeniedHandlerImpl 클래스를 extends하여 처리해보기
 * @author for
 *
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;

	/**
	 * 각각의 파라미터들 때문에 사용자가 입력한 값과 출력할 부분을 별도로 정의할 수 있음(?)
	 * 예외 메시지는 SPRING_SECURITY_403_EXCEPTION을 request 객체의 key로 저장하게 됨
	 * response 객체에 상태 코드를 403으로 셋팅하면 웹서버나 WAS가 가지고 있는 페이지를 보여주게 됨
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

		/* Ajax 호출 확인용 변수 */
		String ajaxHeader = request.getHeader("X-Ajax-call");

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setCharacterEncoding("UTF-8");

		if (StringUtils.isEmpty(ajaxHeader)) {
			/**
			 * 둘 중 어떤걸 사용해야 하는건지? 그냥 아이디 저장용으로 사용하는 건가? 확인해보기
			 */
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Object principal = authentication.getPrincipal();

			if (principal instanceof UserDetails) {
				String memberId = ((UserDetails) principal).getUsername();
				request.setAttribute("memberId", memberId);
			}
			request.setAttribute("errorMsg", accessDeniedException);
			request.getRequestDispatcher(errorPage).forward(request, response);

		} else {
			String resultStr = "";

			if ("true".equals(ajaxHeader)) {
				resultStr = "{\"result\" : \"" + Result.FAIL + "\", \"message\" : \"" + accessDeniedException.getMessage() + "\"}";
			} else {
				resultStr = "{\"result\" : \"" + Result.FAIL + "\", \"message\" : \"Access Denied(Header Value Mismatch)\"}";
			}
			response.getWriter().print(resultStr);
			response.getWriter().flush();
		}

	}

	public void setErrorPage(String errorPage) {
		if ((StringUtils.isEmpty(errorPage) == false) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("errorPage must begin with '/'");
		}

		this.errorPage = errorPage;
	}

}
