package com.dy.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dy.dao.BoardDAO;
import com.dy.domain.BoardVO;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml" })
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/applicationContext-security.xml" })
@ContextConfiguration(locations = { "classpath:/config/spring/context-*.xml" })
public class DAOTest {

	@Autowired
	private BoardDAO dao;

	@Test
	public void testInsert() {
		for (int i = 2; i <= 100; i++) {
			BoardVO vo = new BoardVO();
			vo.setTitle(i + "번 제목");
			vo.setContent(i + "번 내용");
			vo.setWriter(i + "번 작성자");
			vo.setNoticeYn("Y");
			vo.setSecretYn("Y");
			dao.insertBoard(vo);
		}
	}

	@Test
	public void testSelectDetail() {
		dao.selectBoardDetail(1);
	}

	@Test
	public void testUpdate() {
		BoardVO vo = new BoardVO();
		vo.setTitle("수정한 제목");
		vo.setContent("수정한 내용");
		vo.setNoticeYn("N");
		vo.setSecretYn("N");
		vo.setIdx(1);
		dao.updateBoard(vo);
	}

	@Test
	public void testDelete() {
		dao.deleteBoard(1);
	}
	
	@Test
	public void testSelectList() {
		dao.selectBoardList(new BoardVO());
	}
	
	@Test
	public void testSelectCount() {
		dao.selectTotalCount(new BoardVO());
	}

}