package cn.cerestech.middleware.baidu.map.lbs.api;
/**
 * 百度API
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月16日
 */
public abstract class BdApi {
	private String access_key;
	
	public BdApi(String access_key){
		this.access_key = access_key;
	}
	
	public String getAccess_key() {
		return access_key;
	}

	public void setAccess_key(String access_key) {
		this.access_key = access_key;
	}
}
