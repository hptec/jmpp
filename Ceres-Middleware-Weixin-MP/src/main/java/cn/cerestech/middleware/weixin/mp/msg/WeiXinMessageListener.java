package cn.cerestech.middleware.weixin.mp.msg;

public interface WeiXinMessageListener {

	Message onMessage(Message msg);
}
