package com.dy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dy.common.dao.CommonDAO;
import com.dy.common.domain.Authority;
import com.dy.dao.UserDAO;
import com.dy.domain.CustomUserDetails;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private CommonDAO commonDAO;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 사용자 등록
	 * 
	 * @param params - VO
	 * @return boolean - true or false
	 */
	@Transactional
	@Override
	public boolean registerUser(CustomUserDetails params) {

		boolean status = false;

		/* 등록의 경우 */
		if (params.getIdx() == null) {
			/* 패스워드 암호화 */
			String encodedPw = passwordEncoder.encode(params.getUserPw());
			params.setUserPw(encodedPw);
			/* 권한 (default : GUEST) */
			params.setAuthority(Authority.valueOf(1));

			/* 쿼리 실행 횟수 */
			int queryCnt = userDAO.insertUser(params);
			if (queryCnt > 0) {
				/* 정상 처리된 인증 키 삭제 */
				int resultNum = commonDAO.changeStatusOfAuthKey(params.getAuthKey());
				if (resultNum > 0) {
					status = true;
				}
			}

			/* 수정의 경우 */
		} else {
			if (StringUtils.isEmpty(params.getUserPw()) == false) {
				/* 패스워드 암호화 */
				String encodedPw = passwordEncoder.encode(params.getUserPw());
				params.setUserPw(encodedPw);
			}

			/* 쿼리 실행 횟수 */
			int queryCnt = userDAO.updateUser(params);
			if (queryCnt > 0) {
				status = true;
			}
		}

		return status;
	}

	/**
	 * 사용자 정보 상세 조회
	 * 
	 * @param userId - PK
	 * @return CustomUserDetails - VO
	 */
	@Override
	public CustomUserDetails selectUserDetail(String userId) {
		return userDAO.selectUserDetail(userId);
	}

	/**
	 * 사용자 계정 비활성화
	 * 
	 * @param userId - PK
	 * @return boolean - true or false
	 */
	@Override
	public boolean deleteUser(String userId) {

		boolean result = false;

		int queryCnt = userDAO.deleteUser(userId);
		if (queryCnt > 0) {
			result = true;
		}

		return result;
	}

	/**
	 * 사용자 리스트 조회
	 * 
	 * @param params - VO
	 * @return List - 사용자 리스트
	 */
	@Override
	public List<CustomUserDetails> selectUserList(CustomUserDetails params) {

		List<CustomUserDetails> userList = null;

		int totalCount = userDAO.selectTotalCount(params);
		if (totalCount > 1) {
			userList = userDAO.selectUserList(params);
		}

		return userList;
	}

}
