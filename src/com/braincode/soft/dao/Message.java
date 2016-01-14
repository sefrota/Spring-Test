package com.braincode.soft.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.braincode.soft.validation.ValidEmail;

@Entity
@Table(name="messages")
public class Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8346435489607687946L;
	@Id
	@GeneratedValue
	private int id;
	@Size(min=5, max=100)
	private String subject;
	@Size(min=5, max=1000)
	private String content;
	
	//Sender's name
	@Size(min=8, max=60)
	private String name;
	//Sender's email address
	@ValidEmail
	private String email;
	//Recipient's username of the message
	private String username;
	
	public Message(){
		
	}
	
	public Message(String subject, String content, String name, String email, String username) {
		this.subject = subject;
		this.content = content;
		this.name = name;
		this.email = email;
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Message [subject=" + subject + ", content=" + content + ", name=" + name + ", email=" + email
				+ ", username=" + username + "]";
	}
	
	
}
