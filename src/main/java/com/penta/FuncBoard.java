//package com.penta;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import com.pentachord.board.service.boardMasterVO;
//import com.pentachord.cmm.vo.searchVO;
//import com.pentachord.member.service.memberVO;
//
///**
// * @Title : 게시판 관련 함수o
// * @date : 2017. 01. 23.
// * @author : 펜타코드
// */
//public class FuncBoard {
//
//	/**
//	 * 현재 접속한 게시판의 이용 권한을 가지고 있는지 확인합니다.
//	 * @param masterVO
//	 * @param loginMemberVO
//	 * @return
//	 */
//	public static HashMap<String, Boolean> getBoardPerm(boardMasterVO masterVO, memberVO memberVO) {
//		if (memberVO==null) {
//			memberVO = new memberVO();
//			memberVO.setMem_grade(0); // 0 : 비회원
//		}
//		
//		HashMap<String, Boolean> permMap = new HashMap<>();
//		permMap.put("_perm_perm",			FuncBoard.isAllowCheck(masterVO.getPerm_perm(), memberVO.getMem_grade()));
//		permMap.put("_perm_list",				FuncBoard.isAllowCheck(masterVO.getPerm_list(), memberVO.getMem_grade()));
//		permMap.put("_perm_view",			FuncBoard.isAllowCheck(masterVO.getPerm_view(), memberVO.getMem_grade()));
//		permMap.put("_perm_write",			FuncBoard.isAllowCheck(masterVO.getPerm_write(), memberVO.getMem_grade()));
//		permMap.put("_perm_modify",		FuncBoard.isAllowCheck(masterVO.getPerm_modify(), memberVO.getMem_grade()));
//		permMap.put("_perm_reply",			FuncBoard.isAllowCheck(masterVO.getPerm_reply(), memberVO.getMem_grade()));
//		
//		permMap.put("_perm_cmt",			FuncBoard.isAllowCheck(masterVO.getPerm_comment(), memberVO.getMem_grade()));
//		permMap.put("_perm_noti",			FuncBoard.isAllowCheck(masterVO.getPerm_notice(), memberVO.getMem_grade()));
//		
//		permMap.put("_perm_editor",			FuncBoard.isAllowCheck(masterVO.getPerm_editor(), memberVO.getMem_grade()));
//		permMap.put("_perm_file_dn",		FuncBoard.isAllowCheck(masterVO.getPerm_file_down(), memberVO.getMem_grade()));
//		permMap.put("_perm_file_up",		FuncBoard.isAllowCheck(masterVO.getPerm_file_up(), memberVO.getMem_grade()));
//		permMap.put("_perm_img_dn",		FuncBoard.isAllowCheck(masterVO.getPerm_img_down(), memberVO.getMem_grade()));
//		permMap.put("_perm_img_up",		FuncBoard.isAllowCheck(masterVO.getPerm_img_up(), memberVO.getMem_grade()));
//		permMap.put("_perm_filter",			FuncBoard.isAllowCheck(masterVO.getPerm_filter(), memberVO.getMem_grade()));
//		permMap.put("_perm_secret",			FuncBoard.isAllowCheck(masterVO.getPerm_secret(), memberVO.getMem_grade()));
//		
//		return permMap;
//	}
//	
//	/**
//	 * String 형태로 되어있는 json 카테고리를 Map으로 변환합니다.
//	 * @param str_cate
//	 * @return
//	 */
//	public static LinkedHashMap<String, String> getCateMap(String str_cate) {
//		LinkedHashMap<String, String> cate_map = null;
//		if (FuncString.isNotEmpty(str_cate)) {
//			JSONArray cate_array = new JSONArray(str_cate);
//			if (cate_array.length()>0) {
//				cate_map = new LinkedHashMap<>();
//				JSONObject cate_json = null;
//				
//				for (int idx=0; idx<cate_array.length(); idx++) {
//					cate_json = (JSONObject) cate_array.get(idx);
//					cate_map.put(cate_json.getString("key"), cate_json.getString("val"));
//				}
//			}
//		}
//		
//		return cate_map;
//	}
//	
//	/**
//	 * ,로 구분되어 있는 권한을 split후 권한을 가지고 있는지 체크합니다.
//	 * @param s_perm
//	 * @param user_grade
//	 * @return
//	 */
//	private static Boolean isAllowCheck(String s_perm, Integer user_grade) {
//		Boolean b_allow = false;
//		
//		if (FuncString.isNotEmpty(s_perm)) {
//			String[] arr_perm = s_perm.split(",");
//			String s_grade = String.valueOf(user_grade);
//			
//			for (String perm : arr_perm) {
//				if (perm.equals(s_grade)) {
//					b_allow = true;
//					break;
//				}
//			}
//		}
//		
//		return b_allow;
//	}
//	
//	/**
//	 * 페이징을 처리해서 문자열(HTML태그)로 출력합니다.
//	 * parameter는 params에 정의
//	 */
//	public static String paging(String url, int page, int page_per_block, int total_page, Object params) {
//		int total_block = FuncMath.calculDivideCeil(total_page, page_per_block);
//		int block = FuncMath.calculDivideCeil(page, page_per_block);
//		int first_page = (block-1) * page_per_block;
//		int last_page = block * page_per_block;
//		
//		if (total_block <= block) {
//			last_page = total_page;
//		}
//		
//		String returnString = "";
//		String parameterString = "";
//		if (params != null) {
//			parameterString = FuncString.ObjectToParameter(params);
//		}
//		
//		if (page > page_per_block) {
//			returnString += "<a class=\"direction\" href=\"" + url + "?page=1&amp;" + parameterString + "\">"
//					+ "<span></span>처음</a>&nbsp;";
//		}
//		
//		if (block > 1) {
//			int my_page = first_page;
//			returnString += "<a class=\"direction\" href=\"" + url + "?page=" + my_page + "&amp" + parameterString + "\">"
//					+ "<span></span>이전</a>";
//		}
//		
//		for(int direct_page = first_page + 1; direct_page <= last_page; direct_page++) {
//			if(page == direct_page) {
//				returnString += "<strong>" + direct_page + "</strong>";
//			} else {
//				returnString += "<a href=\"" + url + "?page=" + direct_page + "&amp;" + parameterString + "\">"
//						+ direct_page + "</a>";
//			}
//			if (direct_page != last_page) returnString += "&nbsp;";
//		}
//		
//		if(block < total_block) {
//			int my_page = last_page + 1;
//			returnString += "<a class=\"direction\" href=\"" + url + "?page=" + my_page + "&amp;" + parameterString + "\">"
//					+ "다음<span></span></a>";
//			
//			returnString += "<a class=\"direction\" href=\"" + url + "?page=" + total_page + "&amp;" + parameterString + "\">"
//					+ "끝<span></span></a>";
//		}
//		
//		return returnString;
//	}
//	
//	/**
//	 * 페이징 계산하는데 필요한 요소들을 묶음
//	 * 들어있는 요소들 : total_record, total_page, page, apage
//	 */
//	public static void calculPage(int page, int totalRecord, int maxRows, searchVO vo) {
//		//총 페이지수
//		int totalPage = FuncMath.calculDivideCeil(totalRecord, maxRows);
//		int calculPage = (page-1) * maxRows;
//
//		vo.setTotalPage(totalPage);
//		vo.setTotalRecord(totalRecord);
//		vo.setCurrentPage(page);
//		vo.setCalculPage(calculPage);
//	}
//}	
