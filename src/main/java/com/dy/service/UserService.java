package com.dy.service;

import java.util.List;

import com.dy.domain.CustomUserDetails;

public interface UserService {

	/**
	 * 사용자 등록
	 * 
	 * @param params - VO
	 * @return boolean - true or false
	 */
	public boolean registerUser(CustomUserDetails params);

	/**
	 * 사용자 정보 상세 조회
	 * 
	 * @param userId - PK
	 * @return CustomUserDetails - VO
	 */
	public CustomUserDetails selectUserDetail(String userId);

	/**
	 * 사용자 계정 비활성화
	 * 
	 * @param userId - PK
	 * @return boolean - true or false
	 */
	public boolean deleteUser(String userId);

	/**
	 * 사용자 리스트 조회
	 * 
	 * @param params - VO
	 * @return List - 사용자 리스트
	 */
	public List<CustomUserDetails> selectUserList(CustomUserDetails params);

}
