package com.zds.test;

import java.io.IOException;

import com.zds.weixin.pojo.AccessToken;
import com.zds.weixin.util.TokenUtil;
import com.zds.weixin.util.WeixinUtil;

import net.sf.json.JSONObject;

public class WeixinTest {

	public static void main(String[] args) {
		try {
			String token = TokenUtil.getToken();
			System.out.println("票据:"+token);
			
			//=====================图片消息开始==========================
			/*String filePath = "E:\\imges\\1489804655686.jpeg"; // 2M，支持PNG\JPEG\JPG\GIF格式
			String mediaId = WeixinUtil.upload(filePath, at.getAccessToken(), "image");
			System.out.println(mediaId);*/
			//=====================图片消息结束==========================
			
			//=====================缩略图开始==========================
			/*String filePath = "E:\\imges\\spring.jpg"; // 缩略图不能大于64kb
			String mediaId = WeixinUtil.upload(filePath, token, "thumb");
			System.out.println(mediaId);*/
			//=====================缩略图结束==========================
			
			//=====================菜单结束==========================
			String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
			int result = WeixinUtil.createMenu(token, menu);
			if(result == 0) {
				System.out.println("创建菜单成功");
			} else {
				System.out.println("错误码为："+result);
			}
			//=====================菜单结束==========================
			
			//=====================查询菜单开始==========================
			/*JSONObject jsonObject = WeixinUtil.queryMenu(token);
			System.out.println(jsonObject);*/
			//=====================查询菜单结束==========================
			//=====================删除菜单开始==========================
			/*int rs = WeixinUtil.deleteMenu(token);
			System.out.println(rs);*/
			//=====================删除菜单结束==========================
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
