package com.weixin.entity;

public class TextMessage extends WeiXinMessage {

	private String Content;
	private String MsgId;
	
	public TextMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public TextMessage(String toUserName, String fromUserName,
			String createTime, String msgType, String content, String msgId) {
		ToUserName = toUserName;
		FromUserName = fromUserName;
		CreateTime = createTime;
		MsgType = msgType;
		Content = content;
		MsgId = msgId;
	}

	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	@Override
	public String toString() {
		return "TextMessage [ToUserName=" + ToUserName + ", FromUserName="
				+ FromUserName + ", CreateTime=" + CreateTime + ", MsgType="
				+ MsgType + ", Content=" + Content + ", MsgId=" + MsgId + "]";
	}
}
