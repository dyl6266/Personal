package com.dy.service;

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
	public int registerBoard(BoardVO params, HttpServletRequest request);

	/**
	 * 게시글 상세 조회
	 * 
	 * @param idx - PK
	 * @return BoardVO - VO 클래스
	 */
	public BoardVO selectBoardDetail(Integer idx);

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
