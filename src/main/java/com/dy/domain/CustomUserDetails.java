package com.dy.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dy.common.domain.Authority;

/**
 * UserDetails - 사용자의 정보를 담는 인터페이스 (쉽게 생각해서 VO의 개념)
 * 
 * @author for
 *
 */
@SuppressWarnings("serial")
public class CustomUserDetails extends CommonVO implements UserDetails {

	/* PK */
	private Integer idx;

	/* 회원 아이디 */
	private String userId;

	/* 회원 패스워드 */
	private String userPw;

	/* 회원 이름 */
	private String userName;

	/* 회원 휴대폰 번호 */
	private String userPhone;

	/* 회원 권한 */
	private Authority authority;

	/* 회원가입 인증 키 */
	private String authKey;

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	/**
	 * 계정이 가지고 있는 권한 목록 반환
	 * 
	 * @return
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + Authority.valueOf(authority.getValue())));
		return authorities;
	}

	/**
	 * 계정의 패스워드 반환
	 * 
	 * @return
	 */
	@Override
	public String getPassword() {
		return userPw;
	}

	/**
	 * 계정의 아이디 반환
	 * 
	 * @return
	 */
	@Override
	public String getUsername() {
		return userId;
	}

	/**
	 * 계정이 만료 되었는지에 대한 여부 반환 (true : 만료되지 않음)
	 * 
	 * @return
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * 계정이 잠겨 있는지에 대한 여부 반환 (true : 잠기지 않음)
	 * 
	 * @return
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * 패스워드가 만료 되었는지에 대한 여부 반환 (true : 만료되지 않음)
	 * 
	 * @return
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 계정이 활성화(사용 가능) 상태인지에 대한 여부 반환 (true : 활성화)
	 * 
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		boolean status = true;

		if ("N".equals(getStatus().toString())) {
			status = false;
		}

		return status;
	}

}
