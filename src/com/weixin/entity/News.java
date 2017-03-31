package com.weixin.entity;

public class News {

	public News() {

	}

	public News(String title, String description, String picUrl, String url) {
		Title = title;
		Description = description;
		PicUrl = picUrl;
		Url = url;
	}

	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
