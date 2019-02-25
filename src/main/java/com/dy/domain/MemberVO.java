package com.dy.domain;

import com.dy.common.domain.Authority;

public class MemberVO extends CommonVO {

	/* PK */
	private Integer idx;

	/* 회원 아이디 */
	private String memberId;

	/* 회원 패스워드 */
	private String memberPw;

	/* 회원 이름 */
	private String memberName;

	/* 회원 휴대폰 번호 */
	private String memberPhone;

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

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
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

}
