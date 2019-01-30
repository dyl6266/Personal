package com.dy.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dy.domain.BoardVO;

public interface BoardService {

	/**
	 * 게시글 등록
	 * 
	 * @param params - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 */
	public int registerBoard(HttpServletRequest request, BoardVO params);

	/**
	 * 게시글 상세 정보 및 첨부 파일 리스트 조회
	 * 
	 * @param idx - PK
	 * @return HashMap - (VO 클래스, 첨부 파일 리스트)
	 */
	public HashMap<String, Object> selectBoardDetailWithAttachList(Integer idx);

	/**
	 * 게시글 삭제 (사용 여부 변경)
	 * 
	 * @param idxs - PK를 '|'로 구분한 스트링
	 * @return int - 쿼리 실행 횟수
	 */
	public int deleteBoard(String idxs);

	/**
	 * 게시글 리스트 조회
	 * 
	 * @param params - VO 클래스
	 * @return List - 게시글 리스트
	 */
	public List<BoardVO> selectBoardList(BoardVO params);

}
