package com.book.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.db.entity.Book;
import com.db.util.BookDownloader;
import com.db.util.BookFinder;
import com.db.util.BookUploader;
import com.db.util.Mail;
import com.db.util.KeyUtil;
import com.db.util.UserUtil;
import com.weixin.entity.TextMessage;
import com.weixin.util.MessageUtil;

public class TextAction extends BaseAction {
	private String content = "";
	private String replyContent = "你这话我没法接";

	public void text() {

		map = (Map<String, String>) servletRequest.getAttribute("map");
		System.out.println(map == null);
		usrID = map.get("FromUserName");
		myID = map.get("ToUserName");
		content = map.get("Content").replaceAll("['\\\"]", "");
		content = content.trim();
		System.out.println("usr:" + content);	
		
		if (content.startsWith("绑定")) {
			Bind();
		} else if (content.startsWith("我是")) {
			SetNickName();
		} else if (content.startsWith("推送")) {
			PushBook();
		} else if (content.startsWith("喵大林")) {
			String message = MessageUtil.getHelpMessage(usrID, myID);
			ReplyXml(message);
		} else {
			replyContent = new BookFinder().Find(content);
			ReplyText();
		}
		
	}
	
	
	private void ReplyXml(String message) {
		try {
			printWriter = servletResponse.getWriter();
			printWriter.print(message);
			printWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	private void PushBook() {
		String emailString = new UserUtil().getEmaiAdress(usrID);
		//已绑定邮箱
		if (emailString != null) {
			BookDownloader bookDownloader = new BookDownloader();
			String[] strs = content.split("[^0-9]");
			int num=0;
			for (String string : strs) {
				if (string!=null&&string.length()!=0) {
					num++;
				}
			}
			if (num>5) {
				replyContent = "请不要一次性推送超过五本书籍～";
				ReplyText();
			}
			else {
				List<Book> books = bookDownloader.Download(strs);
				//图书存在
				if (books!=null&&books.size()!=0) {
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("\n");
					for (Book book : books) {
						stringBuilder.append(book.getBid() + "." + book.getName()
								+ "\n");
					}
					stringBuilder.append("已发送至你的邮箱" + emailString);
					replyContent = stringBuilder.toString();
					ReplyText();
					Mail mail = new Mail(emailString, "喵大林的推送", " ");
					mail.setBookList(books);
					mail.send();
				}
				else {
					//图书不存在
					replyContent="序号有误推送失败\n发送推送指令获取图书\n例如:推送1,2,3";
					ReplyText();
				}
			}
		} else {
			//未绑定邮箱
			replyContent = "对不起你尚未绑定邮箱，推送失败\n请发送绑定指令进行绑定\n例如:\n绑定123@kindle.cn";
			ReplyText();
		}
	}


	private void SetNickName() {
		String nickName = content.trim().replaceAll("我是", "");
		if (nickName.length()==0) {
			replyContent = "昵称设置失败\n\n请发送指令重新设置\n例如:我是皮卡丘";
		}
		else {
			KeyUtil maskingKeyUtil = new KeyUtil();
			String maskingTest = maskingKeyUtil.testMasking(nickName);
			// 昵称包含敏感词
			if (!maskingTest.equals("")) {
				replyContent =   maskingTest + "\n\n(つд∩)\n昵称设置失败\n请发送指令重新设置\n例如:我是皮卡丘";
			}
			// 昵称不包含敏感词
			else {
				String whiteTest=maskingKeyUtil.testWhite(nickName);
				UserUtil userUtil = new UserUtil();
				boolean flag = userUtil.setNickName(usrID, nickName);
				if (flag) {
					replyContent = whiteTest+"\n你已成功设置了昵称";
				} else {
					replyContent = "已存在该昵称\n请发送指令重新设置\n例如:我是皮卡丘";
				}
			}
		}
		
		ReplyText();
	}


	private void Bind() {
		String emailString = content.trim().replaceAll("绑定", "");
		if (emailString.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")) {
			System.out.println("matchs");
			UserUtil userUtil = new UserUtil();
			userUtil.setEmail(usrID, emailString);
			replyContent = "你已成功绑定邮箱" + emailString + "\n" + "如有变动请重新绑定";
		}
		else {
			replyContent="邮箱地址错误，请发送绑定指令进行绑定\n例如:绑定123@kindle.cn";
		}
		ReplyText();
	}
	


	
	private void ReplyText() {
		System.out.println(replyContent);
		String nickName=new UserUtil().getNickName(usrID);
		if (nickName!=null&&nickName.length()!=0) {
			replyContent = "萌萌哒的"+nickName +"同学:\n"+ replyContent;
			
		}
		replyContent = replyContent +"\n\n(๑•́ ∀ •̀๑)\n回复喵大林召唤小窝使用手册";
		TextMessage reply = new TextMessage(usrID, myID, new Date().getTime()
				+ "", "text", replyContent, null);
		try {
			
			printWriter = servletResponse.getWriter();
			String xml = MessageUtil.TextMessageToXml(reply);
			printWriter.print(xml);
			printWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
