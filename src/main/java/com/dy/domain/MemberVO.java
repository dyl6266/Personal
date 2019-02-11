package com.dy.domain;

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

	/* 회원 이메일 */
	private String memberEmail;

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

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

}