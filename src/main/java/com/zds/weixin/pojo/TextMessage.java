package com.zds.weixin.pojo;
/**
 * 文本消息实体
 * 
 * 继承baseMessage类
 * @author @DT人 2017年7月27日 下午5:42:56
 *
 */
public class TextMessage extends BaseMessage {

	private String Content;
	private String MsgId;
	
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
}
