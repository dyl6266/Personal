package com.dy.common.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dy.domain.MemberVO;
import com.dy.service.MemberService;

public class UserAuthService implements UserDetailsService {

	@Autowired
	private MemberService memberService;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

		MemberVO member = memberService.selectMemberDetail(memberId);

		if (member == null) {
			throw new UsernameNotFoundException("No user found with username" + member.getMemberId());
		}

		Collection<SimpleGrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));

		UserDetails user = new User(memberId, member.getMemberPw(), roles);
		return user;
	}

}
