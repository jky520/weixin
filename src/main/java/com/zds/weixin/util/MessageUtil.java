package com.zds.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.zds.weixin.pojo.Image;
import com.zds.weixin.pojo.ImageMessage;
import com.zds.weixin.pojo.Music;
import com.zds.weixin.pojo.MusicMessage;
import com.zds.weixin.pojo.News;
import com.zds.weixin.pojo.NewsMessage;
import com.zds.weixin.pojo.TextMessage;

/**
 * 消息对象的转换工具类
 * 
 * @author @DT人 2017年7月27日 下午5:17:57
 *
 */
public class MessageUtil {

	public static final String TEXT_MESSAGE = "text"; // 文本消息类型
	public static final String NEWS_MESSAGE = "news"; // 图文消息类型
	public static final String IMAGE_MESSAGE = "image"; // 图片消息类型
	public static final String MUSIC_MESSAGE = "music"; // 音乐消息类型
	public static final String VOICE_MESSAGE = "voice"; // 语音消息类型
	public static final String VIDEO_MESSAGE = "video"; // 视频消息类型
	public static final String LINK_MESSAGE = "link"; // 连接类型
	public static final String LOCATION_MESSAGE = "location"; // 地图消息类型
	
	public static final String EVENT_MESSAGE = "event"; // 事件类型
	public static final String SUBSCRIBE_MESSAGE = "subscribe"; // 关注事件类型
	public static final String UNSUBSCRIBE_MESSAGE = "unsubscribe"; // 取消关注事件类型
	public static final String CLICK_MESSAGE = "CLICK"; // 点击事件类型
	public static final String VIEW_MESSAGE = "VIEW"; // 查看事件类型
	public static final String SCANCODE_MESSAGE = "scancode_push"; // 扫码事件类型
	
	/**
	 * xml转换成map
	 * 
	 * @param req
	 * @return map
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xml2Map(HttpServletRequest req) throws IOException, DocumentException {
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		
		InputStream is = req.getInputStream();
		Document doc = reader.read(is);
		
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		
		is.close();
		return map;
	}
	
	/**
	 * 将文本消息对象转换成XML
	 * @param textMessage
	 * @return
	 */
	public static String textMessage2Xml(TextMessage textMessage) {
		XStream  xstream = new XStream();
		/**
		 * 由于消息textMessage消息传来的格式如下，所以需要alias方法把根目录替换成微信的xml格式
		 * <com.zds.weixin.pojo.TextMessage>
			  <FromUserName>gh_04f2bd8de07e</FromUserName>
			  <CreateTime>1501150150055</CreateTime>
			  <MsgType>text</MsgType>
			  <Content>您发送的消息是：aa</Content>
		   </com.zds.weixin.pojo.TextMessage>
		 */
		xstream.alias("xml", textMessage.getClass()); 
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 拼接文本消息的方法
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName, String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setContent(content);
		text.setMsgType(MessageUtil.TEXT_MESSAGE); // 设置成文本消息
		text.setCreateTime(new Date().getTime());
		return textMessage2Xml(text);
	}
	
