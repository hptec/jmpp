package cn.cerestech.middleware.weixin.mp.msg;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.middleware.weixin.mp.enums.EventType;
import cn.cerestech.middleware.weixin.mp.enums.MessageKey;
import cn.cerestech.middleware.weixin.mp.enums.MessageType;
import cn.cerestech.middleware.weixin.mp.msg.event.ClickEvt;
import cn.cerestech.middleware.weixin.mp.msg.event.LocationEvt;
import cn.cerestech.middleware.weixin.mp.msg.event.ScanEvt;
import cn.cerestech.middleware.weixin.mp.msg.event.SubscribeEvt;
import cn.cerestech.middleware.weixin.mp.msg.event.UnSubscribeEvt;
import cn.cerestech.middleware.weixin.mp.msg.event.UnknownEvt;
import cn.cerestech.middleware.weixin.mp.msg.event.ViewEvt;
import cn.cerestech.middleware.weixin.mp.msg.message.ImageMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.LinkMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.LocationMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.MusicMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.NewsMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.ShortVideoMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.TextMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.UnknownMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.VideoMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.VoiceMsg;
import cn.cerestech.middleware.weixin.mp.msg.message.NewsMsg.Article;

/**
 * @author <a href="mailto:royrxc@gmail.com">bird</a> 消息接口
 */
public abstract class WeiXinMessage {

	static Logger log = LogManager.getLogger();

	public String replyXml() {
		return null;
	}

	/**
	 * 接收方openId/服务号微信号
	 */
	protected String toUserName;
	/**
	 * 发送方微信号，如果发送方位普通用户则为openID
	 */
	protected String fromUserName;
	/**
	 * 消息创建时间 （整型）
	 */
	protected Long createTime;

	protected MessageType messageType;

	public static WeiXinMessage fromXml(String xml) {
		if (!Strings.isNullOrEmpty(xml)) {
			Document dom = null;
			try {
				dom = DocumentHelper.parseText(xml);
			} catch (DocumentException e) {
				log.error(e);
			}
			if (dom != null) {
				Element root = dom.getRootElement();
				String msgType = root.elementTextTrim("MsgType");
				if (!Strings.isNullOrEmpty(msgType)) {
					if ("event".equalsIgnoreCase(msgType)) {
						// 事件
						String event = root.elementTextTrim("Event");
						Event eventMsg = null;
						if (!Strings.isNullOrEmpty(event)) {
							EventType eventType = EnumCollector.forClass(EventType.class).keyOf(event.toLowerCase());
							eventType = eventType == null ? EventType.UNKNOW : eventType;
							switch (eventType) {
							case UNSUBSCRIBE:
								eventMsg = parse(UnSubscribeEvt.class, root);
								break;
							case SCAN:
								ScanEvt scanEvent = parse(ScanEvt.class, root);
								scanEvent.setEventKey(root.elementTextTrim("EventKey"));
								scanEvent.setTicket(root.elementTextTrim("Ticket"));
								eventMsg = scanEvent;
								break;
							case SUBSCRIBE:
								SubscribeEvt subEvent = parse(SubscribeEvt.class, root);
								subEvent.setEventKey(root.elementTextTrim("EventKey"));
								subEvent.setTicket(root.elementTextTrim("Ticket"));
								eventMsg = subEvent;
								break;
							case LOCATION:
								LocationEvt locEvent = parse(LocationEvt.class, root);
								locEvent.setLatitude(root.elementTextTrim("Latitude"));
								locEvent.setLongitude(root.elementTextTrim("Longitude"));
								locEvent.setPrecision(root.elementTextTrim("Precision"));
								eventMsg = locEvent;
								break;
							case CLICK:
								ClickEvt clickEvent = parse(ClickEvt.class, root);
								clickEvent.setEventKey(root.elementTextTrim("EventKey"));
								eventMsg = clickEvent;
								break;
							case VIEW:
								ViewEvt viewEvent = parse(ViewEvt.class, root);
								viewEvent.setEventKey(root.elementTextTrim("EventKey"));
								eventMsg = viewEvent;
								break;
							default:
								eventMsg = parse(UnknownEvt.class, root);
								break;
							}

							return eventMsg;
						}
					} else {
						// 消息
						Message msg = null;

						MessageKey type = EnumCollector.forClass(MessageKey.class).keyOf(msgType.toLowerCase());
						type = type == null ? MessageKey.UNKNOWN : type;
						switch (type) {
						case TEXT:
							TextMsg textMsg = parse(TextMsg.class, root);
							textMsg.setContent(root.elementTextTrim("Content"));
							msg = textMsg;
							break;
						case IMAGE:
							ImageMsg imageMsg = parse(ImageMsg.class, root);
							imageMsg.setPicUrl(root.elementTextTrim("PicUrl"));
							imageMsg.setMediaId(root.elementTextTrim("MediaId"));
							msg = imageMsg;
							break;
						case VOICE:
							VoiceMsg voiceMsg = parse(VoiceMsg.class, root);
							voiceMsg.setFormat(root.elementTextTrim("Format"));
							voiceMsg.setMediaId(root.elementTextTrim("MediaId"));
							voiceMsg.setRecognition(root.elementTextTrim("Recognition"));
							msg = voiceMsg;
							break;
						case VIDEO:
							VideoMsg videoMsg = parse(VideoMsg.class, root);
							videoMsg.setThumbMediaId(root.elementTextTrim("ThumbMediaId"));
							videoMsg.setMediaId(root.elementTextTrim("MediaId"));
							msg = videoMsg;
							break;
						case SHORTVIDEO:
							ShortVideoMsg shortVideoMsg = parse(ShortVideoMsg.class, root);
							shortVideoMsg.setThumbMediaId(root.elementTextTrim("ThumbMediaId"));
							shortVideoMsg.setMediaId(root.elementTextTrim("MediaId"));
							msg = shortVideoMsg;
							break;
						case LOCATION:
							LocationMsg locationMsg = parse(LocationMsg.class, root);
							locationMsg.setLocation_X(root.elementTextTrim("Location_X"));
							locationMsg.setLocation_Y(root.elementTextTrim("Location_Y"));
							locationMsg.setScale(root.elementTextTrim("Scale"));
							locationMsg.setLabel(root.elementTextTrim("Label"));
							msg = locationMsg;
							break;
						case LINK:
							LinkMsg linkMsg = parse(LinkMsg.class, root);
							linkMsg.setTitle(root.elementTextTrim("Title"));
							linkMsg.setDescription(root.elementTextTrim("Description"));
							linkMsg.setUrl(root.elementTextTrim("Url"));
							msg = linkMsg;
							break;

						case CUSTOMERMSG:
						case MUSIC:
						case NEWS:
						default:
							msg = parse(UnknownMsg.class, root);
							break;
						}

						if (msg != null) {
							msg.setMsgId(root.elementTextTrim("MsgId"));
						}

						return msg;
					}

				}
			}

		}
		return null;
	}

