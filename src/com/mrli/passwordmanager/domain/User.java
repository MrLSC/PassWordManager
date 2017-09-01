package com.mrli.passwordmanager.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private String id;
	private String username;
	private String password;
	private Set passwordManagers = new HashSet(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String username, String password, Set passwordManagers) {
		this.username = username;
		this.password = password;
		this.passwordManagers = passwordManagers;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set getPasswordManagers() {
		return this.passwordManagers;
	}

	public void setPasswordManagers(Set passwordManagers) {
		this.passwordManagers = passwordManagers;
	}

}