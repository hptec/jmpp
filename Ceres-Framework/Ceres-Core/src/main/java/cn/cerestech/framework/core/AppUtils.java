package cn.cerestech.framework.core;


public class AppUtils {
	
	/**
	 * 手机号码验证 正则
	 */
	public static final String PHONE_REGEX ="1[3,4,5,8,7][0-9]\\d{8}";
	/**
	 * 手机客户端编码
	 */
	public static final String PAGE_ENCODING="UTF-8";
	
	
	/**
	 * 手机验证码过期时间  3 分钟
	 */
	public static final int VALIDATECOD_EEXPIRE_SECONDS = 180;
	
	/**
	 * Cookie 过期时间,设置为半个小时
	 */
	public static final int COOKIE_EXPIRE_TIME = 1800;
	
	/**
	 * 生成随机数
	 * @param length
	 * @return
	 */
	public static String generateRandomDigit(int length){
		String s = "";
		 while(s.length()<length)
		  s+=(int)(Math.random()*10);
		 return s;
	}
	
	

}
