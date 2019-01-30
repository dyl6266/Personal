package com.dy.common.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
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
	/* 보이드가 아닌 다른 리턴타입 생각해보기 */
	public void downloadAttachFile(@RequestParam(value = "idx", required = false) Integer idx) {
		if ( idx == null ) {
			return;
		}
		
		/* 첨부 파일 상세 정보 */
		AttachVO attach = commonService.selectAttachDetail(idx);
		/* 업로드된 파일 경로 */
		System.out.println(attach.getStoredName());
		final String uploadedPath = "C:\\workspace\\upload\\" + attach.getStoredName();
		System.out.println(uploadedPath);
		if ( ObjectUtils.isEmpty(attach) ) {
			return;
		} else {
			/* 파일이 저장된 위치에서 해당 첨부 파일을 읽어, byte[] 형태로 가지고 옴 */
//			File file = new File("");
//			byte[] fileByte = FileUtils.readFileToByteArray(file)
		}
		
	}

}
