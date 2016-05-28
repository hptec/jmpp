package cn.cerestech.middleware.sms.enums;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.parser.Parser;
import cn.cerestech.framework.core.parser.PropertiesTemplateParser;
import cn.cerestech.middleware.sms.entity.SmsRecord;
import cn.cerestech.middleware.sms.entity.SmsSendResult;
import cn.cerestech.middleware.sms.providers.ISmsProvider;
import cn.cerestech.middleware.sms.providers.ISmsSender;
import cn.cerestech.middleware.sms.providers.YunPianAuthKeys;

public enum SmsProvider implements DescribableEnum, ISmsProvider {
	YUNPIAN("云片网络", "云片网络", //
			PropertiesTemplateParser.fromProperties("sms"), //
			YunPianAuthKeys.values(),
			new ISmsSender() {
				@Override
				public SmsSendResult send(SmsRecord sms, Map<String, Object> configParams) {
					String url = "http://yunpian.com/v1/sms/send.json";
					Map<String, Object> params = Maps
							.newHashMap(configParams == null ? Maps.newHashMap() : configParams);
					params.put("mobile", sms.getTo().number());
					params.put("text", sms.getContent());
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

			}), //
	;

	private String key, desc;
	private Parser parser;
	private ISmsSender sender;
	private SmsProviderAuthKey[] authKeys;

	private SmsProvider(String key, String desc, Parser parser, SmsProviderAuthKey[] keys, ISmsSender sender) {
		this.key = key;
		this.desc = desc;
		this.parser = parser;
		this.authKeys = keys;
		this.sender = sender;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String desc() {
		return desc;
	}

	@Override
	public SmsProviderAuthKey[] authKeys() {
		return authKeys;
	}

	public ISmsSender sender() {
		return sender;
	}

	@Override
	public Parser parser() {
		return parser;
	}

}
