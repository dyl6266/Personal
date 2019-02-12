package com.dy.controller;

import java.util.HashMap;
import java.util.Iterator;
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

import com.dy.common.utils.CommonUtils;
import com.dy.domain.BoardVO;
import com.dy.service.BoardService;

@Controller
public class BoardController extends CommonUtils {

	@Autowired
	private BoardService boardService;

	/**
	 * 게시글 리스트 페이지
	 * 
	 * @param params - 페이징 정보
	 * @return String - 페이지
	 */
	@RequestMapping(value = "/board/list.do")
	public String openBoardList(HttpServletRequest request, @RequestParam(value = "params", required = false) BoardVO params, Model model) {

		System.out.println( CommonUtils.getLoginId() );
		/* 로그인 사용자 정보 (애너테이션으로 대체 가능, 그러나 xml 설정이 필요한듯) */
//		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if ( ObjectUtils.isEmpty(user) == false ) {
//			model.addAttribute("");
//		}

		List<BoardVO> boardList = boardService.selectBoardList(params);

		if (!ObjectUtils.isEmpty(boardList) && boardList.size() > 0) {
			model.addAttribute("boardList", boardList);
		}

		return "/board/list";
	}

	/**
	 * 게시글 등록 페이지
	 * 
	 * @param type - insert/update 구분
	 * @param idx  - 게시글 PK
	 * @return String - 페이지
	 */
	@RequestMapping(value = "/board/write.do")
	public String openBoardWrite(@RequestParam(value = "type", defaultValue = "insert") String type,
			@RequestParam(value = "idx", required = false) Integer idx, Model model) {

		if ("update".equals(type)) {
			if (idx == null) {
				// 리다이렉트 처리
			}

			/* 게시글 상세 정보, 첨부 파일 리스트 */
			HashMap<String, Object> hashMap = boardService.selectBoardDetailWithAttachList(idx);
			if (ObjectUtils.isEmpty(hashMap)) {
				// 오류 리다이렉트 처리
			} else {
				Iterator<String> iterator = hashMap.keySet().iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();
					model.addAttribute(key, hashMap.get(key));
				}
				// end of while
			}
		}

		/* 뷰로 전달할 파라미터들 */
		model.addAttribute("type", type);

		return "/board/write";
	}

	/**
	 * 게시글 상세 페이지
	 * 
	 * @param idx - 게시글 PK
	 * @return String - 페이지
	 */
	@RequestMapping(value = "/board/view.do")
	public String openBoardView(@RequestParam(value = "idx", required = false) Integer idx, Model model) {

		/* 게시글 상세 정보, 첨부 파일 리스트 */
		HashMap<String, Object> hashMap = boardService.selectBoardDetailWithAttachList(idx);
		if (ObjectUtils.isEmpty(hashMap)) {
			// 오류 리다이렉트
		} else {
			Iterator<String> iterator = hashMap.keySet().iterator();

			while (iterator.hasNext()) {
				String key = iterator.next();
				model.addAttribute(key, hashMap.get(key));
			}
			// end of while
		}

		return "/board/view";
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
				queryCnt = boardService.registerBoard(request, params);
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

}
