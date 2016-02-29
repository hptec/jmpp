package cn.cerestech.middleware.weixin.mp.msg.message;

import com.google.common.base.Strings;

import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.msg.Message;

public class TextMsg extends Message {
	/**
	 * 文本信息内容
	 */
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TextMsg() {
		super();
		setMsgType(MessageKey.TEXT);
	}

	@Override
	protected String getIndividualXml() {
		return "<Content><![CDATA[" + Strings.nullToEmpty(content) + "]]></Content>";
	}

}
