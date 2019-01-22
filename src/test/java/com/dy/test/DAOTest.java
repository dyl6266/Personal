package com.dy.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml" })
//@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/applicationContext-security.xml" })
@ContextConfiguration(locations = { "classpath:/config/spring/context-*.xml" })
public class DAOTest {
	
//	@Autowired
//	private


}