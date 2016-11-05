package cn.cerestech.framework.support.mp.msg.mpserver;

import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年10月29日
 */
public class MpTextMsg extends MpMsg{
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
