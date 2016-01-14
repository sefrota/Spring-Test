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
	
	private User user1 = new User("johnwayne", "John Wayne", "imnotbatman", "ROLE_ADMIN", "john@wayne.pt", true);
	private User user2 = new User("billythekid", "Billy The Kid", "shootfirst", "ROLE_USER", "billy@kid.pt", true);
	private User user3 = new User("skinnyjoe", "Skinny Joe", "fatmilksucks", "ROLE_USER", "skinny@joe.pt", true);
	private User user4 = new User("tonytwotimes", "Tony Two Times", "twotimes", "ROLE_USER", "tonytwo@times.pt", true);
	private User user5 = new User("ojsimpson", "OJ Simpson", "iliketokill", "ROLE_USER", "ojsimpson@kill.pt", false);

	private Offer offer1 = new Offer(user1, "Got a problem with indians? Call me and i'll bring you their scalps");
	private Offer offer2 = new Offer(user2, "I'll do it twice as fast as John Wayne");
	private Offer offer3 = new Offer(user3, "Foget bout tha money, just give a me some meataballs");
	private Offer offer4 = new Offer(user4, "I'll doing two times but you got to pay two times too");
	private Offer offer5 = new Offer(user5, "Indians? It's in the bag");
	
	@Before
	public void init(){
		JdbcTemplate jdbc = new JdbcTemplate(datasource);
		
		jdbc.execute("delete from offers");
		jdbc.execute("delete from users");
	}
	
	@Test
	public void create(){
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
		usersDao.create(user5);
		
		offersDao.saveOrUpdate(offer1);
		
		List<Offer> offers1 = offersDao.getOffers();
		assertEquals("Should be 1 offer at this point", 1, offers1.size());
		assertEquals("Retrieved offer should be the same as the inserted one", offer1, offers1.get(0));
		
		offersDao.saveOrUpdate(offer2);
		offersDao.saveOrUpdate(offer3);
		offersDao.saveOrUpdate(offer4);
		offersDao.saveOrUpdate(offer5);

		List<Offer> offers2 = offersDao.getOffers();
		assertEquals("Should be 4 offers for users that are enabled", 4, offers2.size());
	}
	
	@Test
	public void testGetUsername(){
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
		usersDao.create(user5);
		
		offersDao.saveOrUpdate(offer1);
		offersDao.saveOrUpdate(offer2);
		offersDao.saveOrUpdate(offer3);
		offersDao.saveOrUpdate(offer4);
		offersDao.saveOrUpdate(offer5);
		
		List<Offer> offersUsername = offersDao.getOffer(offer1.getUser().getUsername());
		assertEquals("There should be one offer associated with the offer1 user", 1, offersUsername.size());
	
		List<Offer> offersInvalidUsername = offersDao.getOffer("aoaoaoaooa");
		assertEquals("There should be no offers associated with this user", 0, offersInvalidUsername.size());
	}
	
	@Test
	public void testUpdate(){
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
		usersDao.create(user5);
		
		offersDao.saveOrUpdate(offer1);
		offersDao.saveOrUpdate(offer2);
		offersDao.saveOrUpdate(offer3);
		offersDao.saveOrUpdate(offer4);
		offersDao.saveOrUpdate(offer5);
		
		offer3.setText("Don feel like meataball no more. Bring me a nice lasagna");
		offersDao.saveOrUpdate(offer3);
		
		Offer retrieved = offersDao.getOffer(offer3.getId());
		assertEquals("Retrieved offer should be updated.", offer3, retrieved);
	}
	
	
	
	@Test
	public void testDelete(){
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
		usersDao.create(user5);
		
		offersDao.saveOrUpdate(offer1);
		offersDao.saveOrUpdate(offer2);
		offersDao.saveOrUpdate(offer3);
		offersDao.saveOrUpdate(offer4);
		offersDao.saveOrUpdate(offer5);
		
		
		
		Offer retrieved1 = offersDao.getOffer(offer2.getId());
		
		assertNotNull("Offer with ID " + retrieved1.getId() +" should not "
				+ "be null (deleted, actual)", retrieved1);
		
		offersDao.delete(offer2.getId());
		
		Offer retrieved2 = offersDao.getOffer(offer2.getId());
		
		assertNull("Offer with ID " + retrieved1.getId() +" should "
				+ "be null (deleted, actual)", retrieved2);
				
	}
	
	@Test
	public void createOffer(){
		
		User user = new User("johnwayne", "John Wayne", "hellohello", "ROLE_ADMIN", "johnwayne@offers.pt", true);
		Offer offer = new Offer(user, "John Wayne likes to offer");
		usersDao.create(user);
		
		offersDao.saveOrUpdate(offer);
		
		List<Offer> offers = offersDao.getOffers();
		assertEquals("The number of offers should be 1",1, offers.size());
		
		assertEquals("The created offer should be equal to the retrieved offer", offer, offers.get(0));
		
		offer = offers.get(0);
		
		offer.setText("John Wayne doesn't like to offer anymore");
		
		offersDao.saveOrUpdate(offer);
		
		Offer updated = offersDao.getOffer(offer.getId());
		
		assertEquals("The updated offer should match the retrieved offer", offer, updated);
		
		Offer offer2 = new Offer(user, "This is a test offer 2");
		
		offersDao.saveOrUpdate(offer2);
		
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
