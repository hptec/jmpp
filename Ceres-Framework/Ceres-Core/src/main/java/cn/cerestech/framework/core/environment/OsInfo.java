package cn.cerestech.framework.core.environment;

import java.net.InetAddress;

public class OsInfo {
	
	public static String get(String key, String def){
		return System.getProperty(key, def);
	}
	public static String get(String key){
		return System.getProperty(key, "");
	}
	/**
	 * 
	 * @return : “Mac OS X”等等
	 */
	public static String osName(){
		return get("os.name","");
	}
	/**
	 * 
	 * @return : x86_64 等等
	 */
	public static String osArch(){
		return get("os.arch");
	}
	/**
	 * 操作系统版本
	 * @return: "10.11.3" 等等
	 */
	public static String osVersion(){
		return get("os.version");
	}
	/**
	 * 当前系统登录用户
	 * @return
	 */
	public static String userName(){
		return get("user.name");
	}
	/**
	 * 当前程序目录 
	 * @return：/Users/bird/Documents/workspace/ceres/Free-Framework/Free-Core
	 */
	public static String userDir(){
		return get("user.dir");
	}
	/**
	 * 用户主目录
	 * @return
	 */
	public static String userHome(){
		return get("user.home");
	}
	
	public static String charset(){
		return get("file.encoding");
	}
	
	public static String jdk(){
		return get("java.version");
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(InetAddress.getLocalHost());
	}
}
