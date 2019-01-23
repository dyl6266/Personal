package com.dy.service;

import java.util.List;

import com.dy.domain.BoardVO;

public interface BoardService {

	/**
	 * 게시글 등록 (insert OR update)
	 * 
	 * @param params - Class
	 * @return 쿼리 실행 횟수
	 */
	public int registerBoard(BoardVO params);

	/**
	 * 게시글 상세 조회
	 * 
	 * @param idx - PK
	 * @return 게시글 상세 정보 - Class
	 */
	public BoardVO selectBoardDetail(Integer idx);

	/**
	 * 게시글 삭제 (사용 여부 변경)
	 * 
	 * @param idxs - PK를 '|'로 구분한 스트링
	 * @return 쿼리 실행 횟수
	 */
	public int deleteBoard(String idxs);

	/**
	 * 게시글 리스트 조회
	 * 
	 * @param params - Class
	 * @return 게시글 리스트
	 */
	public List<BoardVO> selectBoardList(BoardVO params);

}
