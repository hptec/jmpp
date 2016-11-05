package cn.cerestech.framework.support.mp.entity.base;
/**
 * 素材
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月22日
 */
public class MpMaterial {
	/**
	 * 微信服务器的素材id
	 */
	public String media_id;
	/**
	 * 媒体类型
	 * MaterialType
	 */
	private String type;
	
	/**
	 * 访问的URL
	 */
	private String url;
	/**
	 * 媒体上传时的时间戳
	 */
	private long created_at;
	
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
