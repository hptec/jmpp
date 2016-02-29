package cn.cerestech.middleware.weixin.mp.msg.message;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

public class UnknownMsg extends Message {

	public UnknownMsg() {
		super();
		setMsgType(MessageKey.UNKNOWN);
	}
}
