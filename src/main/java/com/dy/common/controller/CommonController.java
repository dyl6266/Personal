package com.dy.common.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dy.common.service.CommonService;
import com.dy.domain.AttachVO;

@Controller
public class CommonController {

	@Autowired
	private CommonService commonService;

	/**
	 * 첨부 파일 다운로드
	 */
	@RequestMapping(value = "/common/downAttachFile.do")
	public void downloadAttachFile(HttpServletResponse response,
			@RequestParam(value = "idx", required = false) Integer idx) {

		if (idx == null) {
			return;
		}

		/* 첨부 파일 상세 정보 */
		AttachVO attach = commonService.selectAttachDetail(idx);
		/* 업로드된 파일 경로 */
		final String uploadedPath = "C:\\workspace\\upload\\" + attach.getStoredName();

		if (ObjectUtils.isEmpty(attach)) {
			return;
		} else {
			/* 파일이 저장된 위치에서 해당 첨부 파일을 읽어서 byte[] 형태로 변환 */
			File file = new File(uploadedPath);
			try {
				byte[] fileByte = FileUtils.readFileToByteArray(file);
				System.out.println(fileByte);

				response.setContentType("application/octet-stream");
				response.setContentLength(fileByte.length);
				/*
				 * Content-Disposition 속성을 이용하여 해당 패킷이 어떤 형태의 데이터인지 알 수 있음 fileName = 첨부 파일의 이름을
				 * 지정, fileName을 다른 이름으로 한다면, 첨부 파일은 그 이름으로 저장이 됨
				 */
				response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(attach.getOriginalName(), "UTF-8") + "\";");
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.getOutputStream().write(fileByte);

				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			} catch (IOException e) {
				e.printStackTrace();
				return;
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		// end of else

	}

	/**
	 * 첨부 파일 삭제
	 * 
	 * @param idx - 첨부 파일 PK
	 * @return ResponsenEntity - (결과 String, Http 상태 코드)
	 */
	@RequestMapping(value = "/common/deleteAttachFile.do", method = RequestMethod.POST)
	public ResponseEntity<HashMap<String, Object>> deleteAttachFile(@RequestParam(value = "idx", required = false) Integer idx) {

		ResponseEntity<HashMap<String, Object>> entity = null;
		HashMap<String, Object> hashMap = new HashMap<>();

		if (idx == null) {
			hashMap.put("ERROR", "NPE1");
			hashMap.put("MESSAGE", "FAIL");
			entity = new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
			return entity;
		}

		try {
			/* 쿼리 실행 횟수 */
			int queryCnt = commonService.deleteAttach(idx);
			if (queryCnt < 1) {
				hashMap.put("ERROR", "NOT RUNNING");
				hashMap.put("MESSAGE", "FAIL");
				entity = new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
			} else {
				hashMap.put("MESSAGE", "OK");
				entity = new ResponseEntity<>(hashMap, HttpStatus.OK);
			}

		} catch (NullPointerException e) {
			hashMap.put("ERROR", "NOT RUNNING");
			hashMap.put("MESSAGE", "FAIL");
			entity = new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
			e.printStackTrace();

		} catch (Exception e) {
			hashMap.put("ERROR", "NOT RUNNING");
			hashMap.put("MESSAGE", "FAIL");
			entity = new ResponseEntity<>(hashMap, HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return entity;
	}

}
