package cn.cerestech.middleware.sms;

public interface SmsFireWall {

	/**
	 * 相同IP的发送时间限制(单位：毫秒）
	 * 
	 * @return
	 */
	Integer sameIpInterval();

	/**
	 * 相同电话号码发送的时间间隔（单位：毫秒）
	 * 
	 * @return
	 */
	Integer sameMobileInterval();
}
