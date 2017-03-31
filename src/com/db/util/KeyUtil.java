package com.db.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class KeyUtil {
	private Session session;
	private Transaction transaction;

	private void Init() {
		session = HibernateSessionFactory.getSession();
		//transaction = session.beginTransaction();

	}

	private void Destroy() {
		//transaction.commit();
		session.close();
	}
	
	public KeyUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public String testMasking(String nickName) {
		Init();
		StringBuilder reply=new StringBuilder();
		String hql="select new list(m.key,m.reply) from MaskingKey m";
		Query query = session.createQuery(hql);
		List<List<String>> maskingKeys = query.list();
		for (List<String> maskingKey : maskingKeys) {
			if (nickName.matches("\\S*("+maskingKey.get(0).replace(" ", ")\\S*|\\S*(")+")\\S*")) {
				reply.append(maskingKey.get(1)+"\n");
			}	
		}
		Destroy();
		return reply.toString();
	}
	
	public String testWhite(String nickName){
		Init();
		StringBuilder reply=new StringBuilder();
		String hql="select new list(w.key,w.reply) from WhiteKey w";
		Query query = session.createQuery(hql);
		List<List<String>> whiteKeys = query.list();
		for (List<String> whiteKey : whiteKeys) {
			if (nickName.matches("\\S*("+whiteKey.get(0).replace(" ", ")\\S*|\\S*(")+")\\S*")) {
				reply.append(whiteKey.get(1)+"\n");
			}	
		}
		Destroy();
		return reply.toString();
		
	}
	

}
