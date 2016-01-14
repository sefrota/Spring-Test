package com.braincode.soft.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("usersDao")
public class UsersDao {

	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setDataSource(DataSource jdbc) {
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
	
	public Session session(){
		return sessionFactory.getCurrentSession();
	}
	
//	@Transactional
//	public boolean create(User user) {
//		
//		MapSqlParameterSource params = new MapSqlParameterSource();
//		
//		params.addValue("username", user.getUsername());
//		params.addValue("password", passwordEncoder.encode(user.getPassword()));
//		params.addValue("email", user.getEmail());
//		params.addValue("name", user.getName());
//		params.addValue("enabled", user.isEnabled());
//		params.addValue("authority", user.getAuthority());
//		
//		return jdbc.update("insert into users (username, name, password, email, enabled, authority) values (:username, :name, :password, :email, :enabled, :authority)", params) == 1;
//	}

	@Transactional
	public void create(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		session().save(user);
	}
	
	public boolean exists(String username) {
		return getUser(username)!=null;
//		Criteria crit = session().createCriteria(User.class);
//		//crit.add(Restrictions.eq("username", username));
//		crit.add(Restrictions.idEq(username));
//		User user = (User) crit.uniqueResult();
//		return null != user;
//		return jdbc.queryForObject("select count(*) from users where username=:username",
//				new MapSqlParameterSource("username", username), Integer.class) > 0;
	}
	@SuppressWarnings("unchecked")
	//@Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		
		//return jdbc.query("select * from users" , BeanPropertyRowMapper.newInstance(User.class));
		return session().createQuery("from User").list();
	}

	public User getUser(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.idEq(username));
		return (User) crit.uniqueResult();
	}
	
}
