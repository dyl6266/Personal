package com.dy.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dy.domain.AttachVO;

/* 해당 객체의 관리를 스프링이 담당하도록 하는 애너테이션 */
@Component("fileUtils")
public class FileUtils {

	private static final String filePath = "C:\\dev\\file\\";

	public List<AttachVO> insertFileInfo(HttpServletRequest request, Integer boardIdx) {

		List<AttachVO> attachList = new ArrayList<>();

		/* Multipart 형식의 데이터 처리를 위한 캐스팅 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		/* Iterator 패턴을 사용하여 input file의 name을 알아서 가져오도록 처리 */
		Iterator<String> iterator = multipartRequest.getFileNames();

		/*
		 * filePath에 해당하는 파일 객체 생성
		 * 파일이 없으면, 존재하지 않는 부모 폴더까지 포함하여 해당 경로에 폴더 생성
		 */
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		try {
			/* Iterator에 담긴 파일 개수(요소)만큼 반복 처리 */
			while (iterator.hasNext()) {
				MultipartFile multipartFile = multipartRequest.getFile(iterator.next());

				if (!multipartFile.isEmpty()) {
					System.out.println("========== file start ==========");
					System.out.println("name : " + multipartFile.getName());
					System.out.println("fileName : " + multipartFile.getOriginalFilename());
					System.out.println("size : " + multipartFile.getSize());
					System.out.println("========== file end ==========");

					/* 원본 파일명 */
					String originalName = multipartFile.getOriginalFilename();
					/* 파일 확장자 */
					String fileExtension = originalName.substring(originalName.lastIndexOf("."));
					/* 저장 파일명 */
					String storedName = CommonUtils.getRandomString() + fileExtension;
					/* 파일 사이즈 */
					long fileSize = multipartFile.getSize();

					/* 서버에 파일 저장 */
					file = new File(filePath + storedName);
					/* 지정한 경로에 파일 생성 */
					multipartFile.transferTo(file);

					AttachVO attach = new AttachVO();
					attach.setBoardIdx(boardIdx);
					attach.setOriginalName(originalName);
					attach.setStoredName(storedName);
					attach.setFileSize(fileSize);
					attachList.add(attach);
				}
			}
			// end of while
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return attachList;
	}

}