	/**
	 * 构造主菜单的方法
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照菜单提示进行操作:\n\n");
		sb.append("1、个人介绍\n");
		sb.append("2、业务范围的介绍\n\n");
		sb.append("回复?调出此菜单。");
		return sb.toString();
	}
	
	/**
	 * 第一个菜单的内容
	 * @return
	 */
	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("本人从事IT行业已好几年，本人纯属技术爱好，对系统研发有深入的研究、对大数据以及云计算也颇为爱好，从事高端企业级培训也有一定的经验");
		return sb.toString();
	}
	/**
	 * 第二个菜单的内容
	 * @return
	 */
	public static String secondMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("咱们有一个自己的研发团队，主要从事企业的管理系统、移动appd的研发、以及大数据平台的构建与数据分析以及在设计方面也有资深的设计师");
		return sb.toString();
	}
	
	/**
	 * 将图文消息对象转换成XML
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessage2Xml(NewsMessage newsMessage) {
		XStream  xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass()); 
		xstream.alias("item", new News().getClass()); 
		return xstream.toXML(newsMessage);
	}
	
	/**
	 * 图文消息的组装方法
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) {
		String message = null;
		List<News> newList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		
		News news = new News();
		news.setTitle("spring轻量企业开发框架入门");
		news.setPicUrl("http://2696008e.ngrok.io/img/spring.jpg");
		news.setDescription("1、Spring提供了一个全面的多层框架，可在应用程序的所有层使用。这一特性，有助于整合整个应用程序和现成的组件，以及集成最适合的单层框架（如Hibernate或struts等）\n"+ 
							"2、Spring框架提供一个基于POJO的简单编程模型，并且由于这些组件可在服务器容器之外运行，所以测试起来非常容易。\n "+
							"3、IOC容器是整个框架的核心，有助于粘合应用程序的不同部分从而形成一个整体 \n "+
							"4、通过Spring的各种远程选项，这些POJO业务组件可以成为分布式对象，或者，可以使用POJO业务组件来开发和连接分布式EJB组件。 \n "+
							"5、使用Spring AOP可向POJO组件透明的应用系统服务，如事务、安全和检测。 \n "+
							"6、Spring的安全机制是一种全面的解决方案，足以满足任何企业级应用程序的安全需求。");
		news.setUrl("http://2696008e.ngrok.io/img/spring.jpg");
		
		newList.add(news);
		
		newsMessage.setFromUserName(toUserName);
		newsMessage.setToUserName(fromUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.NEWS_MESSAGE); //设置为图文消息类型
		newsMessage.setArticles(newList);
		newsMessage.setArticleCount(newList.size());
		
		message = newsMessage2Xml(newsMessage); // 调用转换方法
		return message;
	}
	
	/**
	 * 将图片消息对象转换成XML
	 * @param newsMessage
	 * @return
	 */
	public static String imageMessage2Xml(ImageMessage imageMessage) {
		XStream  xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass()); 
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 图片素材的组装方法
	 * @param toUserName
	 * @param fromUserName
	 */
	public static String initImageMessage(String toUserName, String fromUserName) {
		String message = null;
		Image image = new Image();
		image.setMediaId("JoRUI3nR_6Iq3u9bLVVD1H6VEOMeRWyCmgbkMRwvJKqYpSqKceSbyovWbFe-NSF8");
		ImageMessage im = new ImageMessage();
		im.setFromUserName(toUserName);
		im.setToUserName(fromUserName);
		im.setMsgType(IMAGE_MESSAGE);
		im.setCreateTime(new Date().getTime());
		im.setImage(image);
		message = imageMessage2Xml(im);
		return message;
	}
	
	/**
	 * 将音乐消息对象转换成XML
	 * @param newsMessage
	 * @return
	 */
	public static String nusicMessage2Xml(MusicMessage musicMessage) {
		XStream  xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass()); 
		return xstream.toXML(musicMessage);
	}
	
	/**
	 * 音乐消息的组装方法
	 * @param toUserName
	 * @param fromUserName
	 */
	public static String initMusicMessage(String toUserName, String fromUserName) {
		String message = null;
		Music music = new Music();
		music.setThumbMediaId("o03FptFnMJ94EHkD3oIASejE2gX0XlHnX2GoYIPNzFUsnom91a6saodmu3yHN5PN");
		music.setTitle("see you again");
		music.setDescription("速度7插曲");
		music.setMusicUrl("http://ec2fe983.ngrok.io/media/sya.mp3");
		music.setHQMusicUrl("http://ec2fe983.ngrok.io/media/sya.mp3");
		
		MusicMessage mm = new MusicMessage();
		mm.setFromUserName(toUserName);
		mm.setToUserName(fromUserName);	
		mm.setMsgType(MUSIC_MESSAGE);
		mm.setCreateTime(new Date().getTime());
		mm.setMusic(music);
		message = nusicMessage2Xml(mm);
		return message;
	} 
}
