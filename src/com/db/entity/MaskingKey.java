package com.db.entity;

/**
 * MaskingKey entity. @author MyEclipse Persistence Tools
 */

//用户名的黑名单，当用户设置为这个昵称的时候拒绝设置并提醒用户不要搞事情
public class MaskingKey implements java.io.Serializable {

	// Fields

	private Integer mid;
	private String key;
	private String reply;

	// Constructors

	/** default constructor */
	public MaskingKey() {
	}

	/** full constructor */
	public MaskingKey(String key, String reply) {
		this.key = key;
		this.reply = reply;
	}

	// Property accessors

	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer id) {
		this.mid = id;
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