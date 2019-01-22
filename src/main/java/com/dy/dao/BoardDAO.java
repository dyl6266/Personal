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
	 */
	public int insertBoard(BoardVO board) {
		return insert(namespace + ".insertBoard", board);
	}

	/**
	 * 게시글 상세 조회
	 */
	public BoardVO selectBoardDetail(Integer idx) {
		return (BoardVO) selectOne(namespace + ".selectBoardDetail", idx);
	}

	/**
	 * 게시글 수정
	 */
	public int updateBoard(BoardVO board) {
		return update(namespace + ".updateBoard", board);
	}

	/**
	 * 게시글 삭제 (사용 여부 변경)
	 */
	public int deleteBoard(Integer idx) {
		return update(namespace + ".deleteBoard", idx);
	}

	/**
	 * 게시글 리스트 조회
	 */
	@SuppressWarnings("unchecked")
	public List<BoardVO> selectBoardList(BoardVO board) {
		return (List<BoardVO>) selectList(namespace + ".selectBoardList", board);
	}

	/**
	 * 게시글 전체 개수 조회
	 */
	public int selectTotalCount(BoardVO board) {
		return (int) selectOne(namespace + ".selectTotalCount", board);
	}

}
