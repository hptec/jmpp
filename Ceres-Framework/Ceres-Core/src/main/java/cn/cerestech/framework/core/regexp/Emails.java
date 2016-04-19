package cn.cerestech.framework.core.regexp;

import com.google.common.base.Strings;

public class Emails {
	
	public static boolean legal(String email){
		return RegExp.match(RegExp.REGEXP_EMAIL, email);
	}
	
	public static String mask(String email){
		if(Strings.isNullOrEmpty(email)){
			return "";
		}
		
		if(!legal(email)){
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
