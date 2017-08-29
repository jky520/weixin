package com.zds.weixin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * 操作txt文件的工具类
 * 
 * @author @DT人 2017年7月28日 上午11:07:30
 *
 */
public class TxtFileUtil {
	
	/**
	 * 读取txt文件的内容
	 * 
	 * @param filePath 文件的地址
	 * @return
	 */
	public static String readTxtFile(String filePath) {
		StringBuffer sb = new StringBuffer();
		try {
			String encode = "GBK";
			File file = new File(filePath);
			if(file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encode);// 编码设置
				BufferedReader br = new BufferedReader(read);
				String lineText = null;
				while((lineText = br.readLine()) != null) {
					sb.append(lineText+"\r\n");
				}
				read.close();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * 把内容写入txt文件中
	 * 
	 * @param content 写入的内容
	 * @param filePath 文件的路径
	 * @return
	 * @throws Exception
	 */
	public static boolean writeTxtFile(String content,String filePath)
			throws Exception{  
		RandomAccessFile mm=null;  
		boolean flag=false;  
		FileOutputStream o=null;  
		try {  
			File f = new File(filePath);
			if (!f.exists()) {  
				f.createNewFile();// 不存在则创建  
	        }  
			o = new FileOutputStream(f);  
		    o.write(content.getBytes("GBK"));  
		    o.close();  
		    flag=true;  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {  
			if(mm != null) {  
				mm.close();  
		    }  
		}  
		return flag;  
	}  
	
}
