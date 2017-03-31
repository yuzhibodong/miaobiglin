package com.db.entity;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer uid;
	private String openId;
	private String nickName;
	private String email;
	private String state;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(String openId, String nickName, String email) {
		this.openId = openId;
		this.nickName = nickName;
		this.email = email;
	}

	// Property accessors
	
	
	
	

	public Integer getUid() {
		return this.uid;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getOpenId() {
		return this.openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}