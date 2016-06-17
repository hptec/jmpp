package cn.cerestech.middleware.baidu.map.lbs.api;
/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月17日
 */
public class Criteria {
	private String key;
	private String val;
	public Criteria(String key, String val) {
		this.key = key;
		this.val = val;
	}
	public static Criteria of(String key, String val){
		return new Criteria(key, val);
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	
}
