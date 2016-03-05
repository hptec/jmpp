package cn.cerestech.middleware.weixin.mp.msg.message;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

/**
 * 连接消息，不做回复使用
 * 
 * @author <a href="mailto:royrxc@gmail.com">bird</a>
 */
public class LinkMsg extends Message {
	/**
	 * 消息标题
	 */
	private String title;
	/**
	 * 消息描述
	 */
	private String description;
	/**
	 * 消息链接
	 */
	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LinkMsg() {
		super();
		setMsgType(MessageKey.LINK);
	}

}
