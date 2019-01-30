package com.dy.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.dy.common.dao.CommonDAO;
import com.dy.common.utils.FileUtils;
import com.dy.dao.BoardDAO;
import com.dy.domain.AttachVO;
import com.dy.domain.BoardVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private FileUtils fileUtils;

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private BoardDAO boardDAO;

	/**
	 * 게시글 등록
	 * 
	 * @param params - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 */
	@Override
	public int registerBoard(HttpServletRequest request, BoardVO params) {

		int queryCnt = 0;

		/* 공지글, 비밀글 설정 */
		String noticeYn = StringUtils.isEmpty(params.getNoticeYn()) ? "N" : params.getNoticeYn();
		String secretYn = StringUtils.isEmpty(params.getSecretYn()) ? "N" : params.getSecretYn();
		params.setNoticeYn(noticeYn);
		params.setSecretYn(secretYn);

		/* 등록의 경우 */
		if (params.getIdx() == null) {
			queryCnt = boardDAO.insertBoard(params);
			/* 수정의 경우 */
		} else {
			queryCnt = boardDAO.updateBoard(params);
		}

		/* 업로드한 파일 리스트 */
		List<AttachVO> fileList = fileUtils.insertFileInfo(request, params.getIdx());
		if (ObjectUtils.isEmpty(fileList) == false && fileList.size() > 0) {
			for (AttachVO attach : fileList) {
				commonDAO.insertAttach(attach);
			}
		}

		return queryCnt;
	}

	/**
	 * 게시글 상세 정보 및 첨부 파일 리스트 조회
	 * 
	 * @param idx - PK
	 * @return HashMap - (VO 클래스, 첨부 파일 리스트)
	 */
	@Override
	public HashMap<String, Object> selectBoardDetailWithAttachList(Integer idx) {

		HashMap<String, Object> hashMap = null;

		if (idx != null) {
			hashMap = new HashMap<>();

			/* 게시글 상세 정보 */
			BoardVO board = boardDAO.selectBoardDetail(idx);
			if (ObjectUtils.isEmpty(board) == false) {
				hashMap.put("board", board);
			}

			/* 첨부 파일 리스트 */
			List<AttachVO> attachList = commonDAO.selectAttachList(idx);
			if (ObjectUtils.isEmpty(attachList) == false) {
				hashMap.put("attachList", attachList);
			}
		}

		return hashMap;
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

		if (StringUtils.isEmpty(idxs) == false) {
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
