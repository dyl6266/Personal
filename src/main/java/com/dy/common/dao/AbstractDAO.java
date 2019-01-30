package com.dy.common.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDAO {

	private static final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 쿼리 아이디 출력
	 * 
	 * @param queryId
	 */
	private void printQueryId(String queryId) {
		logger.info("QueryId ::: " + queryId);
	}

	public int insert(String queryId, Object param) {
		printQueryId(queryId);
		return sqlSession.insert(queryId, param);
	}

	public Object selectOne(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId);
	}

	public Object selectOne(String queryId, Object param) {
		printQueryId(queryId);
		return sqlSession.selectOne(queryId, param);
	}

	public int update(String queryId, Object param) {
		printQueryId(queryId);
		return sqlSession.update(queryId, param);
	}

	public int delete(String queryId, Object param) {
		printQueryId(queryId);
		return sqlSession.delete(queryId, param);
	}

	public List<?> selectList(String queryId) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId);
	}

	public List<?> selectList(String queryId, Object param) {
		printQueryId(queryId);
		return sqlSession.selectList(queryId, param);
	}

}