	private static <T extends WeiXinMessage> T parse(Class<T> clazz, Element root) {
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.catching(e);
		}

		if (obj != null) {
			obj.setToUserName(root.elementText("ToUserName"));
			obj.setFromUserName(root.elementText("FromUserName"));
			String ct = root.elementText("CreateTime");
			obj.setCreateTime(StringUtils.isNotBlank(ct) ? Long.parseLong(ct) : null);
		}

		return obj;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public TextMsg replyText(String content) {
		TextMsg retMsg = reply(TextMsg.class);
		retMsg.setContent(content);
		return retMsg;
	}

	public ImageMsg replyImage(String mediaId) {
		ImageMsg msg = reply(ImageMsg.class);
		msg.setMediaId(mediaId);
		return msg;
	}

	public VoiceMsg replyVoice(String mediaId) {
		VoiceMsg msg = reply(VoiceMsg.class);
		msg.setMediaId(mediaId);
		return msg;
	}

	public VideoMsg replyVideo(String mediaId, String title, String desc) {
		VideoMsg msg = reply(VideoMsg.class);
		msg.setMediaId(mediaId);
		msg.setTitle(title);
		msg.setDescription(desc);
		return msg;
	}

	public MusicMsg replyMusic(String title, String desc, String musicUrl, String HQMusicUrl, String thumbMediaId) {
		MusicMsg msg = reply(MusicMsg.class);
		msg.setTitle(title);
		msg.setDescription(desc);
		msg.setMusicURL(musicUrl);
		msg.setHQMusicUrl(HQMusicUrl);
		msg.setThumbMediaId(thumbMediaId);
		return msg;
	}

	public NewsMsg replyNews(List<Article> articles) {
		NewsMsg msg = reply(NewsMsg.class);
		msg.setArticles(articles);
		return msg;
	}

	private <T extends Message> T reply(Class<T> clazz) {
		T t = null;
		try {
			t = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		t.setCreateTime(System.currentTimeMillis());
		t.setFromUserName(getToUserName());
		t.setToUserName(getFromUserName());
		return t;
	}

	public String toReplyXml() {

		if (getMessageType().equals(MessageType.EVENT)) {
			// 不能回复事件类型
			return null;
		}

		Message msg = (Message) this;
		switch (msg.getMsgType()) {
		case TEXT:
		case IMAGE:
		case VOICE:
		case VIDEO:
		case MUSIC:
		case NEWS:
			// 允许回复的格式
			StringBuffer xml = new StringBuffer();
			xml.append("<xml>");
			xml.append("<ToUserName><![CDATA[" + msg.getToUserName() + "]]></ToUserName>");
			xml.append("<FromUserName><![CDATA[" + msg.getFromUserName() + "]]></FromUserName>");
			xml.append("<CreateTime><![CDATA[" + msg.getCreateTime() + "]]></CreateTime>");
			xml.append("<MsgType><![CDATA[" + msg.getMsgType().key().toLowerCase() + "]]></MsgType>");
			String individualXml = getIndividualXml();
			if (individualXml == null) {
				// 实体对象控制不返回消息
				return null;
			} else {
				xml.append(getIndividualXml());
			}
			xml.append("</xml>");

			return xml.toString();
		default:
			return null;
		}
	}

	/**
	 * 得到每个消息的个性化内容（重要，仅限Middleware开发人员使用)
	 * 
	 * @return
	 */
	protected String getIndividualXml() {
		return "";
	}

}
