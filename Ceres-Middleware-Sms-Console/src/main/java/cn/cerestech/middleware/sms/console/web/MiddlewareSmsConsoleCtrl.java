package cn.cerestech.middleware.sms.console.web;

import org.springframework.web.bind.annotation.RequestMapping;

import cn.cerestech.framework.support.starter.web.WebSupport;

@RequestMapping("$$ceres_sys/console/middleware/sms")
//@Manifest("middleware/sms/manifest.json")
// @RequiredResource(js = {
// "console/base/res/cp?id=console/res/middleware/sms/ceres-pages-middleware-sms.js"
// })
public class MiddlewareSmsConsoleCtrl extends WebSupport {
//
//	@Autowired
//	ConfigService configService;
//
//	@Autowired
//	SmsService smsService;
//
//	@RequestMapping("icp/init")
//	public @ResponseBody void smsIcpinit() {
//		KV kv = KV.on().put("smsProviders", smsService.getProviders());
//		kv.put("enable", smsService.isSMSEnabled().toString());
//
//		kv.put("provider_name", smsService.defaultProvider().getName());
//
//		zipOut(Jsons.from(kv));
//	}
//
//	@RequestMapping("icp/changeProvider")
//	public @ResponseBody void smsIcpChangeProvider(@RequestParam("provider_name") String toProvider) {
//		KV kv = KV.on().put("smsProviders", smsService.getProviders());
//		kv.put("enable", smsService.isSMSEnabled().toString());
//		SmsProviderAuthKey[] keys = smsService.getProvider(toProvider).authKeys();
//		List<KV> keyList = Lists.newArrayList();
//		for (SmsProviderAuthKey k : keys) {
//			KV keyValue = KV.on().put("key", k.key()).put("authKey", k.authKey()).put("desc", k.desc());
//			String value = configService.query(k).stringValue();
//			if (Strings.isNullOrEmpty(value)) {
//				value = k.defaultValue();
//			}
//			keyValue.put("value", value);
//			keyList.add(keyValue);
//		}
//
//		kv.put("authkeys", keyList);
//		kv.put("provider_name", toProvider);
//
//		zipOut(Jsons.from(kv));
//	}
//
//	@RequestMapping("icp/update")
//	public @ResponseBody void smsIcpUpdate(@RequestParam(value = "provider_name") String provider_name) {
//		// 更新provider
//		Provider smsProvider = smsService.setProvider(provider_name);
//		if (smsProvider == null) {
//			zipOut(Result.error(ErrorCodes.SMS_PROVIDER_NOT_EXSIT).toJson());
//			return;
//		}
//
//		// 更新参数值
//		Provider provider = smsService.getProvider(provider_name);
//		SmsProviderAuthKey[] authkeys = provider.authKeys();
//		Enumeration<String> keys = getRequest().getParameterNames();
//		while (keys.hasMoreElements()) {
//			String key = keys.nextElement();
//			if (!key.equals("provider_name")) {
//				for (SmsProviderAuthKey authKey : authkeys) {
//					if (authKey.key().equals(key)) {
//						configService.update(authKey, getRequest().getParameter(key));
//					}
//				}
//			}
//		}
//
//		zipOut(Result.success().toJson());
//
//	}
//
//	@RequestMapping("icp/enable")
//	public @ResponseBody void smsIcpEnable(@RequestParam(value = "enable") String enable) {
//		if (YesNo.NO.key().equalsIgnoreCase(enable)) {
//			configService.update(SmsConfigKeys.SMS_ENABLE, YesNo.YES.key());
//		} else {
//			configService.update(SmsConfigKeys.SMS_ENABLE, YesNo.NO.key());
//		}
//
//		zipOut(Result.success().toJson());
//	}
//
//	@RequestMapping("records/init")
//	public @ResponseBody void smsRecordsInit() {
//		KV kv = KV.on().put("smsProviders", smsService.getProviders());
//
////		kv.put("state", EnumCollector.forClass(SmsState.class).toList());
////
////		KV init = KV.on();
////		init.put("provider_name", smsService.defaultProvider().getName());
////		init.put("fromDate", FORMAT_DATE.format(Dates.addDay(new Date(), -7)));
////		init.put("toDate", FORMAT_DATE.format(new Date()));
////
////		kv.put("init", init);
//
//		zipOut(Jsons.from(kv));
//	}
//
//	@RequestMapping("record/search")
//	public @ResponseBody void smsRecordsSearch(@RequestParam(value = "provider", required = false) String providername,
//			@RequestParam(value = "state", required = false) String strState, //
//			@RequestParam(value = "keyword", required = false) String phone, //
//			@RequestParam(value = "fromto", required = false) String fromto) {
//		Provider smsProvider = null;
//		if (!Strings.isNullOrEmpty(providername)) {
//			smsProvider = smsService.getProvider(providername);
//		}
//
//		SmsState state = null;
//		if (!Strings.isNullOrEmpty(strState)) {
//			state = EnumCollector.forClass(SmsState.class).keyOf(getRequest().getParameter("state"));
//		}
//
//		Date fromDate = null;
//		Date toDate = null;
//		if (!Strings.isNullOrEmpty(fromto)) {
//			List<String> dateStrList = Splitter.on(" - ").trimResults().splitToList(fromto);
//			if (dateStrList.size() > 0) {
////				try {
//////					fromDate = FORMAT_DATE.parse(dateStrList.get(0));
////				} catch (ParseException e) {
////				}
//			}
//			if (dateStrList.size() > 1) {
////				try {
//////					toDate = FORMAT_DATE.parse(dateStrList.get(1));
////				} catch (ParseException e) {
////				}
//			}
//		}
//
//		List<? extends SmsRecord> result = smsService.search(SmsRecord.class, smsProvider, state, phone, fromDate,
//				toDate, null);
//		zipOut(Jsons.from(result));
//	}

}
