package com.braincode.soft.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.braincode.soft.dao.User;
import com.braincode.soft.dao.UsersDao;

@Service("usersService")
public class UsersService {
	
	private UsersDao usersDao;
	
	//Associa o bean relativo ao DAO ao objecto UsersDao através de injecção de dependencias.
	@Autowired
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
	//Cria o utilizador
	public void create(User user) {
		usersDao.create(user);
	}
	
	//Verifica se o utilizador passado como argumento já se encontra registado na base de dados
	public boolean exists(String username) {
		return usersDao.exists(username);
	}
	
	//Retorna todos os utilizadores registados na base de dados
	public List<User> getAllUsers() {
		return usersDao.getAllUsers();
	}

}
