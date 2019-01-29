package com.dy.common;

import java.util.HashMap;

import org.springframework.stereotype.Repository;

import com.dy.domain.AttachVO;

@Repository("commonDAO")
public class CommonDAO extends AbstractDAO {

	private static String namespace = "common";

	/**
	 * 해당하는 테이블의 마지막 PK + 1의 값을 반환
	 * 
	 * @param tableName - 테이블명
	 * @return int - 다음 시퀀스
	 */
	public Integer selectNextIdx(String tableName) {
		return (Integer) selectOne(namespace + ".selectNextIdx", tableName);
	}

	/**
	 * 해당하는 테이블의 마지막 PK + 1의 값으로 업데이트
	 * 
	 * @param tableName - 테이블명
	 * @return int - 쿼리 실행 횟수
	 */
	public void updateNextIdx(HashMap<String, Object> params) {
		update(namespace + ".updateNextIdx", params);
	}

	/**
	 * 파일 등록
	 * 
	 * @param params - VO 클래스
	 * @return int - 쿼리 실행 횟수
	 */
	public void insertAttach(AttachVO params) {
		insert(namespace + ".insertAttach", params);
	}

}
