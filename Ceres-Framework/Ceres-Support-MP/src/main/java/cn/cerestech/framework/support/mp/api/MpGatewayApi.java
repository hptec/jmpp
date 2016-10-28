package cn.cerestech.framework.support.mp.api;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.support.mp.listener.MessageListener;
import cn.cerestech.framework.support.starter.operator.RequestOperator;
import cn.cerestech.framework.support.starter.web.WebSupport;

/**
 * 为免税 appID：wx0ac5e092a266fceb appsecret：9f37840b6ed014cbc1ab95f1f9659173
 * 
 * @author bird
 *
 */
@RestController
@RequestMapping("/api/mp/gateway")
@ConfigurationProperties(prefix = "mp")
public class MpGatewayApi extends WebSupport implements RequestOperator {
	@Autowired
	MessageListener normalListener;

	@NotNull
	private String gatetoken;

	/**
	 * 校验是否来自微信官方网站 用于绑定token 和 url 的验证
	 */
	@RequestMapping(value = "/validator", method = RequestMethod.GET)
	public void validate(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {

		boolean bol = validateGateWay(gatetoken, signature, timestamp, nonce, echostr, true);// 校验是否是
		if (bol) {
			zipOut(echostr);
		}
	}

	/**
	 * POST处理 消息 接收微信发送过来的信息</br>
	 * 并加以处理回复
	 */
	@RequestMapping(value = "/validator", method = RequestMethod.POST)
	public void wechatGateWay(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp,
			@RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
		/* 访问 合法性校验,判定是否是微信官方push 的信息，否则不做任何处理,且判断是否是官方的唯一用户 */
		boolean isIllegal = validateGateWay(gatetoken, signature, timestamp, nonce, echostr, false);// 消息通道验证
		if (isIllegal) {
			String msgStr = readPostString();
			BaseMsg msg = MsgFactory.create(msgStr);
			BaseMsg retMsg = null;
			if (msg != null) {
				switch (msg.getMsgType()) {
				case TEXT:
					retMsg = msgService.textMsgProcess((TextMsg) msg);
					break;
				case IMAGE:
					retMsg = msgService.imgMsgProcess((ImgMsg) msg);
					break;
				case LINK:
					retMsg = msgService.linkMsgProcess((LinkMsg) msg);
					break;
				case LOCATION:
					retMsg = msgService.locationMsgProcess((LocationMsg) msg);
					break;
				case SHORTVIDEO:
				case VIDEO:
					retMsg = msgService.videoMsgProcess((VideoMsg) msg);
					break;
				case VOICE:
					retMsg = msgService.voiceMsgProcess((VoiceMsg) msg);
					break;
				case EVENT:
					// MessageEvent.keyOf(StringUtils.upperCase(root.elementText("Event")))
					EventMsg event = (EventMsg) msg;
					switch (event.getEvent()) {
					case CLICK:
					case VIEW:
						retMsg = msgService.menuClickProcess((MenuEventMsg) msg);
						break;
					case SCAN:
					case SUBSCRIBE:
					case UNSUBSCRIBE:
						retMsg = msgService.subscriptEvent((SubscribeEventMsg) msg);
						break;
					case LOCATION:
						retMsg = msgService.reportLocationEventProcess((LocationEventMsg) msg);
						break;
					case UNKNOW:
						// System.out.println("【WECHAT】-【WARN】未找到事件消息的类型");
						break;
					default:
						break;
					}
					break;
				case UNKNOWN:
					// System.out.println("【WECHAT】-【WARN】未找到消息的类型");
					break;
				default:
					break;
				}
			}
			if (retMsg != null) {
				String content = retMsg.replyXml();
				// System.out.println("ret:"+content);
				if (StringUtils.isNotBlank(content)) {
					HttpIOutils.outputContent(getResponse(), content, "utf-8");
				}
			}
		}
	}

	/**
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 *            ： 当为微信服务器官方发起的初始化绑定，而不是接收消息的验证时，没有该参数
	 * @param isBind:
	 *            是否是官方提交绑定服务器入口的校验
	 * @return
	 */
	private static boolean validateGateWay(String gateToken, String signature, String timestamp, String nonce,
			String echostr, boolean isBind) {
		if (StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce)
				|| (isBind && StringUtils.isBlank(echostr))) {
			return Boolean.FALSE;
		} else {
			String token = gateToken;

			String[] str = { token, timestamp, nonce };

			java.util.Arrays.sort(str);
			String pwd = "";
			// 字段加密结果
			pwd = Encryption.sha1(str[0] + str[1] + str[2]);

			if (signature.equalsIgnoreCase(pwd)) {
				// System.out.println("++++++++++++++++++++++++");
				return Boolean.TRUE;
			} else {
				// System.out.println("========================");
				return Boolean.FALSE;
			}
		}
	}

	/**
	 * js 授权
	 */
	@RequestMapping("jsGrant")
	public @ResponseBody void jsGrant() {
		String url = requestParam("signUrl");
		if (StringUtils.isBlank(url)) {
			zipOut(Result.error().setMessage("地址错误！").toJson());
		} else {
			zipOut(Result.success()
					.setObject(WechatJs.sign(Setting.Quick.MP.appid(), Setting.Quick.MP.appsecret(), url)).toJson());
		}
	}
}
