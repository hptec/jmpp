package cn.cerestech.framework.pay.gopay.utils;

import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.core.HttpUtils;

public class GopayUtils {
	public static String gopay_server_time_url = "http://gateway.gopay.com.cn/time.do";
	
	/**
	 * 国付宝时间戳
	 * @return
	 */
	public static String gopayServerTime(){
		String time = HttpUtils.get(gopay_server_time_url, null);
		try{
			Dates.parse(time,"yyyyMMddHHmmss");
			return time;
		}catch(Exception e){
			return "";
		}
	}
}
