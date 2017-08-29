package com.zds.weixin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zds.weixin.pojo.AccessToken;

import net.sf.json.JSONObject;

/**
 * token的
 * @author @DT人 2017年8月3日 下午7:20:14
 *
 */
public class TokenUtil {
	
	/*private static final String APPID = "wx4fc0215ea7b974e6"; 
	private static final String APPSECRET = "00fc5422dfc7431680abc6cbfcdbd39d"; */
	// 下面是测试号的
	private static final String APPID = "wxdc10a6056241a0ca"; 
	private static final String APPSECRET = "e4b3f7adff07ca054cee7a7caa789e83"; 
	
	/*private static final String APPID = "wx9727ee0c331186e8"; 
	private static final String APPSECRET = "d248d64793912e4f0eba90f5bcbc2056";*/
	/*
	 * 获取access_token的路径
	 */
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"; 
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 获得token，不会每次都去服务器获取，只要不过期就一直可以从本地获取
	 * @return
	 */
	public static String getToken() {
		String token = "";
		try {
			String url = "E:\\token.txt";
			String rs = TxtFileUtil.readTxtFile(url);
			if(rs == null || "".equals(rs)) {
				AccessToken at = getAccessToken();
				token = at.getAccessToken();
				String content = at.getAccessToken() + ",";
				content = content + at.getExpiresIn() + ",";
				content = content + sdf.format(new Date());
				TxtFileUtil.writeTxtFile(content, url);
			} else {
				long now = new Date().getTime();
				String[] arr = rs.split(",");
				long time = sdf.parse(arr[2]).getTime();
				long express_in = Long.valueOf(arr[1]);
				if(((now - time) / 1000) < express_in) {
					token = arr[0];
				} else {
					AccessToken at = getAccessToken();
					token = at.getAccessToken();
					String content = at.getAccessToken() + ",";
					content = content + at.getExpiresIn() + ",";
					content = content + sdf.format(new Date());
					TxtFileUtil.writeTxtFile(content, url);
				}
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}
	
	
	/**
	 * 获取access_token的方法
	 * 
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken at = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replaceAll("APPSECRET", APPSECRET);// 把路劲的对应参数换成自己的
		JSONObject jsonObject = RequestUtil.httpGet(url);
		if(jsonObject != null) {
			at.setAccessToken(jsonObject.getString("access_token"));
			at.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return at;
	}
}
