//package com.pentachord.util.function;
//
//import java.io.UnsupportedEncodingException;
//import java.security.Key;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//import javax.crypto.Cipher;
//import javax.crypto.NoSuchPaddingException;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//import org.apache.commons.codec.binary.Base64;
//import org.springframework.util.Base64Utils;
//
///**
// * @Title : 암호화 관련 함수
// * @date : 2017. 01. 23.
// * @author : 펜타코드
// */
//public class FuncSecurity {
//	/**
//	 * SHA256 암호화
//	 */	
//	public static String encryptSHA512(String s) {
//		String SHA = "";
//		try {
//			MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
//			sha512.update(s.getBytes());
//			byte byteData[] = sha512.digest();
//			StringBuffer sb = new StringBuffer(); 
//			for(int idx=0 ; idx < byteData.length ; idx++){
//				//sb.append(Integer.toString((byteData[idx]&0xff) + 0x100, 16).substring(1));
//				sb.append(String.format("%02X", 0xFF & byteData[idx]));
//			}
//			SHA = sb.toString();
//		} catch(NoSuchAlgorithmException e){
//			//e.printStackTrace(); 
//			SHA = null; 
//		}
//		return SHA;
//	}
//	
//	public static String encryptSHA1(String s) {
//		if (FuncString.isEmpty(s)) {
//			return null;
//		}
//		
//		String SHA = null;
//		try {
//			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
//			sha1.update(s.getBytes());
//			byte byteData[] = sha1.digest();
//			/*
//			StringBuffer sb = new StringBuffer(); 
//			for(int idx=0 ; idx < byteData.length ; idx++){
//				//sb.append(String.format("%02X", 0xFF & byteData[idx]));
//				sb.append(String.format("%02X", 0xFF & byteData[idx]));
//			}
//			*/
//			SHA = new String(Base64Utils.encode(byteData));
//		} catch(NoSuchAlgorithmException e) {
//			//e.printStackTrace();
//			SHA = null;
//		}
//		return SHA;
//	}
//	
//	/* AES256 암호화 시작 */
//	private static String iv;
//	private static Key keySpec;
//	
//	/**
//	 * AES256 암호화, 복호화 초기화
//	 * @throws UnsupportedEncodingException 
//	 */
//	private static void initAES256(String key) throws UnsupportedEncodingException {
//		if (FuncString.isEmpty(key)) {
//			configUtil config = configUtil.getInstance();
//			key = config.getString("site.security_key");
//		}
//		
//		FuncSecurity.iv = key.substring(0, 16);
//		byte[] keyBytes = new byte[16];
//		byte[] b= key.getBytes("UTF-8");
//		int len = b.length;
//		if (len > keyBytes.length)
//			len = keyBytes.length;
//		System.arraycopy(b, 0, keyBytes, 0, len);
//		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
//		
//		FuncSecurity.keySpec = keySpec;
//	}
//	
//	/**
//	 * AES256 암호화
//	 * @param s 암호화 하고자 하는 문자열
//	 * @return
//	 */
//	public static String encryptAES256(String s) {
//		return encryptAES256(s, null);
//	}
//	
//	/**
//	 * AES256 암호화
//	 * @param s 암호화 하고자 하는 문자열
//	 * @param key 입력값이 null 일시 commonVars에 있는 key 사용
//	 */
//	public static String encryptAES256(String s, String key) {
//		if (FuncString.isEmpty(s)) {
//			return null;
//		}
//		
//		String enAES = "";
//		try {
//			initAES256(key);
//			
//			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
//			
//			byte[] encrypted = c.doFinal(s.getBytes("UTF-8"));
//			enAES = new String(Base64.encodeBase64(encrypted));
//		} catch (UnsupportedEncodingException e) {
//			enAES = null;
//		} catch(NoSuchAlgorithmException e ) {
//			enAES = null;
//		} catch(NoSuchPaddingException e ) {
//			enAES = null;
//		} catch (Exception e) {
//			enAES = null;
//		}
//		return enAES;
//	}
//	
//	/**
//	 * AES256 복호화
//	 * @param s 복호화 하고자 하는 문자열
//	 * @return
//	 */
//	public static String decryptAES256(String s) {
//		return decryptAES256(s, null);
//	}
//	
//	/**
//	 * AES256 복호화
//	 * @param s 복호화 하고자 하는 문자열
//	 * @param key 입력값이 null 일시 commonVars에 있는 key 사용
//	 */
//	public static String decryptAES256(String s, String key) {
//		if (FuncString.isEmpty(s)) {
//			return null;
//		}
//		
//		String deAES = "";
//		try {
//			initAES256(key);
//			
//			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//			c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));
//			
//			byte[] byteStr = Base64.decodeBase64(s.getBytes());
//			deAES = new String(c.doFinal(byteStr), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			deAES = null;
//		} catch(NoSuchAlgorithmException e ) {
//			deAES = null;
//		} catch(NoSuchPaddingException e ) {
//			deAES = null;
//		} catch (Exception e) {
//			deAES = null;
//		}
//		
//		return deAES;
//	}
//	
//	/* AES256 암호화 끝 */
//	
//	public static String encodeBase64(String s) {
//		return Base64.encodeBase64String(s.getBytes());
//	}
//	
//	public static String decodeBase64(String s) {
//		return new String(Base64.decodeBase64(s));
//	}
//}
