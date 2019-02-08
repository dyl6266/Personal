package com.dy.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dy.common.domain.Status;
import com.dy.dao.MemberDAO;
import com.dy.domain.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml" })
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/applicationContext-security.xml" })
@ContextConfiguration(locations = { "classpath:/config/spring/context-*.xml" })
public class DAOTest {

	@Autowired
	private MemberDAO dao;
//	private BoardDAO dao;
	
	@Test
	public void testInsert() {
		for (int i=2; i < 20; i++) {
			MemberVO vo = new MemberVO();
			vo.setMemberId("dyl" + i);
			vo.setMemberPw("vpsxkzhem" + i);
			vo.setMemberName(i + "번 회원");
			vo.setMemberPhone("010332800" + i);
			vo.setMemberEmail("dyl00" + i + "@naver.com");
			vo.setStatus(Status.Y);
			dao.insertMember(vo);
		}
	}
	
	@Test
	public void selectDetail() {
		dao.selectMemberDetail(1);
	}
	
	@Test
	public void testUpdate() {
		MemberVO vo = new MemberVO();
		vo.setIdx(1);
		vo.setMemberPw("vpsxkzhem");
		vo.setMemberName("도영도영");
		vo.setMemberPhone("01033280000");
		vo.setMemberEmail("dyl6266@pentachord.com");
		dao.updateMember(vo);
	}
	
	@Test
	public void testDelete() {
		dao.deleteMember(1);
	}
	
	@Test
	public void testList() {
		dao.selectMemberList(new MemberVO());
	}
	
	@Test
	public void testCount() {
		dao.selectTotalCount(new MemberVO());
	}
	

//	@Test
//	public void testInsert() {
//		for (int i = 2; i <= 100; i++) {
//			BoardVO vo = new BoardVO();
//			vo.setTitle(i + "번 제목");
//			vo.setContent(i + "번 내용");
//			vo.setWriter(i + "번 작성자");
//			vo.setNoticeYn("Y");
//			vo.setSecretYn("Y");
//			dao.insertBoard(vo);
//		}
//	}
//
//	@Test
//	public void testSelectDetail() {
//		dao.selectBoardDetail(1);
//	}
//
//	@Test
//	public void testUpdate() {
//		BoardVO vo = new BoardVO();
//		vo.setTitle("수정한 제목");
//		vo.setContent("수정한 내용");
//		vo.setNoticeYn("N");
//		vo.setSecretYn("N");
//		vo.setIdx(1);
//		dao.updateBoard(vo);
//	}
//
//	@Test
//	public void testDelete() {
//		dao.deleteBoard(1);
//	}
//	
//	@Test
//	public void testSelectList() {
//		dao.selectBoardList(new BoardVO());
//	}
//	
//	@Test
//	public void testSelectCount() {
//		dao.selectTotalCount(new BoardVO());
//	}

}