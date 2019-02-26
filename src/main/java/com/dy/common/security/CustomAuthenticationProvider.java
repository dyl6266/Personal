package com.dy.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dy.domain.CustomUserDetails;

/**
 * AuthenticationProvider - 화면에서 입력한 로그인 정보와 DB에서 가져온 사용자 정보를 비교해주는 인터페이스
 * 
 * @author for
 *
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 인즐 절차가 시작되면 가장 먼저 실행되는 메서드 (비즈니스 로직 수행(?))
	 * 
	 * @param authentication - 사용자가 입력한 로그인 정보를 담는 인스턴스(객체)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String userId = (String) authentication.getPrincipal();
		String rawPw = (String) authentication.getCredentials();

		/* CustomUserDetailsService에서 구현한 DB에 저장된 사용자 정보 */
		CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(userId);

		if (passwordEncoder.matches(rawPw, userDetails.getUserPw()) == false) {
			throw new BadCredentialsException("Passwords do not match");
		}

		if (userDetails.isEnabled() == false) {
			throw new DisabledException("계정이 비활성화 되었습니다. 관리자에게 문의해 주세요.");
		}

		/* 사용자가 입력한 정보와 DB에 저장된 사용자 권한을 담아 리턴 */
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
