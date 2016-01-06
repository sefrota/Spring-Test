package com.braincode.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public void create(Offer offer) {
		offersDao.create(offer);
	}

	public void throwTestException() {
		offersDao.getOffer(99999);
	}
}
