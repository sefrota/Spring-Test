package com.braincode.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.braincode.soft.dao.Message;
import com.braincode.soft.dao.MessagesDao;
import com.braincode.soft.dao.User;
import com.braincode.soft.dao.UsersDao;

@Service("usersService")
public class UsersService {
	
	//Associa o bean relativo ao DAO ao objecto UsersDao através de injecção de dependencias.
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private MessagesDao messagesDao;
	
	
	//Cria o utilizador
	public void create(User user) {
		usersDao.create(user);
	}
	
	//Verifica se o utilizador passado como argumento já se encontra registado na base de dados
	public boolean exists(String username) {
		return usersDao.exists(username);
	}
	
	//Retorna todos os utilizadores registados na base de dados
	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		return usersDao.getAllUsers();
	}

	public void sendMessage(Message message){
		messagesDao.saveOrUpdate(message);
	}
	
	public User getUser(String username){
		return usersDao.getUser(username);
	}
}
