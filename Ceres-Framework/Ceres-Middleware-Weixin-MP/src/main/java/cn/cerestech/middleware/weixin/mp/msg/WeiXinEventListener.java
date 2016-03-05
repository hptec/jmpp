package cn.cerestech.middleware.weixin.mp.msg;

public interface WeiXinEventListener {

	Message notify(Event event);
}
