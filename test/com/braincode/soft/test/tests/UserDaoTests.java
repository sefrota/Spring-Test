package com.braincode.soft.test.tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.braincode.soft.dao.User;
import com.braincode.soft.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { 
		"classpath:com/braincode/soft/test/config/datasource.xml",
		"classpath:com/braincode/soft/config/security-context.xml",
		"classpath:com/braincode/soft/config/dao-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTests {
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private DataSource dataSource;
	
	@Before
	public void init(){
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		jdbc.execute("delete from offers");
		jdbc.execute("delete from users");
	}
	
	@Test
	public void testCreateUser(){
		User user = new User("johnwayne", "John Wayne", "hellohello", "ROLE_ADMIN", "johnny@cash.pt", true);
	
		assertTrue("User creation should return true",usersDao.create(user));
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
		
		List<User> users = usersDao.getAllUsers();
		
		assertEquals("Number of users should be 1", 1, users.size());
	
		assertTrue("User should exist", usersDao.exists(user.getUsername()));
		
		assertEquals("Created user should be identical to retrieved user", user, users.get(0));
	}
}
