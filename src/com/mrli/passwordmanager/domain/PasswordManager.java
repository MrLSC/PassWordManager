package com.mrli.passwordmanager.domain;

/**
 * PasswordManager entity. @author MyEclipse Persistence Tools
 */

public class PasswordManager implements java.io.Serializable {

	// Fields

	private String id;
	private User user;
	private String accountType;
	private String username;
	private String password;
	private String unlockme;

	// Constructors

	/** default constructor */
	public PasswordManager() {
	}

	/** full constructor */
	public PasswordManager(User user, String accountType, String username,
			String password, String unlockme) {
		this.user = user;
		this.accountType = accountType;
		this.username = username;
		this.password = password;
		this.unlockme = unlockme;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public String getUnlockme() {
		return this.unlockme;
	}

	public void setUnlockme(String unlockme) {
		this.unlockme = unlockme;
	}

}