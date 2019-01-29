package com.dy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dy.domain.BoardVO;
import com.dy.service.BoardService;

@Controller
// util class 상속하기
public class BoardController {

	@Autowired
	private BoardService boardService;

	/**
	 * 게시글 리스트 페이지
	 * 
	 * @param params - 페이징 정보
	 * @return 페이지
	 */
	@RequestMapping(value = "/board/list.do")
	public String openBoardList(@RequestParam(value = "params", required = false) BoardVO params, Model model) {

		List<BoardVO> boardList = boardService.selectBoardList(params);

		if (!ObjectUtils.isEmpty(boardList) && boardList.size() > 0) {
			model.addAttribute("boardList", boardList);
		}

		return "/board/list";
	}

	@RequestMapping(value = "/board/write.do")
	public String openBoardWrite(@RequestParam(value = "type", defaultValue = "insert") String type,
			@RequestParam(value = "idx", required = false) Integer idx, Model model) {

		BoardVO board = null;

		if ("update".equals(type)) {
			if (idx == null || idx < 1) {
				// 리다이렉트 처리
			} else {
				/* 게시글 상세 정보 */
				board = boardService.selectBoardDetail(idx);

				if (ObjectUtils.isEmpty(board)) {
					// 리다이렉트 처리
				} else {
					model.addAttribute("board", board);
				}
			}
		}

		/* 뷰로 전달할 파라미터들 */
		model.addAttribute("type", type);

		return "/board/write";
	}

	@RequestMapping(value = "/board/process.do", method = RequestMethod.POST)
	public String processingBoard(HttpServletRequest request,
			@RequestParam(value = "type", required = false) String type, // 프로세스 유형
			@RequestParam(value = "idxs", required = false) String idxs, // 삭제에 사용되는 파라미터
			BoardVO params) { // 등록에 사용되는 파라미터

		if (StringUtils.isEmpty(type)) {
			// 오류 리다이렉트
		}

		try {
			/* 쿼리 실행 횟수 */
			int queryCnt = 0;

			/* 등록의 경우 */
			if ("register".equals(type)) {
				queryCnt = boardService.registerBoard(params, request);
			} else if ("delete".equals(type)) {
				queryCnt = boardService.deleteBoard(idxs);
			}

			if (queryCnt > 0) {
				// 실패 리다이렉트
			} else {
				// 성공 리다이렉트
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			// 실패 리다이렉트
		} catch (Exception e) {
			e.printStackTrace();
			// 실패 리다이렉트
		}

		return "redirect:/board/list.do";
	}

//	@RequestMapping(value = "/board/process.do", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseEntity<HashMap<String, Object>> processingBoard(MultipartHttpServletRequest request, // 파일 업로드 처리
//																   @RequestParam(value = "type", required = false) String type, // 프로세스 타입
//																   @RequestParam(value = "idxs", required = false) String idxs, // 삭제에 사용되는 파라미터
//																   @RequestBody BoardVO params) { // 등록에 사용되는 파라미터
//
//		ResponseEntity<HashMap<String, Object>> entity = null;
//
//		HashMap<String, Object> resultMap = new HashMap<>();
//
//		if (StringUtils.isEmpty(type) || ObjectUtils.isEmpty(params)) {
//			resultMap.put("MESSAGE", "FAIL");
//			resultMap.put("ERROR", "NPE1");
//			entity = new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
//		} else {
//			try {
//				/* 쿼리 실행 횟수 */
//				int queryCnt = 0;
//
//				/* 등록의 경우 */
//				if ("register".equals(type)) {
//					queryCnt = boardService.registerBoard(params, request);
//				/* 삭제의 경우 */
//				} else if ("delete".equals(type)) {
//					queryCnt = boardService.deleteBoard(idxs);
//				}
//
//				if (queryCnt > 0) {
//					resultMap.put("MESSAGE", "OK");
//					entity = new ResponseEntity<>(resultMap, HttpStatus.OK);
//				} else {
//					resultMap.put("MESSAGE", "FAIL");
//					resultMap.put("ERROR", "NOT RUNNING");
//				}
//			} catch (NullPointerException e) {
//				resultMap.put("MESSAGE", "FAIL");
//				resultMap.put("ERROR", "NPE2");
//				entity = new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
//				e.printStackTrace();
//			} catch (Exception e) {
//				resultMap.put("MESSAGE", "FAIL");
//				resultMap.put("ERROR", "UNKNOWN");
//				entity = new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
//				e.printStackTrace();
//			}
//		}
//
//		return entity;
//	}

}
