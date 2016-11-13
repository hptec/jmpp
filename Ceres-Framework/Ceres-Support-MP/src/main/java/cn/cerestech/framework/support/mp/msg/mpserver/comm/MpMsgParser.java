package cn.cerestech.framework.support.mp.msg.mpserver.comm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;

import cn.cerestech.framework.support.mp.enums.Event;
import cn.cerestech.framework.support.mp.enums.RMsgType;
import cn.cerestech.framework.support.mp.msg.mpserver.MpImageMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpLinkMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpLocationMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpShortvideoMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpTextMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpVideoMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpVoiceMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpClickEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpLocationEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpSubscribeEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpTempMsgStateEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpViewEvt;

/**
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月2日
 */
public class MpMsgParser {
	private static final Logger LOG = LogManager.getLogger(MpMsgParser.class);
	
	public static MpMsg parse(String xml) {
		if (!Strings.isNullOrEmpty(xml)) {
			Document dom = null;
			try {
				dom = DocumentHelper.parseText(xml);
			} catch (DocumentException e) {
				LOG.error(e);
			}
			if (dom != null) {
				MpMsg result = null;
				Element root = dom.getRootElement();
				String msgType = root.elementTextTrim("MsgType");
				RMsgType type = RMsgType.keyOf(msgType);
				if (type != null) {
					switch(type){
						case EVENT:
							String event = root.elementTextTrim("Event");
							Event eventMsg = Event.keyOf(event.toUpperCase());
							switch(eventMsg){
								case SUBSCRIBE:
								case UNSUBSCRIBE:
								case SCAN:
									MpSubscribeEvt se = parse(MpSubscribeEvt.class, root);
									se.setEventKey(root.elementTextTrim("EventKey"));
									se.setTicket(root.elementTextTrim("Ticket"));
									se.setEvent(eventMsg);
									result = se;
									break;
								case CLICK:
									MpClickEvt ce = parse(MpClickEvt.class, root);
									ce.setEventKey(root.elementTextTrim("EventKey"));
									ce.setEvent(eventMsg);
									result = ce;
									break;
								case LOCATION:
									MpLocationEvt le = parse(MpLocationEvt.class, root);
									le.setLatitude(root.elementTextTrim("Latitude"));
									le.setLongitude(root.elementTextTrim("Longitude"));
									le.setPrecision(root.elementTextTrim("Precision"));
									le.setEvent(eventMsg);
									result = le;
									break;
								case VIEW:
									MpViewEvt ve = parse(MpViewEvt.class, root);
									ve.setEventKey(root.elementTextTrim("EventKey"));
									ve.setEvent(eventMsg);
									result = ve;
									break;
								case TEMPLATESENDJOBFINISH:
									System.out.println("接收模板消息发送状态事件："+xml);
									MpTempMsgStateEvt te = parse(MpTempMsgStateEvt.class, root);
									te.setStatus(root.elementTextTrim("Status"));
									te.setEvent(eventMsg);
									break;
							}
							break;
						case IMAGE:
							MpImageMsg im = parse(MpImageMsg.class, root);
							im.setPicUrl(root.elementTextTrim("PicUrl"));
							im.setMediaId(root.elementTextTrim("MediaId"));
							result = im;
							break;
						case LINK:
							MpLinkMsg lm = parse(MpLinkMsg.class, root);
							lm.setTitle(root.elementTextTrim("Title"));
							lm.setDescription(root.elementTextTrim("Description"));
							lm.setUrl(root.elementTextTrim("Url"));
							result = lm;
							break;
						case LOCATION://主动上报位置
							MpLocationMsg locm = parse(MpLocationMsg.class, root);
							locm.setLocation_X(root.elementTextTrim("Location_X"));
							locm.setLocation_Y(root.elementTextTrim("Location_Y"));
							locm.setScale(root.elementTextTrim("Scale"));
							locm.setLabel(root.elementTextTrim("Label"));
							result = locm;
							break;
						case SHORTVIDEO:
							MpShortvideoMsg svm = parse(MpShortvideoMsg.class, root);
							svm.setThumbMediaId(root.elementTextTrim("ThumbMediaId"));
							svm.setMediaId(root.elementTextTrim("MediaId"));
							result = svm;
							break;
						case TEXT:
							MpTextMsg tm = parse(MpTextMsg.class, root);
							tm.setContent(root.elementTextTrim("Content"));
							result = tm;
							break;
						case VIDEO:
							MpVideoMsg vm = parse(MpVideoMsg.class, root);
							vm.setThumbMediaId(root.elementTextTrim("ThumbMediaId"));
							vm.setMediaId(root.elementTextTrim("MediaId"));
							result = vm;
							break;
						case VOICE:
							MpVoiceMsg vom = parse(MpVoiceMsg.class, root);
							vom.setFormat(root.elementTextTrim("Format"));
							vom.setMediaId(root.elementTextTrim("MediaId"));
							vom.setRecognition(root.elementTextTrim("Recognition"));
							result = vom;
							break;
					}
				}
				return result;
			}
		}
		return null;
	}
	
	
	private static <T extends MpMsg> T parse(Class<T> clazz, Element root) {
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.catching(e);
		}

		if (obj != null) {
			obj.setTo(root.elementText("ToUserName"));
			obj.setFrom(root.elementText("FromUserName"));
			String ct = root.elementText("CreateTime");
			obj.setCreateTime(Longs.tryParse(ct));
			obj.setMsgType(RMsgType.keyOf(root.elementTextTrim("MsgType")));
			obj.setMsgId(root.elementTextTrim("MsgId"));
		}

		return obj;
	}
}
