package com.book.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware {
	protected HttpServletRequest servletRequest;
	protected HttpServletResponse servletResponse;
	protected Map<String, Object> session;
	protected Map<String, String> map;
	

	protected String usrID="";
	protected String myID="";
	protected PrintWriter printWriter;
	
	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
		try {
			this.servletRequest.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
		this.servletResponse.setCharacterEncoding("utf-8");

	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
