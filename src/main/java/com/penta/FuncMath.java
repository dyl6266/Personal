package com.penta;

import java.util.Random;

/**
 * @Title : 숫자 관련 함수
 * @date : 2017. 01. 23.
 * @author : 펜타코드
 */
public class FuncMath {
	
	/**
	 * i1 / i2으로 나누기를 한 후 올림 후 int로 출력한다.
	 * @param i1
	 * @param i2
	 * @return
	 */
	public static int calculDivideCeil(int i1, int i2) {
		double t_i1 = Double.parseDouble( Integer.toString(i1) );
		double t_i2 = Double.parseDouble( Integer.toString(i2) );
		double divide = Math.ceil(t_i1 / t_i2);
		
		return (int) divide;
	}
	
	/**
	 * n1 ~ n2사이의 숫자 중 랜덤하게 출력
	 * @param n1
	 * @param n2
	 * @return
	 */
  	public static int random(int n1, int n2) {
  		Random random = new Random();
  		return random.nextInt(n1) + n2;
  	}
  	
  	/**
  	 * 문자열을 소수점 버림 후 int로 출력합니다.
  	 * @param s
  	 * @return
  	 */
  	public static int ceil(String s) {
  		double d = Double.parseDouble(s);
  		return (int) Math.ceil(d);
  	}
	
	/**
     * 두 지점간의 거리 계산
     * 
     * @param lat1 지점 1 위도
     * @param lon1 지점 1 경도 
     * @param lat2 지점 2 위도
     * @param lon2 지점 2 경도
     * @param unit 거리 표출단위 (kilometer, meter, mile(기본값))
     * @return
     */
    @SuppressWarnings("unused")
	private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
         
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
         
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
         
        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        } 
 
        return (dist);
    }
    
    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
