package com.braincode.soft.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.braincode.soft.validation.ValidEmail;

@Entity
@Table(name="offers")
public class Offer {
	@Id
	@GeneratedValue
	private int id;
	
	@Size(min=20, max=255,groups={PersistenceValidationGroup.class, FormValidationGroup.class})
	@Column(name="text")
	//must specify the column if the name of the atribute is 
	//different from the name of the column in the actual database.
	private String text;
	
	//It maps the relationship from this object (Offer)
	//to the object referred as the attribute(User)
	//In this case many offers can belong to one User
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	
	public Offer() {
		this.user = new User();
	}

	public Offer(User user, String text) {
		this.user = user;
		this.text = text;
	}
	
	

	public Offer(int id, User user, String text) {
		this.id = id;
		this.user = user;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getUsername(){
		return user.getUsername();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Offer other = (Offer) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Offer [id=" + id + ", text=" + text + ", user=" + user + "]";
	}

	
 
	
}
