package com.dy.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.dy.common.CommonDAO;
import com.dy.common.FileUtils;
import com.dy.dao.BoardDAO;
import com.dy.domain.AttachVO;
import com.dy.domain.BoardVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private FileUtils fileUtils;

	@Autowired
	private BoardDAO boardDAO;

	/**
	 * 게시글 등록
	 * 
	 * @param params - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@Override
	public int registerBoard(BoardVO params, HttpServletRequest request) {

		int queryCnt = 0;

		/* 공지글, 비밀글 설정 */
		String noticeYn = StringUtils.isEmpty(params.getNoticeYn()) ? "N" : params.getNoticeYn();
		String secretYn = StringUtils.isEmpty(params.getSecretYn()) ? "N" : params.getSecretYn();
		params.setNoticeYn(noticeYn);
		params.setSecretYn(secretYn);

		/* 등록의 경우 */
		Integer nextIdx = null;
		if (params.getIdx() == null || params.getIdx() < 1) {
			/* 다음 PK 번호 */
			nextIdx = commonDAO.selectNextIdx("board");
			params.setIdx(nextIdx);

			queryCnt = boardDAO.insertBoard(params);

			/* sequence 테이블 업데이트 */
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("nextIdx", nextIdx);
			hashMap.put("tableName", String.valueOf("board"));
			commonDAO.updateNextIdx(hashMap);

		/* 수정의 경우 */
		} else {
			queryCnt = boardDAO.updateBoard(params);
		}

		Integer boardIdx = params.getIdx() == null ? nextIdx : params.getIdx();

		List<AttachVO> fileList = fileUtils.insertFileInfo(request, boardIdx);
		if ( !ObjectUtils.isEmpty(fileList) && fileList.size() > 0 ) {
			for (AttachVO attach : fileList) {
				commonDAO.insertAttach(attach);
			}
		}

		return queryCnt;
	}

	/**
	 * 게시글 상세 조회
	 * 
	 * @param idx - PK
	 * @return BoardVO - VO 클래스
	 */
	@Override
	public BoardVO selectBoardDetail(Integer idx) {

		BoardVO board = null;

		if (idx != null && idx > 0) {
			board = boardDAO.selectBoardDetail(idx);
		}

		return board;
	}

	/**
	 * 게시글 삭제 (사용 여부 변경)
	 * 
	 * @param idxs - PK를 '|'로 구분한 스트링
	 * @return int - 쿼리 실행 횟수
	 */
	@Override
	public int deleteBoard(String idxs) {

		int queryCnt = 0;

		if (!StringUtils.isEmpty(idxs)) {
			String[] idxArray = idxs.split("|");

			for (String idx : idxArray) {
				queryCnt += boardDAO.deleteBoard(Integer.parseInt(idx));
			}
			System.out.println(queryCnt);
		}

		return queryCnt;
	}

	/**
	 * 게시글 리스트 조회
	 * 
	 * @param params - VO 클래스
	 * @return List - 게시글 리스트
	 */
	@Override
	public List<BoardVO> selectBoardList(BoardVO params) {

		List<BoardVO> boardList = null;

		int totalCnt = boardDAO.selectTotalCount(params);

		if (totalCnt > 0) {
			boardList = boardDAO.selectBoardList(params);
		}

		return boardList;
	}

}
