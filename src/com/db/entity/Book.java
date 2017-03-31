package com.db.entity;

import java.sql.Blob;

/**
 * Book entity. @author MyEclipse Persistence Tools
 */

public class Book implements java.io.Serializable {

	// Fields

	private Integer bid;
	private String name;
	private String author;
	private Blob file;
	private String type;

	// Constructors

	/** default constructor */
	public Book() {
	}

	/** full constructor */
	public Book(String name, String author, Blob file) {
		this.name = name;
		this.author = author;
		this.file = file;
	}

	// Property accessors

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getBid() {
		return this.bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Blob getFile() {
		return this.file;
	}

	public void setFile(Blob file) {
		this.file = file;
	}

}