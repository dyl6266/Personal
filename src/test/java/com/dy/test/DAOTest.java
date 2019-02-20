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
	
//	@Test
//	public void testDelete() {
//		dao.deleteMember(1);
//	}
	
	@Test
	public void testList() {
		dao.selectMemberList(new MemberVO());
	}
	
	@Test
	public void testCount() {
		dao.selectTotalCount(new MemberVO());
	}
	
}