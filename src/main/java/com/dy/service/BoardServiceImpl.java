package com.dy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dy.dao.BoardDAO;
import com.dy.domain.BoardVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;

	/**
	 * 게시글 등록
	 * 
	 * @param params - Class
	 * @return 쿼리 실행 횟수
	 */
	@Override
	public int registerBoard(BoardVO params) {

		int queryCnt = 0;

		/* 등록의 경우 */
		if ( params.getIdx() == null || params.getIdx() < 1 ) {
			queryCnt = boardDAO.insertBoard(params);
			/* 수정의 경우 */
		} else {
			queryCnt = boardDAO.updateBoard(params);
		}

		return queryCnt;
	}

	/**
	 * 게시글 상세 조회
	 * 
	 * @param idx - PK
	 * @return 게시글 상세 정보 - Class
	 */
	@Override
	public BoardVO selectBoardDetail(Integer idx) {

		BoardVO board = null;

		if ( idx != null && idx > 0 ) {
			board = boardDAO.selectBoardDetail(idx);
		}

		return board;
	}

	/**
	 * 게시글 삭제 (사용 여부 변경)
	 * 
	 * @param idxs - PK를 '|'로 구분한 스트링
	 * @return 쿼리 실행 횟수
	 */
	@Override
	public int deleteBoard(String idxs) {

		int queryCnt = 0;

		if ( !StringUtils.isEmpty(idxs) ) {
			String[] idxArray = idxs.split("|");

			for ( String idx : idxArray ) {
				queryCnt += boardDAO.deleteBoard(Integer.parseInt(idx));
			}
			System.out.println(queryCnt);
		}

		return queryCnt;
	}

	/**
	 * 게시글 리스트 조회
	 * 
	 * @param params - Class
	 * @return 게시글 리스트
	 */
	@Override
	public List<BoardVO> selectBoardList(BoardVO params) {

		List<BoardVO> boardList = null;

		int totalCnt = boardDAO.selectTotalCount(params);

		if ( totalCnt > 0 ) {
			boardList = boardDAO.selectBoardList(params);
		}

		return boardList;
	}

}
