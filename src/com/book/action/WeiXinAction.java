package com.book.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import com.db.util.UserUtil;
import com.weixin.entity.TextMessage;
import com.weixin.util.MessageUtil;
import com.weixin.util.WeiXinChecker;

public class WeiXinAction extends BaseAction {

	private String token = "zhenghejun";

	// 不同的Action类型对应的返回值
	private final static String TEXTACTION = "textAction";
	private final static String IMAGEACTION = "imageAction";
	private final static String VOICEACTION = "voiceAction";
	private final static String VIDEOACTION = "videoAction";
	private final static String SHORTVIDEOACTION = "shortvideoAction";
	private final static String LOCATIONACTION = "locationAction";

	public WeiXinAction() {

	}

	public String weixin() {

		String method = servletRequest.getMethod();
		if (method.equals("GET")) {
			WeiXinChecker.Check(servletRequest, servletResponse, token);
			return null;
		} else {
			try {
				map = MessageUtil.xmlToMap(servletRequest);
				String msgType = map.get("MsgType");
				usrID = map.get("FromUserName");
				myID = map.get("ToUserName");
				System.out.println(msgType);
				// 接收的是文本信息
				if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
					System.out.println("leave weixinAction");
					servletRequest.setAttribute("map", map);
					return TEXTACTION;
				}
				else if (MessageUtil.MESSAGE_IMAGE.equals(msgType)) {
						ReplyText("我怀疑你要搞事情，等我喊喵大林回来斗图！");
				}
				else if (MessageUtil.MESSAGE_LOCATION.equals(msgType)) {
						ReplyText("站着别动，我去找你。");
				}
				else if (MessageUtil.MESSAGE_VIDEO.equals(msgType)){
					ReplyText("你发了什么小视频，我还小不敢点开看(⊃д⊂)");
				}
				else if (MessageUtil.MESSAGE_VOICE.equals(msgType)) {
					ReplyText("我肯定不是第一个说你声音好听的人(⊃д⊂)");
					
				}
				else if (MessageUtil.MESSAGE_EVNET.equals(msgType)) {
					String eventType = map.get("Event");
					if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
						String message = MessageUtil
								.getHelpMessage(usrID, myID);
						ReplyXml(message);
					} else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
						String message = MessageUtil
								.getHelpMessage(usrID, myID);
						ReplyXml(message);
					} else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
						String message = MessageUtil
								.getHelpMessage(usrID, myID);
						ReplyXml(message);
					} else if (MessageUtil.MESSAGE_SCANCODE.equals(eventType)) {
						String message = MessageUtil
								.getHelpMessage(usrID, myID);
						ReplyXml(message);
					} else {
						String message = MessageUtil
								.getHelpMessage(usrID, myID);
						ReplyXml(message);
					}
					return null;
				} else {
					String message = MessageUtil.getHelpMessage(usrID, myID);
					ReplyXml(message);
					return null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
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

	private void ReplyText(String replyContent)
	{
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
