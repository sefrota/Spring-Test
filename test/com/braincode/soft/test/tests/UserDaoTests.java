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
	private User user1 = new User("johnwayne", "John Wayne", "imnotbatman", "ROLE_ADMIN", "john@wayne.pt", true);
	private User user2 = new User("billythekid", "Billy The Kid", "shootfirst", "ROLE_USER", "billy@kid.pt", true);
	private User user3 = new User("skinnyjoe", "Skinny Joe", "fatmilksucks", "ROLE_USER", "skinny@joe.pt", true);
	private User user4 = new User("tonytwotimes", "Tony Two Times", "twotimes", "ROLE_USER", "tonytwo@times.pt", true);
	private User user5 = new User("ojsimpson", "OJ Simpson", "iliketokill", "ROLE_USER", "ojsimpson@kill.pt", false);

	
	
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
	public void testCreateRetrieve(){
		usersDao.create(user1);
		
		List<User> users1 = usersDao.getAllUsers();
		
		assertEquals("One user must have been created and retrieved", 1, users1.size());
		
		assertEquals("Inserted user must match retrieved user", user1, users1.get(0));
		
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
		usersDao.create(user5);
		
		List<User> users2 = usersDao.getAllUsers();
		
		assertEquals("We should have created at this moment 5 users", 5, users2.size());
	}
	
	@Test
	public void testExists(){
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		
		assertTrue("User should exist", usersDao.exists(user2.getUsername()));
		assertFalse("User should not exist", usersDao.exists("ahahaha"));
		
	}
	
//	@Test
//	public void testUsers(){
//	
//		//assertTrue("User creation should return true",usersDao.create(user));
//		usersDao.create(user1);
////		Authentication auth = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword());
////        SecurityContextHolder.getContext().setAuthentication(auth);
////		
//		List<User> users = usersDao.getAllUsers();
//		
//		assertEquals("Number of users should be 1", 1, users.size());
//	
//		assertTrue("User should exist", usersDao.exists(user1.getUsername()));
//		assertFalse("User should not exist", usersDao.exists("ahahaha"));
//		
//		assertEquals("Created user should be identical to retrieved user", user1, users.get(0));
//	}
}
