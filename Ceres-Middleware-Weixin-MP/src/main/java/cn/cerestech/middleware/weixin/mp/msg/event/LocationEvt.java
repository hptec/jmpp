package cn.cerestech.middleware.weixin.mp.msg.event;

import cn.cerestech.middleware.weixin.mp.enums.EventType;
import cn.cerestech.middleware.weixin.mp.msg.Event;

/**
 * @author <a href="mailto:royrxc@gmail.com">bird</a> 上报地理位置
 */
public class LocationEvt extends Event {
	/**
	 * 地理位置纬度
	 */
	private String Latitude;
	/**
	 * 地理位置经度
	 */
	private String Longitude;
	/**
	 * 地理位置精度
	 */
	private String Precision;

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String latitude) {
		Latitude = latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String longitude) {
		Longitude = longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String precision) {
		Precision = precision;
	}

	public LocationEvt() {
		super();
		setEvent(EventType.LOCATION);
	}

}
