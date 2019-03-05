package com.dy.common.domain;

public enum Authority {

	/* 사용자 권한 */
	GUEST(1), MEMBER(2), ADMIN(3);

	private final int value;

	Authority(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Authority valueOf(int value) {
		switch (value) {
		case 1:
			return Authority.GUEST;
		case 2:
			return Authority.MEMBER;
		case 3:
			return Authority.ADMIN;
		default:
			throw new AssertionError("Unknown Authority : " + value);
		}
	}

}
