package cn.cerestech.framework.pay.wxpay.publics;

import java.util.Date;
import java.util.Random;

public class CommonUtil {
	public static String createOnceStr(int len){
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		while(res.length() < len){
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
	
	public static String timeStamp(){
		return Long.toString(new Date().getTime() / 1000);
	}
	
	public static String noncestr(int len){
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < len; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
}
