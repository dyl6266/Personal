package com.dy.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class CommonUtils {

	/**
	 * 로그인한 회원의 아이디를 반환
	 * 
	 * @return String - 로그인 아이디
	 */
	public static String getLoginId() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (String) user.getUsername();
	}

	/**
	 * 숫자를 포함한 랜덤 문자열 32글자를 반환
	 * 
	 * @return String - 랜덤 문자열
	 */
	public static String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 현재 시간을 문자열 형태로 반환
	 * 
	 * @return String - 현재 시간
	 */
	public static String getCurrentTime() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return (String) sdf.format(calendar.getTime());
	}

}
