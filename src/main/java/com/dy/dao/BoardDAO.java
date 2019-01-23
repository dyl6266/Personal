package com.dy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dy.common.AbstractDAO;
import com.dy.domain.BoardVO;

@Repository("boardDAO")
public class BoardDAO extends AbstractDAO {

	private static String namespace = "board";

	/**
	 * 게시글 등록
	 * 
	 * @param params - Class
	 * @return 쿼리 실행 횟수
	 */
	public int insertBoard(BoardVO params) {
		return insert(namespace + ".insertBoard", params);
	}

	/**
	 * 게시글 상세 조회
	 * 
	 * @param idx - PK
	 * @return 게시글 상세 정보 - Class
	 */
	public BoardVO selectBoardDetail(Integer idx) {
		return (BoardVO) selectOne(namespace + ".selectBoardDetail", idx);
	}

	/**
	 * 게시글 수정
	 * 
	 * @param params - Class
	 * @return 쿼리 실행 횟수
	 */
	public int updateBoard(BoardVO params) {
		return update(namespace + ".updateBoard", params);
	}

	/**
	 * 게시글 삭제 (사용 여부 변경)
	 * 
	 * @param idx - PK
	 * @return 쿼리 실행 횟수
	 */
	public int deleteBoard(Integer idx) {
		return update(namespace + ".deleteBoard", idx);
	}

	/**
	 * 게시글 리스트 조회
	 * 
	 * @param board - Class
	 * @return 게시글 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<BoardVO> selectBoardList(BoardVO params) {
		return (List<BoardVO>) selectList(namespace + ".selectBoardList", params);
	}

	/**
	 * 게시글 전체 개수 조회
	 * 
	 * @param board - Class
	 * @return 게시글 개수
	 */
	public int selectTotalCount(BoardVO params) {
		return (int) selectOne(namespace + ".selectTotalCount", params);
	}

}
