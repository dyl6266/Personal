package com.dy.common.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private MemberAuthService memberAuthService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 인즐 절차가 시작되면 가장 먼저 실행되는 메서드
	 * 
	 * @param authentication - 화면에서 입력한 로그인 정보
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		/* 로그인한 유저 정보를 담는 객체 */
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;

		UserDetails user = memberAuthService.loadUserByUsername(authToken.getName());
		String rawPw = (String) authentication.getCredentials();
		String encodedPw = (String) passwordEncoder.encode(user.getPassword());
		System.out.println(rawPw);
		System.out.println(encodedPw);

		if (passwordEncoder.matches(rawPw, encodedPw) == false) {
			System.out.println("다르다...");
			System.out.println("다르다...");
			System.out.println("다르다...");
			throw new BadCredentialsException("Passwords do not match");
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(user, null, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
