package com.db.entity;

/**
 * WhiteKey entity. @author MyEclipse Persistence Tools
 */

//用户名的白名单，当用户设置为这个昵称的时候完成设置并抖个机灵
public class WhiteKey implements java.io.Serializable {

	// Fields

	private Integer wid;
	private String key;
	private String reply;

	// Constructors

	/** default constructor */
	public WhiteKey() {
	}

	/** full constructor */
	public WhiteKey(String key, String reply) {
		this.key = key;
		this.reply = reply;
	}

	// Property accessors

	public Integer getWid() {
		return this.wid;
	}

	public void setWid(Integer id) {
		this.wid = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getReply() {
		return this.reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

}