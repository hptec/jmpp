package cn.cerestech.framework.support.mp.msg.mpserver.event;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpEventMsg;

/**
 * 打开公众号自动上报位置的事件
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月3日
 */
public class MpLocationEvt extends MpEventMsg {
	private String latitude;
	private String longitude;
	private String precision;
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
}
