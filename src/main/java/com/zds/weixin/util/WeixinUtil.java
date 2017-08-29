package com.zds.weixin.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.zds.weixin.pojo.menu.Button;
import com.zds.weixin.pojo.menu.ClickButton;
import com.zds.weixin.pojo.menu.Menu;
import com.zds.weixin.pojo.menu.ViewButton;

import net.sf.json.JSONObject;

/**
 * 
 * @author @DT人 2017年7月27日 下午8:28:34
 *
 */

public class WeixinUtil {
	
	/*
	 * 新增临时素材的路径
	 */
	private static final String TEMP_FILE_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE"; 
	/*
	 * 新增菜单地址
	 */
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"; 
	/*
	 * 自定义菜单查询接口地址
	 */
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN"; 
	/*
	 * 自定义菜单删除接口地址
	 */
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN"; 
	
	
	/**
	 * 文件上传的方法
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static String upload(String filePath, String accessToken, String type) throws IOException {
		File file = new File(filePath);
		if(!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		
		String action = TEMP_FILE_UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replaceAll("TYPE", type);
		
		URL url = new URL(action);

		String result = null;
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存

		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
       
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
               + BOUNDARY);

		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\""
               + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());

		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];

		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
       
		out.write(foot);
		out.flush();
		out.close();
       
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con
                   .getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	    JSONObject jsonObj = JSONObject.fromObject(result);
	    String typeName = "media_id";
	    if(!"image".equals(type)) {
	    	typeName = type + "_media_id";
	    }
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	/**
	 * 文件上传的方法
	 * @param path
	 * @param type
	 * @return
	 */
	public static String uploadfile(String path, String accessToken, String type) {
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		
		try {
			client = HttpClients.createDefault();
			String url = TEMP_FILE_UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replaceAll("TYPE", type);
			HttpPost post = new HttpPost(url);
			FileBody fb = new FileBody(new File(path));
			HttpEntity entity = MultipartEntityBuilder.create().addPart("media", fb).build();
			post.setEntity(entity);
			response = client.execute(post);
			int sc = response.getStatusLine().getStatusCode();
			if(sc >= 200 && sc < 300) {
				String json = EntityUtils.toString(response.getEntity());
				JSONObject jsonObj = JSONObject.fromObject(json);
				System.out.println(jsonObj);
			    String typeName = "media_id";
			    if(!"image".equals(type)) {
			    	typeName = type + "_media_id";
			    }
				String mediaId = jsonObj.getString(typeName);
				return mediaId;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(client != null) client.close();
				if(response != null) response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	/**
	 * 菜单的组建方法
	 * 
	 * @return
	 */
	public static Menu initMenu() {
		Menu menu = new Menu();
		ClickButton cb11 = new ClickButton();
		cb11.setName("click菜单");
		cb11.setType("click");
		cb11.setKey("11");
		
		ViewButton vb21 = new ViewButton();
		vb21.setName("食谱食法");
		vb21.setType("view");
		vb21.setUrl("http://www.meishij.net");
		
		ClickButton cb31 = new ClickButton();
		cb31.setName("扫描事件");
		cb31.setKey("31");
		cb31.setType("scancode_push");
		
		ClickButton cb32 = new ClickButton();
		cb32.setName("地理位置");
		cb32.setKey("32");
		cb32.setType("location_select");
		
		Button button = new Button();
		button.setName("菜单");
		
		button.setSub_button(new Button[]{cb31, cb32});
		menu.setButton(new Button[]{cb11,vb21, button});
		
		return menu;
	}
	
	/**
	 * 创建菜单的方法
	 * @param token
	 * @param menu
	 * @return
	 */
	public static int createMenu(String token, String menu) {
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = RequestUtil.httpPost(url, menu);
		if(jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	/**
	 * 查询菜单的方法
	 * @param token
	 * @return
	 */
	public static JSONObject queryMenu(String token) {
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = RequestUtil.httpGet(url);
		return jsonObject;
	}
	/**
	 * 删除菜单的方法
	 * @param token
	 * @return
	 */
	public static int deleteMenu(String token) {
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = RequestUtil.httpGet(url);
		int result = 0;
		if(jsonObject != null) {
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
}
