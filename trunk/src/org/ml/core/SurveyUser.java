package org.ml.core;

import java.util.Date;

public class SurveyUser {
	
	private String firstName;
	private String lastName;
	private String email;
	private Date dateCreated;
	private Date dateSubmited;
	private String key;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public void setDateSubmited(Date dateSubmited) {
		this.dateSubmited = dateSubmited;
	}
	public Date getDateSubmited() {
		return dateSubmited;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	

}
