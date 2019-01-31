package com.dy.common.utils;

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

	/* 현재 시간 (19-01-01 형식) */
//	private static final String currentTime = CommonUtils.getCurrentTime().substring(2, 10);
	
	/* 업로드 경로 */
	private static final String filePath = "C:\\workspace\\upload\\";

	/**
	 * 업로드한 파일 리스트 반환
	 * 
	 * @return List - 업로드 파일 리스트
	 */
	public List<AttachVO> insertFileInfo(HttpServletRequest request, Integer boardIdx) {

		List<AttachVO> attachList = new ArrayList<>();

		/* Multipart 형식의 데이터 처리를 위한 캐스팅 */
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		/* Iterator 패턴을 사용하여 input file의 name을 알아서 가져오도록 처리 */
		Iterator<String> iterator = multipartRequest.getFileNames();

		/* filePath에 해당하는 경로를 가지는 file 객체 생성 */
		File file = new File(filePath);
		if ( file.exists() == false ) {
			/* 파일이 존재하지 않으면, 존재하지 않는 부모 폴더까지 포함하여 해당 경로에 폴더 생성 */
			file.mkdirs();
		}

		try {
			/* Iterator에 담긴 파일 개수(요소)만큼 반복 처리 */
			while ( iterator.hasNext() ) {
				/* 서버로 넘어온 파일 name을 통해 multipartFile 객체에 파일 정보 저장 */
				MultipartFile multipartFile = multipartRequest.getFile(iterator.next());

				if ( multipartFile.isEmpty() == false ) {
					System.out.println("========== file start ==========");
					System.out.println("name : " + multipartFile.getName()); // 파라미터 이름
					System.out.println("fileName : " + multipartFile.getOriginalFilename()); // 업로드한 파일 이름
					System.out.println("size : " + multipartFile.getSize()); // 업로드한 파일 크기
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
