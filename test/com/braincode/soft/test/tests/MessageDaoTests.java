package com.braincode.soft.test.tests;

import static org.junit.Assert.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.braincode.soft.dao.Message;
import com.braincode.soft.dao.MessagesDao;
import com.braincode.soft.dao.Offer;
import com.braincode.soft.dao.OffersDao;
import com.braincode.soft.dao.User;
import com.braincode.soft.dao.UsersDao;

@ActiveProfiles("dev")
@ContextConfiguration(locations = { 
		"classpath:com/braincode/soft/test/config/datasource.xml",
		"classpath:com/braincode/soft/config/security-context.xml",
		"classpath:com/braincode/soft/config/dao-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageDaoTests {

	@Autowired
	private MessagesDao messagesDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private DataSource datasource;
	
	private User user1 = new User("johnwayne", "John Wayne", "imnotbatman", "ROLE_ADMIN", "john@wayne.pt", true);
	private User user2 = new User("billythekid", "Billy The Kid", "shootfirst", "ROLE_USER", "billy@kid.pt", true);
	private User user3 = new User("skinnyjoe", "Skinny Joe", "fatmilksucks", "ROLE_USER", "skinny@joe.pt", true);
	private User user4 = new User("tonytwotimes", "Tony Two Times", "twotimes", "ROLE_USER", "tonytwo@times.pt", true);
	private User user5 = new User("ojsimpson", "OJ Simpson", "iliketokill", "ROLE_USER", "ojsimpson@kill.pt", false);


	@Before
	public void init(){
		JdbcTemplate jdbc = new JdbcTemplate(datasource);
		
		jdbc.execute("delete from messages");
		jdbc.execute("delete from offers");
		jdbc.execute("delete from users");
		
	}
	
	@Test
	public void testSave(){
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
		usersDao.create(user5);
		
		Message message1 = new Message("Money", "Give me some money","Captain Hook" ,"captainhook@hookmyride.pt",user1.getUsername());
		messagesDao.saveOrUpdate(message1);
		
		
	}
	
}
