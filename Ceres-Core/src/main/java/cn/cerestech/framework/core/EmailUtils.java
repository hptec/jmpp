package cn.cerestech.framework.core;

import org.apache.commons.lang3.StringUtils;

public class EmailUtils {
	
	public static boolean isEmail(String email){
		return RegExp.match(RegExp.REGEXP_EMAIL, email);
	}
	
	public static String mask(String email){
		if(StringUtils.isBlank(email)){
			return "";
		}
		
		if(!isEmail(email)){
			return email;
		}
		
		String prefix = "", suffix = "";
		
		int idx = email.indexOf("@");
		
		prefix = idx > 1?email.substring(0, 2):email.substring(0, idx-1);
		
		suffix = email.substring(idx);
		
		return prefix + "**"+ suffix;
	}
	
	public static void main(String[] args) {
		
		System.out.println(mask("ranxc@gmail.com"));
	}
}
