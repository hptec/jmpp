package cn.cerestech.framework.support.mp.mpapi;

/**
 * 基本类
 * @author bird
 *
 */
public abstract class API {
	private String appid;
	private String appsecret;
	
	public API(String appid, String appsecret){
		this.appid = appid;
		this.appsecret = appsecret;
	}
	
	public String getAppid() {
		return appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
}
