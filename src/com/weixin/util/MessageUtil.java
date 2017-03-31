package com.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.weixin.entity.Image;
import com.weixin.entity.ImageMessage;
import com.weixin.entity.News;
import com.weixin.entity.NewsMessage;
import com.weixin.entity.TextMessage;

public class MessageUtil {
	
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVNET = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE= "scancode_push";
	
	
	//第一次关注和获取帮助的图文
	public static String getHelpMessage(String toUserName,String fromUserName)
	{
		String message = null;
		News news = new News();
		news.setTitle("使用屯书小窝的正确姿势");
		news.setDescription("使用屯书小窝的正确姿势");
		news.setPicUrl("http://miaobiglin.ngrok.cc/webtest/image/bigcat.jpg");
		news.setUrl("http://u372325.viewer.maka.im/k/YT8PXK84");
		List<News> newsList = new ArrayList<News>();
		newsList.add(news);
		News news2 = new News();
		news2.setTitle("点击调教喵大林");
		news2.setDescription("用户测试反馈表");
		news2.setPicUrl("http://miaobiglin.ngrok.cc/webtest/image/twocat.jpg");
		news2.setUrl("https://www.sojump.hk/jq/10938463.aspx");
		newsList.add(news2);
		
		message = getNewsMessageXml(toUserName, fromUserName, newsList);
		return message;
	}
	
	//图文消息转换为xml
	private static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
	
	//图文消息的组装
	public static String getNewsMessageXml(String toUserName,String fromUserName,List<News> newsList){
		String message = null;
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(toUserName);
		newsMessage.setFromUserName(fromUserName);
		newsMessage.setCreateTime(new Date().getTime()+"");
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		message = newsMessageToXml(newsMessage);
		return message;
	}
	
	//图片消息转换为xml
	private static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	//图片信息的封装
	public static String getImageMessageXml(String toUserName,String fromUserName,String mediaId){
		String message = null;
		Image image = new Image();
		image.setMediaId(mediaId);
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(fromUserName);
		imageMessage.setToUserName(toUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime()+"");
		imageMessage.setImage(image);
		message = imageMessageToXml(imageMessage);
		return message;
	}
	

	//xml转为map集合
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream inputStream =  request.getInputStream();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();
		List<Element> list = root.elements(); 
		for (Element element : list) {
			map.put(element.getName(), element.getText());
		}
		inputStream.close();
		return map;
	}
	

	
	
	//TextMessage转换为xml
	public static String TextMessageToXml(TextMessage textMessage)
	{
		XStream xStream = new XStream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	 
}
