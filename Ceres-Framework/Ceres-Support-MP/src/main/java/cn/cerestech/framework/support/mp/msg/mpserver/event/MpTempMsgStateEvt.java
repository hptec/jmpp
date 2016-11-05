package cn.cerestech.framework.support.mp.msg.mpserver.event;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpEventMsg;

/**
 * 模板消息发送成功的推送通知消息
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月5日
 */
public class MpTempMsgStateEvt extends MpEventMsg {
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
