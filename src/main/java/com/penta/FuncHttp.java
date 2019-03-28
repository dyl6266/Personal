package com.penta;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Title : HTTP 관련 함수
 * @date : 2017. 01. 23.
 * @author : 펜타코드
 */
public class FuncHttp {

	public static HttpServletRequest getHttpRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		
		return sra.getRequest();
	}
	
	public static HttpSession getHttpSession() {
		return getHttpRequest().getSession();
	}
	
	/**
	 * 현재 Root Context Path를 가져옴
	 * @param request
	 * @return
	 */
	public static String getContextPath() {
		String context = getHttpRequest().getContextPath();
		
		if ( ! (context.equals("/") || context.equals("")) ) {
			return context;
		} else {
			return "";
		}
	}
	
	public static String getRemoteAddr() {
		HttpServletRequest request = getHttpRequest();
		String remoteIp = request.getHeader("X-FORWARDED-FOR"); 
		if (remoteIp == null) {
			try {
				remoteIp = Inet4Address.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				remoteIp = request.getRemoteAddr();
			}
		}
		
		return remoteIp;
	}
	
	/**
	 * 현재 페이지 주소를 가져옴
	 * Controller 전용
	 * @param request
	 * @return
	 */
	public static String getPageUrl() {
		return (String) getHttpRequest().getAttribute("javax.servlet.forward.request_uri");
	}
	
	/**
	 * Parameter Key에 데이터가 있는지 체크 후
	 * NULL, 공백이면 true, 없으면 false
	 * @param session
	 * @param name
	 * @return
	 */
	public static Boolean paramToString(String name){
		HttpServletRequest request = getHttpRequest();
		if (request.getParameter(name) == null)
			return false;
		else {
			if (FuncString.isEmpty(request.getParameter(name).toString()))
				return false;
			else
				return true;
		}
	}
	
	/**
	 * Parameter Key에 데이터가 있는지 체크 후
	 * NULL, 공백이면 s, 없으면 value
	 * @param session
	 * @param name
	 * @return
	 */
	public static String paramToString(String name, String s){
		HttpServletRequest request = getHttpRequest();
		if (!paramToString(name)) {
			return s;
		} else {
			return FuncString.isEmpty(request.getParameter(name), s);
		}
	}
	
	/**
	 * Session Key에 데이터가 있는지 체크 후
	 * NULL, 공백이면 true, 없으면 false
	 * @param session
	 * @param name
	 * @return
	 */
	public static Boolean sessionToString(HttpSession session, String name){
		if (session.getAttribute(name) == null)
			return false;
		else {
			if (FuncString.isEmpty(session.getAttribute(name).toString()))
				return false;
			else
				return true;
		}
	}
	
	/**
	 * Session Key에 데이터가 있는지 체크 후
	 * NULL, 공백이면 s, 없으면 value
	 * @param session
	 * @param name
	 * @return
	 */
	public static String sessionToString(HttpSession session, String name, String s){
		if (!sessionToString(session, name)) {
			return s;
		} else {
			return session.getAttribute(name).toString().trim();
		}
	}
	
	/**
	 * HTML 태그 허용 안함
	 * @param s
	 * @return
	 */
	public static String DisableTag(String s) {
		return s.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	/**
	 * 일반(특수) 문자를 html 문자로 변환
	 * \n, \r, "" => <br />, &nbsp; 변환
	 * @param s
	 * @return
	 */
	public static String nl2br(String s) {
		if (FuncString.isEmpty(s)) {
			return null;
		}
		return s.replaceAll("\\r\\n", "<br/>").replaceAll("\\r", "<br/").replaceAll("\\n", "<br/>");
	}
	
	/**
	 * html 문자를 일반(특수) 문자로 변환
	 * <br />, &nbsp; => \n\r, " " 변환
	 * @param s
	 * @return
	 */
	public static String br2nl(String s) {
		return s.replaceAll("<br>", "\r\n").replaceAll("<br/>", "\r\n");
	}
	
	/**
	 * 스크립트를 제거
	 * @param s
	 * @return
	 */
	public static String script2char(String s) {
		Matcher m;
		Pattern script = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>",Pattern.DOTALL);  
		m = script.matcher(s);  
		s = m.replaceAll("");  
		return s;
	}
	
	/**
	 * XSS 예방 함수
	 * @param s
	 * @return
	 */
	public static String CleanXSS (String s) {
		s = s.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        s = s.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        s = s.replaceAll("'", "& #39;");
        s = s.replaceAll("eval\\((.*)\\)", "");
        s = s.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        s = s.replaceAll("script", "");
		return s;
	}
	
	/**
	 * HTML 태그/JS/CSS 제거
	 * @param s
	 * @return
	 */
	public static String removeTag(String s) {
		return s.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	}
	
	public static String tagEventRemove(String s) {
		return s.replaceAll("(?i)(on)[^= ]*=['\"](.*)['\"]", "");
	}
	
	/**
	 * removeTag 메소드 실행 후 
	 * CleanXSS 메소드 실행
	 * @param s
	 * @return
	 */
	public static String stripTag(String s) {
		s = removeTag(s);
		s = CleanXSS(s);
		
		return s;
	}
	
	/**
	 * 허용된 태그 외의 나머지 모두 삭제
	 * @param contents
	 * @return
	 */
	public static String removeNoAllowTag(String contents) {
		try {
			/*
			 * Img List 필요
			//IMG src에 허용되지 않는 url 제거
			description = description.replace("&lt;", "<").replace("&gt;", ">"); //&lt; &gt; 태그 모두 <>로 변환
			Pattern pattern = Pattern.compile("<img[^>]*[>]"); //이미지 태그 모두 찾기
			Matcher matcher = pattern.matcher(description);
			
			boolean passFlag;
			String matcherString;
			String imot50;
			while(matcher.find()) {
				passFlag = false;
				matcherString = matcher.group();
				
				//현재 이미지가 DB에 저장되어있는지 찾기
				for(ResultSet rs: imotList.getItemList()){
					imot50 = ((UIMileageRS)rs).getImot().getFile50().toString();
					if (matcherString.indexOf(imot50)>-1) {
						passFlag = true;
						break;
					}
				}
				
				if(!passFlag){
					description = description.replace(matcherString, "");
				}
			}
			*/
			//허용되지 않는 태그 삭제
			String pattern2 = "<(\\/?)(?!\\/####)([^<|>]+)?>";  
			String[] allowTags = new String[]{"img", "br", "p", "div", "span", "dl", "dd", "dt" };
			StringBuffer buffer = new StringBuffer();  
			 for (int i = 0; i < allowTags.length; i++)  
			 {  
			     buffer.append("|" + allowTags[i].trim() + "(?!\\w)");  
			 }  
			pattern2 = pattern2.replace("####",buffer.toString());    
			contents = contents.replaceAll(pattern2,"");  
			
			//on으로 시작하는 event 모두 삭제
			contents = contents.replaceAll("(?i)(on)[^= ]*=['\"](.*)['\"]", "");
		} catch(Exception e) {
			contents = "";
		}
		
		return contents;
	}

	/* *
	 * RSS 피드를 가져옴 
	 * @param URL
	 * @return
	public static List<rssVO> getRSSFeed(String URL) {
		List<rssVO> rssList = null;
		try {
			URL url_rss = new URL(URL);
			SyndFeedInput rssInput = new SyndFeedInput();
			SyndFeed feed = rssInput.build(new XmlReader(url_rss));
			if (feed != null) {
				rssList = new ArrayList<>();
				@SuppressWarnings("rawtypes")
				List entries = feed.getEntries();
				for (int index = 0; index < 5; index++) {
					SyndEntry entry = (SyndEntry)entries.get(index);
					rssList.add(new rssVO(entry.getTitle(), entry.getDescription().getValue(), entry.getLink()));
				}
			}
			
		} catch(IllegalArgumentException e ) {
			rssList = null;
		} catch(FeedException e) {
			rssList = null;
		} catch(Exception e){
			//e.printStackTrace();
			rssList = null;
		}
		
		return rssList;
	}
	 */
	
	/**
	 * REST API를 통해 데이터를 가져옵니다
	 * @param sURL
	 * @param headerMap
	 * @param sPostParam
	 * @return
	 * @throws Exception
	 */
	public static String getHttpUrlConnection(String sURL, HashMap<String, String> headerMap, String sPostParam) throws Exception {
		URL url = new URL(sURL);
		HttpURLConnection con = null;
		DataOutputStream wr = null;
		try {
			con = (HttpURLConnection)url.openConnection();
			
			//header 설정
			con.setRequestMethod("POST");
			Iterator<?> iterator = headerMap.entrySet().iterator();
			Entry<?, ?> entry = null;
			while(iterator.hasNext()) {
				entry = (Entry<?, ?>) iterator.next();
				con.setRequestProperty(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
			}
			
	        con.setDoOutput(true);
	        wr = new DataOutputStream(con.getOutputStream());
	        wr.writeBytes(sPostParam);
	        wr.flush();
		} catch(IOException e) {
			// Connection Error 1
			return null;
		} finally {
	        wr.close();
		}
		

        int responseCode = con.getResponseCode();
        BufferedReader br = null;
        StringBuffer resultBuffer = null;
        try {
	        if(responseCode==200) { // 정상 호출
	            br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
	        } else {  // 에러 발생
	            br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "UTF-8"));
	        }
	        String inputLine;
	        resultBuffer = new StringBuffer();
	        while ((inputLine = br.readLine()) != null) {
	        	resultBuffer.append(inputLine);
	        }
        } catch (IOException e) {
        	// Buffer Error 1
        	return null;
        } finally {
        	br.close();
        }
	    
        return resultBuffer.toString();
	}
	
	/**
	 * 쿠키 생성
	 * @param response
	 * @param key
	 * @param value
	 * @param period
	 */
	public static void setCookie(HttpServletResponse response, String key, String value, int period ) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(period);
		
		response.addCookie(cookie);
	}
	
	/**
	 * 쿠키 가져오기
	 * @param key
	 * @return
	 */
	public static String getCookie(String key) {
		String value = null;
		Cookie[] cookies = getHttpRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (key.equals(cookie.getName())) {
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}
}
