package cn.cerestech.middleware.sms.providers;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.sms.SmsFireWall;
import cn.cerestech.middleware.sms.entity.SmsResult;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;

public class YunPianProvider implements Provider {
	public static String SEND_URL = "http://yunpian.com/v1/sms/send.json";
	Logger log = LogManager.getLogger();

	@Override
	public SmsProviderAuthKey[] authKeys() {
		return YunPianAuthKeys.values();
	}

	@Override
	public SmsResult send(Mobile to, String content, Map<String, Object> configParams) {
		Map<String, Object> params = configParams == null ? Maps.newHashMap() : configParams;
		params.put("mobile", to.number());
		params.put("text", content);
		params.put("uid", Math.ceil(Math.random() * 10000) + "");

		SmsResult result = new SmsResult();

		try {
			String resp = Https.of().post(SEND_URL, params).readString();
			log.trace("短信发送结果:" + resp);
			if (Strings.isNullOrEmpty(content)) {
				result.setSuccess(Boolean.FALSE);
			}
			Jsons ele = Jsons.from(content);
			Integer code = ele.get("code").asInt();
			if (code != null) {
				String msg = Strings.nullToEmpty(ele.get("msg").asString());
				String detail = ele.get("detail").asString();
				if (code == 0) {
					result.setSuccess(Boolean.TRUE);
				} else {
					result.setSuccess(Boolean.FALSE);
				}
				result.setMessage(msg);
				result.setOuterCode(code + "");
				result.setOuterTransId(null);
				result.setRemark(detail);
			} else {
				// 没有返回码
				result.setSuccess(Boolean.FALSE);
				result.setMessage("云片服务器不可用");
			}
			result.setRetJson(resp);
		} catch (Throwable t) {
			result.setSuccess(Boolean.FALSE);
			result.setMessage(t.getMessage());
		}
		return result;
	}

	private static SmsFireWall fireWall = new SmsFireWall() {
		@Override
		public Integer sameMobileInterval() {
			return 5000;
		}

		@Override
		public Integer sameIpInterval() {
			return 5000;
		}
	};

	public SmsFireWall getFireWall() {
		return fireWall;
	}

}
