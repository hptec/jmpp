package cn.cerestech.middleware.weixin.mp.msg.message;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

public class CustomerServiceMsg extends Message {

	public CustomerServiceMsg() {
		this.setMsgType(MessageKey.CUSTOMERMSG);
	}

}
