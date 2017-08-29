package com.zds.weixin.pojo;
/**
 * 图文消息的里层实体
 * 
 * @author @DT人 2017年7月27日 下午7:20:46
 *
 */
public class News {
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
}
