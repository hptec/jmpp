package cn.cerestech.middleware.weixin.mp.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum EventType implements DescribableEnum {
	// 订阅事件，包括无参数扫描二维码事件
	SUBSCRIBE("subscribe", "订阅"), //
	UNSUBSCRIBE("unsubscribe", "取消订阅"), //
	// 二维码扫描事件，在已经关注了微信号时有参数返回，但不表示新参数
	SCAN("scan", "扫描二维码"), //
	// 微信开通位置服务后，用户同意以后每5秒钟上报一次用户位置事件
	LOCATION("location", "发送位置"), //
	// 自定义菜单点击事件，click 类型
	CLICK("click", "点击菜单"), //
	// 自定义菜单view类型 出发时间
	VIEW("view", "跳转链接"), //
	UNKNOW("UNKONW", "未知事件"),//
	;
	private String key, desc;

	private EventType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String key() {
		return this.key;
	}

	public String desc() {
		return this.desc;
	}
}
