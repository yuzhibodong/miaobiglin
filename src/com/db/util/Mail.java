package com.db.util;

import java.sql.Blob;
import java.util.*;
import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import com.db.entity.Book;
import com.db.entity.Config;

public class Mail {
	// 定义发件人、收件人、SMTP服务器、用户名、密码、主题、内容等
	private String displayName;
	private String to;
	private String from;
	private String smtpServer;
	private String username;
	private String password;
	private String subject;
	private String content;
	private boolean ifAuth; // 服务器是否要身份认证
	//private String filPath = "";
	private String fileName = "";
	//private Vector file = new Vector(); // 用于保存发送附件的文件名的集合
	private List<Book> bookList;
	
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	/**
	 * 设置SMTP服务器地址
	 */
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	/**
	 * 设置发件人的地址
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * 设置显示的名称
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * 设置服务器是否需要身份认证
	 */
	public void setIfAuth(boolean ifAuth) {
		this.ifAuth = ifAuth;
	}

	/**
	 * 设置E-mail用户名
	 */
	public void setUserName(String username) {
		this.username = username;
	}

	/**
	 * 设置E-mail密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 设置接收者
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * 设置主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 设置主体内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 该方法用于收集附件名
	 */
//	public void addAttachfile(String fname) {
//		file.addElement(fname);
//	}

	public Mail() {

	}

	/**
	 * 初始化SMTP服务器地址、发送者E-mail地址、用户名、密码、接收者、主题、内容
	 */
	public Mail(String to, String subject,
			String content) {
		this.smtpServer = Config.SMTPSERVER;
		this.from = Config.FROM;
		this.displayName = Config.DISPLAYNAME;
		this.ifAuth = true;
		this.username = Config.USERNAME;
		this.password = Config.PASSWORD;
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	/**
	 * 初始化SMTP服务器地址、发送者E-mail地址、接收者、主题、内容
	 */
	public Mail(String smtpServer, String from, String displayName, String to,
			String subject, String content) {
		this.smtpServer = smtpServer;
		this.from = from;
		this.displayName = displayName;
		this.ifAuth = false;
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	/**
	 * 发送邮件
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public HashMap send() {
		HashMap map = new HashMap();
		map.put("state", "success");
		String message = "邮件发送成功！";
		Session session = null;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpServer);
		if (ifAuth) { // 服务器需要身份认证
			props.put("mail.smtp.auth", "true");
			SmtpAuth smtpAuth = new SmtpAuth(username, password);
			session = Session.getInstance(props, smtpAuth);
		} else {
			props.put("mail.smtp.auth", "false");
			session = Session.getInstance(props, null);
		}
		session.setDebug(true);
		Transport trans = null;
		try {
			Message msg = new MimeMessage(session);
			try {
				Address from_address = new InternetAddress(from, displayName);
				msg.setFrom(from_address);
			} catch (java.io.UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			Multipart mp = new MimeMultipart();
			MimeBodyPart mbp = new MimeBodyPart();
			mbp = new MimeBodyPart();
			mbp.setContent(content.toString(), "text/html;charset=utf-8");
			mp.addBodyPart(mbp);

			if (bookList != null && !bookList.isEmpty()) {
				for (Book book : bookList) {
					mbp = new MimeBodyPart();
					Blob bookBlob = book.getFile();
					InputStream inputStream=null;
					try {
						inputStream = bookBlob.getBinaryStream();
						byte[] buffer = new byte[inputStream.available()];
						inputStream.read(buffer);
						DataHandler dataHandler = new DataHandler(buffer,
								"application/octet-stream");
						mbp.setDataHandler(dataHandler);
						fileName = book.getName()+"."+book.getType();
						try {
							fileName = MimeUtility.encodeText(fileName);
						} catch (Exception e) {
							// TODO: handle exception
						}
						mbp.setFileName(fileName);
						mp.addBodyPart(mbp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (inputStream!=null) {
							try {
								inputStream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}

				}
			}
			// if(!file.isEmpty()){//有附件
			// Enumeration efile=file.elements();
			// while(efile.hasMoreElements()){
			// mbp=new MimeBodyPart();
			// filPath=efile.nextElement().toString(); //选择出每一个附件名
			// FileDataSource fds=new FileDataSource(filPath); //得到数据源
			// mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
			// fileName = fds.getName();
			// try {
			// fileName = MimeUtility.encodeText(fileName);
			//
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
			// mbp.setFileName(fileName); //得到文件名同样至入BodyPart
			//
			// mp.addBodyPart(mbp);
			// }
			// file.removeAllElements();
			// }

			msg.setContent(mp); // Multipart加入到信件
			msg.setSentDate(new Date()); // 设置信件头的发送日期
			// 发送信件
			msg.saveChanges();
			trans = session.getTransport("smtp");
			trans.connect(smtpServer, username, password);
			trans.sendMessage(msg, msg.getAllRecipients());
			trans.close();

		} catch (AuthenticationFailedException e) {
			map.put("state", "failed");
			message = "邮件发送失败！错误原因：\n" + "身份验证错误!";
			e.printStackTrace();
		} catch (MessagingException e) {
			message = "邮件发送失败！错误原因：\n" + e.getMessage();
			map.put("state", "failed");
			e.printStackTrace();
			Exception ex = null;
			if ((ex = e.getNextException()) != null) {
				System.out.println(ex.toString());
				ex.printStackTrace();
			}
		}
		// System.out.println("\n提示信息:"+message);
		map.put("message", message);
		return map;
	}

}

class SmtpAuth extends javax.mail.Authenticator {
	private String username, password;

	public SmtpAuth(String username, String password) {
		this.username = username;
		this.password = password;
	}

	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
		return new javax.mail.PasswordAuthentication(username, password);
	}
}
