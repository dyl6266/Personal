package com.dy.common;

import java.util.UUID;

public class CommonUtils {

	/**
	 * 숫자를 포함한 랜덤 문자열 32글자를 반환
	 * 
	 * @return String - 랜덤 문자열
	 */
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
