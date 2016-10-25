package cn.cerestech.framework.support.mp.msg;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.strings.StringTypes;
import cn.cerestech.framework.support.mp.enums.MsgType;

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
		return attrs.get("FromUserName").stringValue();
	}

	public void setFromUserName(String fromUserName) {
		attrs.put("FromUserName", new StringTypes(fromUserName));
	}

	public Long getCreateTime() {
		return attrs.get("CreateTime").longValue();
	}

	public void setCreateTime(Long createTime) {
		attrs.put("CreateTime", new StringTypes(createTime.toString()));
	}

	public MsgType getMsgType() {
		return EnumCollector.forClass(MsgType.class).keyOf(attrs.get("MsgType").stringValue());
	}

	public void setMsgType(MsgType msgType) {
		attrs.put("MsgType", new StringTypes(msgType.key()));
	}

	public String getMsgId() {
		return attrs.get("MsgId").stringValue();
	}

	public void setMsgId(String msgId) {
		attrs.put("MsgId", new StringTypes(msgId));
	}

}
