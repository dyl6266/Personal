package com.dy.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.common.dao.CommonDAO;
import com.dy.domain.AttachVO;

@Service("commonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonDAO commonDAO;

	/**
	 * 첨부 파일 상세 조회
	 * 
	 * @param idx - PK
	 * @return AttachVO - VO 클래스
	 */
	@Override
	public AttachVO selectAttachDetail(Integer idx) {
		return commonDAO.selectAttachDetail(idx);
	}

}
