package com.penta;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Title : properties 데이터 가져오기
 * @date : 2017. 01. 23.
 * @author : 펜타코드
 */
public class configUtil {
	private static volatile configUtil _config = new configUtil();	
	private static Properties properties = null;
	
	private configUtil() {}
	
	public static synchronized configUtil getInstance() {
		return _config;
	}
	
	public String getString(String key) {
		if (properties==null) {
			initProperties();
		}
		return (String) properties.get(key);
	}
	
	private void initProperties() {
		InputStream is = getClass().getResourceAsStream("/egovframework/egovProps/globals.properties");
		
		properties = new Properties();
		try {
			properties.load(is);
		} catch(IOException e) {
			properties = null;
		} catch(Exception e) {
			properties = null;
		}
	}
}
