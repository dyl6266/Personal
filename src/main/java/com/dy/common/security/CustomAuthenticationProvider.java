package com.dy.common.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	public CustomAuthenticationProvider() {
		 super();
	}

//	@Autowired
//	private MemberAuthService memberAuthService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		final String name = authentication.getName().toString();
		final String principal = authentication.getPrincipal().toString();
		final String rawPw = authentication.getCredentials().toString();

		
		final List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		final UserDetails user = new User(name, rawPw, authorities);
		final Authentication auth = new UsernamePasswordAuthenticationToken(user, rawPw, authorities);
		return auth;
		/* 로그인한 유저 정보를 담는 객체 */
//		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
//
//		UserDetails user = memberAuthService.loadUserByUsername(authToken.getName());
//		String rawPw = (String) authentication.getCredentials();
//		String encodedPw = (String) passwordEncoder.encode(user.getPassword());
//
//		if (passwordEncoder.matches(rawPw, encodedPw) == false) {
//			throw new BadCredentialsException("Passwords do not match");
//		}
//
//		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//		return new UsernamePasswordAuthenticationToken(user, null, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
//		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
