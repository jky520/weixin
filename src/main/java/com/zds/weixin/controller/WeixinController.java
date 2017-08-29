package com.zds.weixin.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zds.weixin.pojo.TextMessage;
import com.zds.weixin.util.CheckUtil;
import com.zds.weixin.util.MessageUtil;

/**
 * @author @DT人 2017年7月27日 下午12:56:23
 *
 */
@RestController
public class WeixinController {
	
	/**
	 * 微信后台与本地项目的映射方法
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@GetMapping("/wx")
	public String regist(@RequestParam String signature,
						 @RequestParam String timestamp, 
						 @RequestParam String nonce,
						 @RequestParam String echostr ) {
		if(CheckUtil.checkSignature(signature, timestamp, nonce)) {
			return echostr;
		}
		return null;
	}
	
	/**
	 * 文本消息的接收与响应的接口实现
	 */
	@PostMapping("/wx")
	public String messageResponse(HttpServletRequest req) {
		String message = null;
		try {
			req.setCharacterEncoding("UTF-8"); // 设置请求的格式为UTF-8
			Map<String,String> map = MessageUtil.xml2Map(req);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			
			if(MessageUtil.TEXT_MESSAGE.equals(msgType)) { // 判断是否是文本消息
				if("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				} else if("2".equals(content)) {
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				} else if("3".equals(content)) {
					message = MessageUtil.initImageMessage(toUserName, fromUserName);
				} else if("4".equals(content)) {
					message = MessageUtil.initMusicMessage(toUserName, fromUserName);
				} else if("?".equals(content) || "？".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			} else if(MessageUtil.EVENT_MESSAGE.equals(msgType)) { // 事件判断的逻辑
				String eventType = map.get("Event");
				if(MessageUtil.SUBSCRIBE_MESSAGE.equals(eventType)) { // 关注事件的判断
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				} else if(MessageUtil.CLICK_MESSAGE.equals(eventType)) { // 点击事件的判断
					String url = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName, url);
				} else if(MessageUtil.SCANCODE_MESSAGE.equals(eventType)) { // 扫码事件的判断
					String key = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName, key);
				}
			} else if(MessageUtil.LOCATION_MESSAGE.equals(msgType)) {
				String label = map.get("Label");
				message = MessageUtil.initText(toUserName, fromUserName, label);
			}
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return message;
	}
}
