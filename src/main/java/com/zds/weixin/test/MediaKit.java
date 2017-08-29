package com.zds.weixin.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.zds.weixin.util.TokenUtil;

import net.sf.json.JSONObject;

/**
 * @author @DT人 2017年8月3日 下午6:01:49
 *
 */
public class MediaKit {

	private static final String GET_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	public static void getMedia(String mediaId, File f) {
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		
		try {
			client = HttpClients.createDefault();
			String url = GET_MEDIA_URL.replace("ACCESS_TOKEN", TokenUtil.getToken()).replaceAll("MEDIA_ID", mediaId);
			HttpGet get = new HttpGet(url);
			response = client.execute(get);
			int sc = response.getStatusLine().getStatusCode();
			if(sc >= 200 && sc < 300) {
				InputStream is = response.getEntity().getContent();
				FileUtils.copyInputStreamToFile(is, f); // 存到本地把文件
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
	}
}
