package com.udacity.jwdnd.course1.cloudstorage.model;


public class Credentials {
	private Integer credentialId;
	private String url;
	private String username;
	private String key;
	private String password;
	private String passwordTxt;
	private Integer userId;

	public Credentials() {
	}

	public Credentials(Integer userId, String password, String key, String username, String url, Integer credentialId) {
		this.userId = userId;
		this.password = password;
		this.key = key;
		this.username = username;
		this.url = url;
		this.credentialId = credentialId;
	}

	public Integer getCredentialId() {
		return credentialId;
	}

	public void setCredentialId(Integer credentialId) {
		this.credentialId = credentialId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPasswordTxt() {
		return passwordTxt;
	}

	public void setPasswordTxt(String passwordTxt) {
		this.passwordTxt = passwordTxt;
	}
}
