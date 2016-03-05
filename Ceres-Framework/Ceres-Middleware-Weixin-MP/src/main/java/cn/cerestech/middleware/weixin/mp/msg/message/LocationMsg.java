package cn.cerestech.middleware.weixin.mp.msg.message;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

public class LocationMsg extends Message {
	/**
	 * 地理位置维度
	 */
	private String location_X;
	/**
	 * 地理位置精度
	 */
	private String location_Y;
	/**
	 * 地图缩放大小
	 */
	private String scale;
	/**
	 * 地理位置信息
	 */
	private String label;

	public String getLocation_X() {
		return location_X;
	}

	public void setLocation_X(String location_X) {
		this.location_X = location_X;
	}

	public String getLocation_Y() {
		return location_Y;
	}

	public void setLocation_Y(String location_Y) {
		this.location_Y = location_Y;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public LocationMsg() {
		super();
		setMsgType(MessageKey.LOCATION);
	}

}
