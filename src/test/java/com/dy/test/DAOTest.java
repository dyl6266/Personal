package com.dy.test;

import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dy.common.domain.YesNo;
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
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void test() {
		Random random = new Random();
		int test = random.nextInt(100);
		System.out.println(test);
	}
	
	
	@Test
	public void testInsert() {
//		for (int i=2; i < 20; i++) {
			MemberVO vo = new MemberVO();
			vo.setMemberId("dyl2436");
			vo.setMemberPw(passwordEncoder.encode("ehdud123"));
			vo.setMemberName("도영");
			vo.setMemberPhone("01033282436");
			vo.setStatus(YesNo.Y);
			dao.insertMember(vo);
//		}
	}
	
	@Test
	public void selectDetail() {
		MemberVO member = dao.selectMemberDetail("dyl2436");
		
		
		String test1 = "ehdud123";
		String test2 = passwordEncoder.encode(test1);
		
		
		System.out.println(passwordEncoder.matches(test1, test1));
		System.out.println(passwordEncoder.matches(test1, test2));
		System.out.println(passwordEncoder.matches(test2, test1));
		System.out.println(passwordEncoder.matches(test2, test2));
	}
	
	@Test
	public void testUpdate() {
		MemberVO vo = new MemberVO();
		vo.setIdx(1);
		vo.setMemberPw("vpsxkzhem");
		vo.setMemberName("도영도영");
		vo.setMemberPhone("01033280000");
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