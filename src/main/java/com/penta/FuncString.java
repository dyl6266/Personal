package com.penta;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @Title : 문자 관련 함수
 * @date : 2017. 01. 23.
 * @author : 펜타코드
 */
public class FuncString {
	
	/**
	 * 입력받은 값이 Null 또는 공백인지 체크합니다
	 * @param o
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Boolean isEmpty(Object o) {
		if (o==null) {
			return true;
		} else {
			if (o instanceof String) 
				return base_isEmpty(String.valueOf(o));
			else if (o instanceof List)
				return ((List) o).isEmpty();
			else if (o instanceof Map)
				return ((Map) o).isEmpty();			
			else if (o instanceof Object[])
				return Array.getLength(o) == 0;
			else
				return base_isEmpty(String.valueOf(o));
		}
	}
	
	public static Boolean isEmpty(String s) {
		return base_isEmpty(s);
	}
	public static Boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}
	
	/**
	 * NULL, 공백 여부 체크 후
	 * NULL, 공백이면 true, 아니면 false
	 * @param s
	 * @return
	 */
	private static Boolean base_isEmpty(String s) {
		if (s == null)
			return true;
		else if ("null".equals(s.toLowerCase())) 
			return true;
		else {
			if (isNull2(s).trim().equals("")) 
				return true;
			else
				return false;
		}
	}
	
	/**
	 * 입력받은 두값이 같은 값인지 확인합니다.
	 * @param o
	 * @param o2
	 * @return
	 */
	public static Boolean isEqual(Object o, Object o2) {
		if (o instanceof String || o instanceof Character) {
			if (o2 instanceof String || o2 instanceof Character) {
				String s = String.valueOf(o);
				String s2 = String.valueOf(o2);

				return s.equals(s2);
			}
		}
		
		if (o==null) {
			return false;
		}
		
		return o.equals(o2);
	}
	public static Boolean isNotEqual(Object o, Object o2) {
		return !o.equals(o2);
	}
	
	/**
	 * NULL 여부 체크 후 
	 * 문자면 true, null이면 공백
	 * @param s
	 * @return
	 */
	private static String isNull2(String s) {
		if (s==null || s.length() <0) 
			return "";
		else
			return s;
	}
	
