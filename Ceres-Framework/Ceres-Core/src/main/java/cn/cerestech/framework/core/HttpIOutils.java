package cn.cerestech.framework.core;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class HttpIOutils {
	/**
	 * 输出字符串并关闭输出流
	 * @param response
	 * @param str
	 */
	public static void outputContent(HttpServletResponse response, String str,String encoding){
		PrintWriter writer;
		try {
			response.setCharacterEncoding(encoding);
			writer = response.getWriter();
			writer.write(str);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(StringUtils.isNotBlank(str)){
//			outputContent(response, str.getBytes(),encoding);
//		}
	}
	/**
	 * 输出流并关闭输出流
	 * @param response
	 * @param in
	 */
	public static void outputContent(HttpServletResponse response, InputStream in,String encoding){
		if(in == null){
			return;
		}
		
		try {
			byte[] bytes = new byte[100];
			int len = 0;
		
			BufferedOutputStream writer = new BufferedOutputStream(
								 response.getOutputStream()); 
			BufferedInputStream reader = new BufferedInputStream(new DataInputStream(in));
			
			while((len = reader.read(bytes))!=-1){
				writer.write(bytes,0,len);
				writer.flush();
			}
			writer.flush();
			writer.close();
			reader.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 输出字节数组 并关闭输出流
	 * @param response
	 * @param bytes
	 */
	public static void outputContent(HttpServletResponse response, byte[] bytes, String encoding){
		if(StringUtils.isNotBlank(encoding)){
			response.setCharacterEncoding(encoding);
		}
		if(response == null || bytes == null || bytes.length ==0){
			return ;
		}
		try {
			BufferedOutputStream writer = new BufferedOutputStream(
								 new DataOutputStream(response.getOutputStream())); 
			writer.write(bytes,0,bytes.length);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 输出字符数组 并关闭输出流
	 * @param response
	 * @param chars
	 */
	public static void outputContent(HttpServletResponse response, char[] chars,String encoding){
		if(response == null  || chars == null || chars.length <=0){
			return ;
		}
		if(StringUtils.isNotBlank(encoding)){
			response.setCharacterEncoding(encoding);
		}
		try {
			BufferedWriter writer = new BufferedWriter(
								 new OutputStreamWriter(response.getOutputStream())); 
			writer.write(chars,0,chars.length);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取网页输入的字符串流数据数据
	 * @param request
	 * @return  输入流 字符串数据
	 */
	public static String inputHttpContent(HttpServletRequest request){
		StringBuffer sbf = new StringBuffer("");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String line;
			while((line = reader.readLine())!=null){
				sbf.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sbf.toString();
	}
}
