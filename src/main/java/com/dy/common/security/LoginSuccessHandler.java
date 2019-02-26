package com.dy.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.ObjectUtils;

import com.dy.common.domain.YesNo;
import com.dy.domain.MemberVO;
import com.dy.service.MemberService;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private MemberService memberService;
	
	@Autowired
//	private UserService userService;

	/* 세션 타임아웃 시간 */
	private static final int TIME = 60 * 60;

	public void checkMemberStatus(String memberId) {
		MemberVO member = memberService.selectMemberDetail(memberId);
//		CustomUserDetails userDetails = user
		if (ObjectUtils.isEmpty(member)) {
			return;
		}

		YesNo yesNo = member.getStatus();
		if ("N".equals(yesNo.toString())) {
			return;
		}
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		/* 로그인 정보 (애너테이션으로 처리 가능) */
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if ( ObjectUtils.isEmpty(authentication.getPrincipal()) == false ) {
			request.getSession().setMaxInactiveInterval(TIME);
		}
		
//		checkMemberStatus(user.getUsername());
//
//		if (ObjectUtils.isEmpty(user) == false) {
//			request.getSession().setMaxInactiveInterval(TIME);
//		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

}
