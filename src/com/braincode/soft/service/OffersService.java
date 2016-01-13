package com.braincode.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.braincode.soft.dao.Offer;
import com.braincode.soft.dao.OffersDao;

@Service("offersService")
public class OffersService {
	
	private OffersDao offersDao;
	
	@Autowired
	public void setOffersDao(OffersDao offersDao) {
		this.offersDao = offersDao;
	}

	public List<Offer> getCurrent() {
		return offersDao.getOffers();
	}
	
	@Secured({"ROLE_USER", "ROLE_ADMIN"})
	public void create(Offer offer) {
		offersDao.create(offer);
	}

	public boolean hasOffer(String name) {
		
		if(null==name) return false;
		
		List<Offer> offers = offersDao.getOffer(name);
		
		return offers.size() > 0 ? true : false;
	}

	public Offer getOffers(String username) {
		if(null == username)
			return null;
		
		List<Offer> offers = offersDao.getOffer(username);
		if(offers.size()== 0)
			return null;
		
		return offers.get(0);
	}

	public void saveOrUpdate(Offer offer) {
		if(offer.getId()!=0)
			offersDao.update(offer);
		else
			offersDao.create(offer);
		
	}

	public void delete(int id) {
		offersDao.delete(id);
	}

}
