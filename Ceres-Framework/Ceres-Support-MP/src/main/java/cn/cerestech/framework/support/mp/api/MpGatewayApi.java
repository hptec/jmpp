package cn.cerestech.framework.support.mp.api;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.http.HttpIO;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.utils.Encrypts;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;
import cn.cerestech.framework.support.mp.msg.client.passive.comm.MpPassiveMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpMsgParser;
import cn.cerestech.framework.support.mp.service.MpMsgDispatcher;
import cn.cerestech.framework.support.starter.web.WebSupport;

/**
 * 
 * @author bird
 *
 */
@RestController
@RequestMapping("/api/mp/gateway")
@ConfigurationProperties(prefix="mp")
public class MpGatewayApi extends WebSupport{
	
	@Autowired
	MpMsgDispatcher msgDispatch;

	@NotNull
	private String gatetoken;
	
	@NotNull
	private String appid;
	@NotNull
	private String appsecret;

	/**
	 * 校验是否来自微信官方网站 用于绑定token 和 url 的验证
	 */
	@RequestMapping(value = "/validator", method = RequestMethod.GET)
	public void validate(@RequestParam(defaultValue="",required=false,value="signature") String signature, @RequestParam(defaultValue="",required=false,value="timestamp") String timestamp,
			@RequestParam(defaultValue="",required=false,value="nonce") String nonce, @RequestParam(defaultValue="",required=false,value="echostr") String echostr) {

		boolean bol = validateGateWay(gatetoken, signature, timestamp, nonce, echostr, true);// 校验是否是
//		System.out.println("校验：：：：："+bol);
		if (bol) {
			HttpIO.out(getResponse(), echostr, "UTF-8");
		}
	}

	/**
	 * POST处理 消息 接收微信发送过来的信息</br>
	 * 并加以处理回复
	 */
	@RequestMapping(value = "/validator", method = RequestMethod.POST)
	public void wechatGateWay(@RequestParam(defaultValue="",required=false,value="signature") String signature, @RequestParam(defaultValue="",required=false,value="timestamp") String timestamp,
			@RequestParam(defaultValue="",required=false,value="nonce") String nonce, @RequestParam(defaultValue="",required=false,value="echostr") String echostr) {
		/* 访问 合法性校验,判定是否是微信官方push 的信息，否则不做任何处理,且判断是否是官方的唯一用户 */
		boolean isIllegal = validateGateWay(gatetoken, signature, timestamp, nonce, echostr, false);// 消息通道验证
		if (isIllegal) {
			String msgStr = readPostString();
			MpMsg msg = MpMsgParser.parse(msgStr);
			MpPassiveMsg retMsg = msgDispatch.dispatch(msg);
			
			if (retMsg != null) {
				String content = retMsg.reply();
				if (!Strings.isNullOrEmpty(content)) {
					HttpIO.out(getResponse(), content, "UTF-8");
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
		if (Strings.isNullOrEmpty(signature) || Strings.isNullOrEmpty(timestamp) || Strings.isNullOrEmpty(nonce)
				|| (isBind && Strings.isNullOrEmpty(echostr))) {
			return Boolean.FALSE;
		} else {
			String token = gateToken;

			String[] str = { token, timestamp, nonce };

			java.util.Arrays.sort(str);
			String pwd = "";
			// 字段加密结果
			pwd = Encrypts.sha1(str[0] + str[1] + str[2]);

			if (signature.equalsIgnoreCase(pwd)) {
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		}
	}

	/**
	 * js 授权
	 */
	@RequestMapping("jsGrant")
	public @ResponseBody void jsGrant() {
		String url = getRequest("signUrl");
		if (Strings.isNullOrEmpty(url)) {
			zipOut(Result.error().setMessage("地址错误！").toJson());
		} else {
			zipOut(Result.success().setObject(MemoryStrategy.of(getAppid(), getAppsecret()).JS().signUrl(url)).toJson());
		}
	}

	public String getGatetoken() {
		return gatetoken;
	}

	public void setGatetoken(String gatetoken) {
		this.gatetoken = gatetoken;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	
}
