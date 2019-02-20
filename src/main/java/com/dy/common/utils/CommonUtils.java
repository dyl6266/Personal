package com.dy.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.dy.common.domain.YesNo;

public class CommonUtils extends UIUtils {

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
	 * length 만큼의 랜덤 숫자 반화
	 * 
	 * @param length - 생성할 숫자 길이
	 * @param useYn  - 중복 허용 여부
	 * @return
	 */
	public static String getRandomNumber(int length, YesNo useYn) {

		String resultNum = "";
		Random random = new Random();

		/* 중복을 허용하는 경우 */
		if ("Y".equals(useYn.toString())) {
			for (int i = 0; i < length; i++) {
				/* 0 ~ 9 사이의 난수 */
				String randomNum = Integer.toString(random.nextInt(10));
				resultNum += randomNum;
			}
			/* 중복을 허용하지 않는 경우 */
		} else {
			for (int i = 0; i < length; i++) {
				/* 0 ~ 9 사이의 난수 */
				String randomNum = Integer.toString(random.nextInt(10));
				/* 결과에 randomNum과 동일한 숫자가 존재하면 continue */
				if (resultNum.contains(randomNum)) {
					i -= 1;
					continue;
				}
				resultNum += randomNum;
			}
		}

		return resultNum;
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
