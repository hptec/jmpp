package cn.cerestech.middleware.sms.providers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.HttpUtils;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.middleware.sms.entity.SmsSendResult;
import cn.cerestech.middleware.sms.enums.HuoniResponesCode;
import cn.cerestech.middleware.sms.enums.SmsProviderAuthKey;

public class HuoniProvider implements SmsProvider {

	private static final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final String SEND_MSG_URI = "http://sms.huoni.cn:8080/smshttp/infoSend";

	private String name = "火尼短信";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SmsProviderAuthKey[] authKeys() {
		return HuoniAuthKeys.values();
	}

	@Override
	public SmsSendResult send(String phone, String text, Map<String, Object> configParams) {

		Object account = configParams.get(HuoniAuthKeys.ACCOUNT.authKey());
		String acc = "";
		if (account != null) {
			acc = account.toString();
		}

		String outer_id = getRandomTaskId(acc, Math.round((Math.random()) * 100000) + "");

		Map<String, Object> params = Maps.newHashMap(configParams);
		params.put("content", text);
		params.put("phonelist", phone);
		params.put("taskId", outer_id);
		params.put("sendtime", "");

		String res = HttpUtils.post(SEND_MSG_URI, params);

		System.out.println(res);

		Matcher matcher = Pattern.compile("[0-9]{1,}\\,[0-9]{1,}\\,[0-9]{1,}").matcher(res);// .matches();
		String resCode = null;
		while (matcher.find()) {
			resCode = matcher.group();
		}
		SmsSendResult result = new SmsSendResult();
		if (Strings.isNullOrEmpty(resCode)) {
			String[] arr = res.split(",");
			int code = Integer.parseInt(arr[0]);
			HuoniResponesCode s = (HuoniResponesCode) EnumCollector.forClass(HuoniResponesCode.class).keyOf(code + "");
			if (s == null) {
				s = HuoniResponesCode.UNKOWN;
			}

			result.setOuter_id(outer_id);
			result.setOuter_code(s.key());
			result.setMessage(s.desc());
			result.setRemark(res);

			if (s.equals(HuoniResponesCode.SUCCESS)) {
				result.setSuccess(Boolean.TRUE);
			} else {
				result.setSuccess(Boolean.FALSE);
			}
		} else {
			result.setSuccess(Boolean.FALSE);
			result.setOuter_id(outer_id);
			result.setOuter_code(HuoniResponesCode.UNKOWN.key());
			result.setMessage(HuoniResponesCode.UNKOWN.desc());
		}
		return result;
	}

	private static String getRandomTaskId(String acc, String randNum) {
		StringBuffer task = new StringBuffer("");
		task.append(acc).append("_").append(sf.format(new Date())).append("_http_").append(randNum);
		return task.toString();
	}
}
