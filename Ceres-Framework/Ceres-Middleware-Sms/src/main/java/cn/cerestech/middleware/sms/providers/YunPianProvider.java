package cn.cerestech.middleware.sms.providers;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.parser.Parser;
import cn.cerestech.framework.core.parser.PropertiesTemplateParser;
import cn.cerestech.middleware.sms.entity.SmsSendResult;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;

public class YunPianProvider implements SmsProvider {

	private String name = "云片网络";

	public String getName() {
		return name;
	}

	/**
	 * 得到网关的连接授权参数
	 * 
	 * @return
	 */
	public SmsProviderAuthKey[] authKeys() {
		return YunPianAuthKeys.values();
	}

	@Override
	public SmsSendResult send(String phone, String text, Map<String, Object> configParams) {
		String url = "http://yunpian.com/v1/sms/send.json";
		Map<String, Object> params = Maps.newHashMap(configParams == null ? Maps.newHashMap() : configParams);
		params.put("mobile", phone);
		params.put("text", text);
		params.put("uid", Math.ceil(Math.random() * 10000) + "");

		SmsSendResult result = new SmsSendResult();

		try {
			String content = Https.of().post(url, params).readString();
			if (Strings.isNullOrEmpty(content)) {
				content = "{}";
			}
			JsonObject element = Jsons.from(content).getRoot().getAsJsonObject();
			if (element.has("code")) {
				Integer code = element.get("code").getAsInt();
				String msg = Strings.nullToEmpty(element.get("msg").getAsString());
				String detail = null;
				if (element.has("detail")) {
					detail = element.get("detail").getAsString();
				}
				if (code == 0) {
					result.setSuccess(Boolean.TRUE);
				} else {
					result.setSuccess(Boolean.FALSE);
				}
				result.setMessage(msg);
				result.setOuter_code(code + "");
				result.setOuter_id(null);
				result.setRemark(detail);

			} else {
				// 没有返回码
				result.setSuccess(Boolean.FALSE);
				result.setMessage("云片服务器不可用");
			}
		} catch (Throwable t) {
			result.setSuccess(Boolean.FALSE);
			result.setMessage(t.getMessage());
		}
		return result;
	}

	private static Parser parser = PropertiesTemplateParser.fromProperties("sms_tpl");

	@Override
	public Parser getParser() {
		return parser;
	}
}
