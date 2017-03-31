package com.db.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class BookFinder {
	
	private Session session;
	
	public BookFinder() {
	
	}
	
	private void Init() {
		session = HibernateSessionFactory.getSession();
	}
	
	private void Destroy() {
		session.close();
	}
	
	public String Find(String content)
	{
		Init();
		String keyword = content.replaceAll("找", "");
		StringBuffer result = new StringBuffer();
		String hql = "select new list(b.bid,b.name) from Book b where b.name like'%"+keyword+"%'";
		Query query = session.createQuery(hql);
		List<List> books = query.list();
		if (books!=null&&books.size()!=0) {
			result.append("你要找的书是不是：\n");
			for (List book : books) {
				result.append(book.get(0).toString()).append(":").append(book.get(1).toString()).append("\n");
			}
			result.append("\n回复推送指令获取图书\n例如推送1，2，3");
		}
		else {
			result.append("很抱歉你所搜索的图书尚未收录～");
		}
		
		Destroy();
		return result.toString();
	}
	
	public boolean isExist(String name) {
		
		Init();
		String hql = "select b.name from Book b where b.name ='"+name+"'";
		Query query = session.createQuery(hql);
		List<String> books = query.list();
		boolean result = books!=null&&!books.isEmpty();
		return result;
	}

}
