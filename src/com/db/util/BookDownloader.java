package com.db.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.db.entity.Book;

public class BookDownloader {
	private Session session;
	
	public BookDownloader() {
		// TODO Auto-generated constructor stub
	}
	
	private void Init() {
		session = HibernateSessionFactory.getSession();
	}
	
	private void Destroy() {
		session.close();
	}
	

	
	public List<Book> Download(String[] strs) {
		Init();
		List<Book> list = new ArrayList<Book>();
		for(int i = 0; i<strs.length;i++)
		{
			if (strs[i].length()==0) {
				continue;
			}
			Book book = (Book) session.get(Book.class,Integer.parseInt(strs[i]));
			if (book!=null) {
				list.add(book);
			}
		}
		Destroy();
		return list;
	}
}
