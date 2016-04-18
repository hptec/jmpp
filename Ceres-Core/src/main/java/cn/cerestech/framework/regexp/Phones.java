package cn.cerestech.framework.regexp;


import com.google.common.base.Strings;

public class Phones {
	
	public static boolean legal(String phone){
		if(Strings.isNullOrEmpty(phone)){
			return false;
		}
		return RegExp.match(RegExp.REGEXP_PHONE, phone);
	}
	
	public static String maskPhone(String phone){
		if(Strings.isNullOrEmpty(phone)){
			return "";
		}
		phone = phone.trim();
		
		String pre = phone.length()>=3?phone.substring(0, 3):phone;
		String mid = phone.length()>3?"***":"";
		String sufix = phone.length()>3?phone.substring(phone.length()>=6?(phone.length()-3):3):"";
		
		return pre+mid+sufix;
	}
	
}
