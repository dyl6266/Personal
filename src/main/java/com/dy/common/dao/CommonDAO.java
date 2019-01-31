package com.dy.common.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dy.domain.AttachVO;
import com.dy.domain.BoardVO;

@Repository("commonDAO")
public class CommonDAO extends AbstractDAO {

	private static String namespace = "common";

	/**
	 * 해당하는 테이블의 마지막 PK + 1의 값을 반환
	 * 
	 * @param tableName - 테이블명
	 * @return Integer - 다음 시퀀스
	 */
	public Integer selectNextIdx(String tableName) {
		return (Integer) selectOne(namespace + ".selectNextIdx", tableName);
	}

	/**
	 * 해당하는 테이블의 마지막 PK + 1의 값으로 업데이트
	 * 
	 * @param params - HashMap (타겟 테이블, 다음 PK 번호)
	 */
	public void updateNextIdx(HashMap<String, Object> params) {
		update(namespace + ".updateNextIdx", params);
	}

	/**
	 * 첨부 파일 등록
	 * 
	 * @param params - VO 클래스
	 */
	public void insertAttach(AttachVO params) {
		insert(namespace + ".insertAttach", params);
	}

	/**
	 * 첨부 파일 삭제 (사용 여부 변경)
	 * 
	 * @param idx
	 */
	public int deleteAttach(Integer idx) {
		return update(namespace + ".deleteAttach", idx);
	}

	/**
	 * 첨부 파일 상세 조회
	 * 
	 * @param idx - PK
	 * @return AttachVO - VO 클래스
	 */
	public AttachVO selectAttachDetail(Integer idx) {
		return (AttachVO) selectOne(namespace + ".selectAttachDetail", idx);
	}

	/**
	 * 첨부 파일 리스트 조회
	 * 
	 * @param boardIdx - 게시글 번호
	 * @return List - 첨부 파일 리스트
	 */
	@SuppressWarnings("unchecked")
	public List<AttachVO> selectAttachList(Integer boardIdx) {
		return (List<AttachVO>) selectList(namespace + ".selectAttachList", boardIdx);
	}

	/**
	 * 첨부 파일 전체 개수 조회
	 * 
	 * @param boardIdx - 게시글 번호
	 * @return int - 첨부 파일 개수
	 */
	public int selectTotalCount(Integer boardIdx) {
		return (int) selectOne(namespace + ".selectTotalCount", boardIdx);
	}

}