	/**
	 * 숫자 여부 체크 후
	 * 숫자이면 true, 아니면 false  
	 * @param s
	 * @return
	 */
	public static Boolean isNumeric(String s) {
		s = isNull2(s);
		if (Pattern.matches("^[0-9]+$", s)) {
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * 숫자 여부 체크 후 
	 * 숫자이면 숫자, 아니면 대체 숫자 노출 
	 * @param s
	 * @param s2
	 * @return
	 */
	public static String isNumeric(String s, String s2) {
		s = isNull2(s);
		if (isNumeric(s)) {
			return s;
		} else {
			return s2;
		}
	}
	
	
	/**
	 * NULL, 공백 여부 체크 후 
	 * NULL, 공백이면 s2, 아니면 입력 받은 값
	 * @param s
	 * @param s2
	 * @return
	 */
	public static String isEmpty(String s, String s2) {
		if (isEmpty(s)) {
			return s2;
		} else {
			return s;
		}
	}
	
	/**
	 * Null, 공백 체크 후
	 * NUll, 공백이면 S2 아니면 다른 입력한 값
	 * @param s
	 * @param s2
	 * @return
	 */
	public static Object isEmpty(Object s, Object s2) {
		if (isEmpty(s)) {
			return s2;
		} else {
			return s;
		}
	}
	
	/**
	 * s2의 값이 s에 포함되는지 확인
	 * @param s
	 * @param s2
	 * @return
	 */
	public static Boolean isContains(String s, String s2) {
		if (s==null) {
			return false;
		}
		
		return s.contains(s2);
	}
	
	/**
	 * 배열을 문자열로 변환 
	 * @param arr
	 * @param c
	 * @return
	 */
	public static String implode(ArrayList<String> arr, String c) {
		String returnString = new String();
		for (int i=0; i < arr.size(); i++) {
			returnString += arr.get(i)+c;
		}
		returnString = returnString.substring(0, returnString.length() -1);
		return returnString;
	}
	
	/**
	 * Map을 문자열로 변환 
	 * @param params
	 * @param c
	 * @param c2
	 * @param option
	 * @return
	 */
	public static String implode(HashMap<String, Object> params, String c, String c2, int option) {
		ArrayList<String> arr = new ArrayList<String>();
		for (Entry<String, Object> row : params.entrySet()) {
			if (option == 1)
				arr.add(row.getKey());
			else if (option == 2)
				arr.add(row.getValue().toString());
			else
				arr.add(row.getKey() + c + row.getValue());
		}
		return implode(arr, c2);
	}
	
	/**
	 * IPv4의 값을 숫자로 변환
	 * @param ipAddr
	 * @return
	 */
	public static Long ipToInt(String ipAddr) {
        String[] ipAddrArray = ipAddr.split("\\.");

        long num = 0;
        for (int i=0;i<ipAddrArray.length;i++) {
            int power = 3-i;
            /*
             * i의 값으 범위는 0~255 사이이며, 그 값을 256으로 나눈 나머지 값에  
             * 256을 power의 값인 
             * 1~9 사이는 2, 
             * 10~99 사이는 1, 
             * 100 이상은 0 의 값에 누승하여 num 값에 더함
             */
            num += ((Integer.parseInt(ipAddrArray[i])%256 * Math.pow(256,power)));
        }
        return num;
    }
	
	/**
	 * 숫자로된 IPv4를 아이피로 변환
	 */
	public static String intToIp(long num) {
        return ((num >> 24 ) & 0xFF) + "." +
               ((num >> 16 ) & 0xFF) + "." +
               ((num >>  8 ) & 0xFF) + "." +
               ( num        & 0xFF);
    }
	
	/**
	 * 패턴과 일치 여부 반환
	 * 일치하면 true, 아니면 false
	 * @param pattern
	 * @param s
	 * @return
	 */
	public static Boolean isPatternMatch(String pattern, String s) {
		if (Pattern.matches(pattern, s))
			return true;
		else
			return false;
	}
	
	/**
     * 양쪽으로 자리수만큼 문자 채우기
     *
     * @param   str         원본 문자열
     * @param   size        총 문자열 사이즈(리턴받을 결과의 문자열 크기)
     * @param   strFillText 원본 문자열 외에 남는 사이즈만큼을 채울 문자
     * @return  
     */
    public static String getCPad(String str, int size, String strFillText) {
        int intPadPos = 0;
        for(int i = (str.getBytes()).length; i < size; i++) {
            if(intPadPos == 0) {
                str += strFillText;
                intPadPos = 1;
            } else {
                str = strFillText + str;
                intPadPos = 0;
            }
        }
        return str;
    }
    
    /**
     * 왼쪽으로 자리수만큼 문자 채우기
     *
     * @param   str         원본 문자열
     * @param   size        총 문자열 사이즈(리턴받을 결과의 문자열 크기)
     * @param   strFillText 원본 문자열 외에 남는 사이즈만큼을 채울 문자
     * @return  
     */
    public static String getLPad(String str, int size, String strFillText) {
        for(int i = (str.getBytes()).length; i < size; i++) {
            str = strFillText + str;
        }
        return str;
    }

    /**
     * 오른쪽으로 자리수만큼 문자 채우기
     *
     * @param   str         원본 문자열
     * @param   size        총 문자열 사이즈(리턴받을 결과의 문자열 크기)
     * @param   strFillText 원본 문자열 외에 남는 사이즈만큼을 채울 문자
     * @return  
     */
    public static String getRPad(String str, int size, String strFillText) {
        for(int i = (str.getBytes()).length; i < size; i++) {
            str += strFillText;
        }
        return str;
    }
    
    /**
     * Object형태의 parameter를 Key=value로 변환
     * @param object
     * @return
     */
	public static String ObjectToParameter(Object object) {
		String returnString = "";
		
		try {
			Field[] fields = object.getClass().getDeclaredFields();
			String value = null;
			for (Field f : fields) {
				f.setAccessible(true);
				if (f.get(object)==null) {
					value = "";
				} else {
					value = String.valueOf(f.get(object));
				}
				returnString = returnString + f.getName() + "=" + value + "&amp;";
			}
		} catch(SecurityException e) {
			returnString = null;
		} catch(Exception e) {
			returnString = null;
		}
		
		return returnString;
	}
	
	/**
	 * 랜덤 문자열 생성 (영문 대소문자 + 숫자)
	 * @param length
	 * @return
	 */
	public static String RandomStringCreate(int length) {
		int[] plus_value = {48, 65, 97};
		StringBuffer certString = new StringBuffer();
		int index = 0, limitLength = 0;
		char c = 0;
		Random random = null;
		for (int idx=1; idx<=length; idx++) {
			random = new Random(); 
			limitLength = generatedRandom(length);
			index = (int)(random.nextInt(limitLength) % 3);
			if (index == 0) {
				c = (char)((int)(random.nextInt(limitLength) % 10) + plus_value[index]);
			} else {
				c = (char)((int)(random.nextInt(limitLength) % 26) + plus_value[index]);
			}
			certString.append(c);
		}
		return certString.toString();
	}
	
	/**
	 * min ~ max 사이의 랜덤 숫자를 return 합니다.
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInteger(int min, int max) {
		return (generatedRandom(9999999) % (max-min)) + min;
	}
	
	private static int generatedRandom(int limitLength) {
		Random random = new Random();
		int index = random.nextInt(limitLength) * limitLength;
		if (index < 1) {
			index = generatedRandom(limitLength);
		}
		
		return index;
	}
	
	/**
	 * length 보다 작으면 s 최대 length로 글자를 줄임
	 * @param s
	 * @param length
	 * @return
	 */
	public static String subString(String s, int length) {
		if (s.length() < length) {
			return s.substring(0, s.length());
		} else {
			return s.substring(0, length);
		}
	}
	
	public static Boolean arrayFindValue(ArrayList<?> list, String listValue, String findValue) {
		List<String> tmpList = new ArrayList<String>();
		
		Boolean findFlag = false;
		if (list.size() > 0) {
			for (Object o : list) {
				for (Field f : o.getClass().getDeclaredFields()) {
					f.setAccessible(true);
					String fKey = f.getName();
					try {
						if (fKey.equals(listValue)) {
							String fValue = (String) f.get(o);
							tmpList.add(fValue);
						}
					} catch (IllegalArgumentException e) {
						return false;
					} catch (IllegalAccessException e) {
						return false;
					}
				}
			}
			
			findFlag = arrayFindValue(tmpList, findValue);
		}
		
		return findFlag;
	}
	
	private static Boolean arrayFindValue(List<String> list, String value) {
		
		for (String s : list) {
			if (s.equals(value)) {
				return true;
			}
		}
		
		return false;
	}
}
