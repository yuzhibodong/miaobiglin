package com.db.util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.db.entity.User;

public class UserUtil {

	private Session session;
	private Transaction transaction;

	private void Init() {
		session = HibernateSessionFactory.getSession();
		transaction = session.beginTransaction();

	}

	private void Destroy() {
		transaction.commit();
		session.close();
	}

	public boolean isOpenIdExist(String openId) {
		String hql="select u.openId from User u where u.openId='" + openId+"'";
		Query query = session.createQuery(hql);
		String result = (String) query.uniqueResult();
		return result!=null;
	}
	
	public String getOpenIdByNickName(String nickName) {
		String hql="select u.openId from User u where u.nickName='" + nickName+"'";
		Query query = session.createQuery(hql);
		String result = (String) query.uniqueResult();
		return result;
	}
	
	public String getEmaiAdress(String openId) {
		Init();
		String hql = "select u.email from User u where u.openId='" + openId+"'";
		Query query = session.createQuery(hql);
		String emailString = (String) query.uniqueResult();
		System.out.println(emailString);
		Destroy();
		return emailString;
	}

	public void setEmail(String openId, String email) {
		Init();
		if (isOpenIdExist(openId)) {
			String hql = "update User u set u.email ='"+email+"' where u.openId ='" + openId+"'";
			Query query = session.createQuery(hql);
			query.executeUpdate();
		}
		else {
			User u = new User(openId, null, email);
			session.save(u);
		}
		Destroy();
	}
	
	public String getNickName(String openId) {
		String nickName=null;
		Init();
		String hql = "select u.nickName from User u where u.openId='" + openId+"'";
		Query query = session.createQuery(hql);
		nickName = (String) query.uniqueResult();
		Destroy();
		return nickName;
	}
	
	public boolean setNickName(String openId,String nickName)
	{
		Init();
		if (isOpenIdExist(openId)) {
			String idOfTheName=getOpenIdByNickName(nickName);
			//用户存在，昵称没有人使用
			if (idOfTheName==null) {
				String hql = "update User u set u.nickName ='"+nickName+"' where u.openId ='" + openId+"'";
				Query query = session.createQuery(hql);
				query.executeUpdate();
				Destroy();
				return true;
			}
			else {//用户存在，昵称被使用
				if (idOfTheName.equals(openId)) {
					//昵称是自己的
					Destroy();
					return true;
				}
				else {
					//昵称是别人的
					Destroy();
					return false;
				}
				
			}
		}
		else {//用户不存在
			String idOfTheName=getOpenIdByNickName(nickName);
			if (idOfTheName!=null) {
				//昵称是别人的
				Destroy();
				return false;
			}
			else {
				//昵称是可以用的
				User u = new User(openId, nickName, null);
				session.save(u);
				Destroy();
				return true;
			}
			
		}
		
	}

	
}
