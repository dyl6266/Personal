package com.dy.test;

import java.util.Random;

import com.dy.common.domain.Authority;
import com.dy.common.domain.YesNo;

public class Test {
	
	public static void main(String[] args) {
//		System.out.println(CommonUtils.getRandomNumber(8, YesNo.Y));
//		System.out.println(CommonUtils.getRandomNumber(8, YesNo.N));
		
		for ( Authority auth : Authority.values() ) {
			System.out.println(auth);
			System.out.println(auth.getValue());
		}
	}
	
	public static String getRandomNumber(int length, YesNo useYn) {
		String result = "";
		Random random = new Random();
		
		String randomNum = "";
		
		/* 중복 Y의 경우 */
		if ( "Y".equals(useYn.toString()) ) {
			for (int i = 0; i < length; i++) {
				randomNum = Integer.toString(random.nextInt(10));
				result += randomNum;
			}
		/* 중복 N의 경우 */
		} else {
			for (int i = 0; i < length; i++) {
				randomNum = Integer.toString(random.nextInt(10));
				/* */
				if ( result.contains(randomNum) ) {
					i -= 1;
					continue;
				}
				result += randomNum;
			}
		}
		System.out.println(result);
		return result;
	}
	
	
}
