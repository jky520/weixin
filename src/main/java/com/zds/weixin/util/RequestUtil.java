package com.zds.weixin.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

/**
 * 请求的工具类
 * 
 * @author @DT人 2017年8月3日 下午7:13:20
 *
 */
public class RequestUtil {

	/**
	 * get请求-接口的请求方法方法
	 * @param url
	 * @return
	 */
	public static JSONObject httpGet(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				String result = EntityUtils.toString(entity,"UTF-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * post请求-接口的请求方法方法
	 * @param url
	 * @param outStr
	 * @return
	 */
	public static JSONObject httpPost(String url,String param) {
		//DefaultHttpClient httpClient = new DefaultHttpClient();
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpClient = HttpClients.createDefault(); // 
			httpPost.setEntity(new StringEntity(param,"UTF-8")); // 把请求参数设进去
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(httpClient != null) httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
}
