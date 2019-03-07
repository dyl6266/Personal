package com.dy.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dy.common.dao.AbstractDAO;
import com.dy.domain.CustomUserDetails;

@Repository("userDAO")
public class UserDAO extends AbstractDAO {

	private static final String namespace = "user";

	/**
	 * 사용자 등록
	 * 
	 * @param params - VO
	 * @return int - 쿼리 실행 수
	 */
	public int insertUser(CustomUserDetails params) {
		return insert(namespace + ".insertUser", params);
	}

	/**
	 * 사용자 정보 상세 조회
	 * 
	 * @param userId - PK
	 * @return CustomUserDetails - VO
	 */
	public CustomUserDetails selectUserDetail(String userId) {
		return (CustomUserDetails) selectOne(namespace + ".selectUserDetail", userId);
	}

	/**
	 * 사용자 정보 수정
	 * 
	 * @param params - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 */
	public int updateUser(CustomUserDetails params) {
		return update(namespace + ".updateUser", params);
	}

	/**
	 * 사용자 계정 비활성화
	 * 
	 * @param userId - PK
	 * @return int - 쿼리 실행 횟수
	 */
	public int deleteUser(String userId) {
		return update(namespace + ".deleteUser", userId);
	}

	/**
	 * 사용자 리스트 조회
	 * 
	 * @param params - VO
	 * @return List - 사용자 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<CustomUserDetails> selectUserList(CustomUserDetails params) {
		return (List<CustomUserDetails>) selectList(namespace + ".selectUserList", params);
	}

	/**
	 * 전체 사용자 수 조회
	 * 
	 * @param params - VO
	 * @return int - 전체 사용자 수
	 */
	public int selectTotalCount(CustomUserDetails params) {
		return (int) selectOne(namespace + ".selectTotalCount", params);
	}

}
