package cn.cerestech.middleware.weixin.mp.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.Encrypts;
import cn.cerestech.framework.core.HttpIOutils;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.web.support.WebSupport;
import cn.cerestech.middleware.weixin.mp.msg.Event;
import cn.cerestech.middleware.weixin.mp.msg.Message;
import cn.cerestech.middleware.weixin.mp.msg.WeiXinMessage;
import cn.cerestech.middleware.weixin.mp.service.WeiXinMpService;
import cn.cerestech.middleware.weixin.service.WeiXinService;

/**
 * 为免税 appID：wx0ac5e092a266fceb appsecret：9f37840b6ed014cbc1ab95f1f9659173
 * 
 * @author bird
 *
 */
@Controller
@RequestMapping("/mpgateway")
public class MpGateway extends WebSupport {
	// @Autowired
	// JsapiService jsApiService;

	@Autowired
	WeiXinService weixinService;

	@Autowired
	WeiXinMpService weixinMpService;

	/**
	 * 校验是否来自微信官方网站 用于绑定token 和 url 的验证
	 */
	@RequestMapping(value = "/cgi-bin", method = RequestMethod.GET)
	public @ResponseBody void validate() {
		String echostr = requestParam("echostr");

		if (validate(Boolean.TRUE)) {
			HttpIOutils.outputContent(getResponse(), echostr, "gbk");
		}
	}

	/**
	 * POST处理 消息 接收微信发送过来的信息</br>
	 * 并加以处理回复
	 */
	@RequestMapping(value = "/cgi-bin", method = RequestMethod.POST)
	public @ResponseBody void cgiBin() {
		/* 访问 合法性校验,判定是否是微信官方push 的信息，否则不做任何处理,且判断是否是官方的唯一用户 */
		if (validate(false)) {
			String msgStr = HttpIOutils.inputHttpContent(getRequest());
			WeiXinMessage msg = WeiXinMessage.fromXml(msgStr);
			WeiXinMessage retMsg = null;
			switch (msg.getMessageType()) {
			case EVENT:
				retMsg = weixinMpService.notify((Event) msg);
				break;
			case MESSAGE:
				retMsg = weixinMpService.onMessage((Message) msg);
				break;
			}

			if (retMsg != null) {
				String content = retMsg.replyXml();
				System.out.println("ret:" + content);
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
	 * @param isBind
	 *            : 是否是官方提交绑定服务器入口的校验
	 * @return
	 */
	private Boolean validate(boolean isBind) {
		String signature = requestParam("signature");
		String timestamp = requestParam("timestamp");
		String nonce = requestParam("nonce");
		String echostr = requestParam("echostr");
		String token = weixinService.getProfile().getGatewayToken();// 校验token
		if (Strings.isNullOrEmpty(signature) || Strings.isNullOrEmpty(timestamp) || Strings.isNullOrEmpty(nonce)
				|| (isBind && Strings.isNullOrEmpty(echostr))) {
			return Boolean.FALSE;
		} else {
			String[] str = { token, timestamp, nonce };
			java.util.Arrays.sort(str);
			// 字段加密结果
			String pwd = Encrypts.sha1(str[0] + str[1] + str[2]);

			Boolean success = signature.equalsIgnoreCase(pwd);

			if (log.isTraceEnabled()) {
				StringBuffer info = new StringBuffer();
				info.append("Recived from WeiXin:");
				info.append(" [signature=" + signature + "]");
				info.append(" [timestamp=" + timestamp + "]");
				info.append(" [nonce=" + nonce + "]");
				info.append(" [echostr=" + echostr + "]");
				info.append(" validate ");
				info.append(success ? "SUCCESS!" : "FAILED!");
				log.trace(info);
			}

			return success;
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
			// zipOut(Result.success().setObject(jsApiService.sign(url)).toJson());
		}
	}

}
