package cn.cerestech.framework.support.mp.msg;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.strings.StringTypes;

/**
 * 微信公众号最基本的消息类型
 * 
 * @author harryhe
 *
 */
public class MpMsg {

	protected Map<String, StringTypes> attrs = Maps.newHashMap();

	public static MpMsg from(String msgXml) {
		MpMsg msg = new MpMsg();
		try {
			InputSource source = new InputSource(new StringReader(msgXml));
			Document document = new SAXBuilder().build(source);
			Element root = document.getRootElement();// 获得根节点
			root.getChildren().forEach(ele -> {
				String name = ele.getName();
				String text = ele.getText();
				msg.attrs.put(name, new StringTypes(text));
			});

		} catch (JDOMException | IOException e) {
			throw new RuntimeException(e);
		}
		return msg;
	}

	public String getToUserName() {
		return attrs.get("ToUserName").stringValue();
	}

	public void setToUserName(String toUserName) {
		attrs.put("ToUserName", new StringTypes(toUserName));
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}

	public MessageType getMsgType() {
		return MsgType;
	}

	public void setMsgType(MessageType msgType) {
		MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

}
