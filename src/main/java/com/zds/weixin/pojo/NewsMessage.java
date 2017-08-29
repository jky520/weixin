package com.zds.weixin.pojo;

import java.util.List;

/**
 * 图文消息的外层实体
 * 
 * @author @DT人 2017年7月27日 下午7:18:15
 *
 */
public class NewsMessage extends BaseMessage {

	private Integer ArticleCount; // 数量
	
	private List<News> Articles; // 消息体

	public Integer getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}
	public List<News> getArticles() {
		return Articles;
	}
	public void setArticles(List<News> articles) {
		Articles = articles;
	}
}
