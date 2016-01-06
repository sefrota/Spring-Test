package com.braincode.soft.dao;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.braincode.soft.validation.ValidEmail;

public class User {
	
	@NotBlank(message="The username cannot be blank")
	@Size(min=8, max=15, message="The username size must be between 8 and 15 characters long")
	@Pattern(regexp="^\\w{8,}$", message="The username must contain only alphanumeric characters")
	private String username;
	
	@NotBlank
	@Pattern(regexp="^\\S+$")
	@Size(min=8, max=15, message="The password size must be between 8 and 15 characters long")
	private String password;
	private String authority;
	
	@ValidEmail
	private String email;
	private boolean enabled = false;

	public User() {
	}

	public User(String username, String password, String authority, String email, boolean enabled) {
		this.username = username;
		this.password = password;
		this.authority = authority;
		this.email = email;
		this.enabled = enabled;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
