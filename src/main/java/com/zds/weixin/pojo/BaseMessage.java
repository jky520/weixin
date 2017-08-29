package com.zds.weixin.pojo;
/**
 * 重复使用的属性抽出来作为父类
 * 
 * @author @DT人 2017年7月27日 下午7:22:59
 *
 */
public class BaseMessage {
	private String ToUserName;
	private String FromUserName;
	private String MsgType;
	private Long CreateTime;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
}
