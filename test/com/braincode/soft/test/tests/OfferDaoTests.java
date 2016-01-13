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
public class OfferDaoTests {

	@Autowired
	private OffersDao offersDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private DataSource datasource;
	
	@Before
	public void init(){
		JdbcTemplate jdbc = new JdbcTemplate(datasource);
		
		jdbc.execute("delete from offers");
		jdbc.execute("delete from users");
	}
	
	@Test
	public void createOffer(){
		
		User user = new User("johnwayne", "John Wayne", "hellohello", "ROLE_ADMIN", "johnwayne@offers.pt", true);
		Offer offer = new Offer(user, "John Wayne likes to offer");
		usersDao.create(user);
		
		assertTrue("The offer should be created",offersDao.create(offer));
		
		List<Offer> offers = offersDao.getOffers();
		assertEquals("The number of offers should be 1",1, offers.size());
		
		assertEquals("The created offer should be equal to the retrieved offer", offer, offers.get(0));
		
		offer = offers.get(0);
		
		offer.setText("John Wayne doesn't like to offer anymore");
		
		assertTrue("The offer should be updated", offersDao.update(offer));
		
		Offer updated = offersDao.getOffer(offer.getId());
		
		assertEquals("The updated offer should match the retrieved offer", offer, updated);
		
		Offer offer2 = new Offer(user, "This is a test offer 2");
		
		assertTrue("Offer creation should return true", offersDao.create(offer2));
		
		List<Offer> userOffers = offersDao.getOffer(user.getUsername());
		
		assertEquals("Should be 2 offers for user",2, userOffers.size());
		
		List<Offer> secondList = offersDao.getOffers();
		
		for(Offer current: secondList){
			Offer retrieved = offersDao.getOffer(current.getId());
			
			assertEquals("Offer by Id should match offer from list.", current, retrieved);
			offersDao.delete(retrieved.getId());
		}
		
		List<Offer> empty = offersDao.getOffers();
		
		assertEquals("The list should be empty", 0, empty.size());
	}
}
