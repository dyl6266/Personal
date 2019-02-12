package com.dy.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dy.dao.MemberDAO;

public class MemberAuthService implements UserDetailsService {

	@Autowired
	private MemberDAO memberDAO;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
