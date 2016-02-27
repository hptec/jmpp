package cn.cerestech.middleware.aliyunoss.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import cn.cerestech.console.annotation.RequiredResource;
import cn.cerestech.console.web.WebConsoleSupport;
import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.middleware.aliyunoss.entity.AliyunOssSysFile;
import cn.cerestech.middleware.aliyunoss.enums.AliyunOssConfigKey;
import cn.cerestech.middleware.aliyunoss.enums.AliyunOssErrorCodes;
import cn.cerestech.middleware.aliyunoss.provider.AliyunOssProvider;
import cn.cerestech.middleware.aliyunoss.service.AliyunOssService;

@RequestMapping("$$ceres_sys/console/middleware/aliyunoss")
@Controller
@RequiredResource(js = {
		"console/base/res/cp?id=console/res/middleware/aliyunoss/ceres-pages-middleware-aliyunoss.js" })
public class MiddlewareAliyunOssConsoleCtrl extends WebConsoleSupport {

	@Autowired
	ConfigService configService;

	@Autowired
	AliyunOssService aliyunOssService;

	@RequestMapping("icp/init")
	public @ResponseBody void aliyunOssIcpInit() {
		KV kv = KV.on();
		KV init = KV.on();
		init.put("enable", configService.query(AliyunOssConfigKey.ALIYUN_OSS_OPEN).boolValue().toString());
		init.put("aliyunossProviders", aliyunOssService.getProviders());
		init.put("bucketname", aliyunOssService.getBucketName());
		init.put("accessKeyId", aliyunOssService.accessKeyId());
		init.put("accessKeySecret", aliyunOssService.accessKeySecret());
		String provider_name = null;
		AliyunOssProvider provider = aliyunOssService.getProvider();
		if (provider != null) {
			provider_name = provider.getName();
		}
		init.put("provider_name", provider_name);
		kv.put("init", init);
		zipOut(Jsons.toJson(kv));
	}

	@RequestMapping("icp/enable")
	public @ResponseBody void aliyunOssIcpEnable(@RequestParam(value = "enable") String enable) {
		if (YesNo.NO.key().equalsIgnoreCase(enable)) {
			configService.update(AliyunOssConfigKey.ALIYUN_OSS_OPEN, YesNo.YES.key());
		} else {
			configService.update(AliyunOssConfigKey.ALIYUN_OSS_OPEN, YesNo.NO.key());
		}

		zipOut(Result.success().toJson());
	}

	@RequestMapping("icp/update")
	public @ResponseBody void aliyunOssIcpUpdate(@RequestParam(value = "provider_name") String provider_name,
			@RequestParam(value = "accessKeyId") String accessKeyId,
			@RequestParam(value = "accessKeySecret") String accessKeySecret,
			@RequestParam(value = "bucketname") String bucketname) {
		// 更新provider
		AliyunOssProvider provider = aliyunOssService.getProvider(provider_name);
		if (provider == null) {
			zipOut(Result.error(AliyunOssErrorCodes.PROVIDER_NOT_EXIST).toJson());
			return;
		}

		aliyunOssService.setProvider(provider);
		aliyunOssService.accessKeyId(accessKeyId);
		aliyunOssService.accessKeySecret(accessKeySecret);
		aliyunOssService.setBucketName(bucketname);

		zipOut(Result.success().toJson());
	}

	@RequestMapping("records/init")
	public @ResponseBody void aliyunossRecordsInit() {
		KV kv = KV.on().put("aliyunossProviders", aliyunOssService.getProviders());

		kv.put("sync", EnumCollector.forClass(YesNo.class).toList());

		KV init = KV.on();
		// init.put("provider_name", aliyunOssService.getProvider().getName());
		init.put("fromDate", FORMAT_DATE.format(Dates.addDay(new Date(), -7)));
		init.put("toDate", FORMAT_DATE.format(new Date()));

		kv.put("init", init);

		zipOut(Jsons.toJson(kv));
	}

	@RequestMapping("record/search")
	public @ResponseBody void aliyunossRecordsSearch(
			@RequestParam(value = "provider", required = false) String provider_name,
			@RequestParam(value = "sync", required = false) String strSync,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "fromto", required = false) String fromto) {
		AliyunOssProvider provider = null;
		if (!Strings.isNullOrEmpty(provider_name)) {
			AliyunOssProvider tmp = aliyunOssService.getProvider(provider_name);
			if (tmp.getName().equals(provider_name)) {
				provider = tmp;
			}
		}

		YesNo sync = null;
		if (!Strings.isNullOrEmpty(strSync)) {
			sync = EnumCollector.forClass(YesNo.class).keyOf(strSync);
		}
		Date fromDate = null;
		Date toDate = null;
		if (!Strings.isNullOrEmpty(fromto)) {
			List<String> dateStrList = Splitter.on(" - ").trimResults().splitToList(fromto);
			if (dateStrList.size() > 0) {
				try {
					fromDate = FORMAT_DATE.parse(dateStrList.get(0));
				} catch (ParseException e) {
				}
			}
			if (dateStrList.size() > 1) {
				try {
					toDate = FORMAT_DATE.parse(dateStrList.get(1));
				} catch (ParseException e) {
				}
			}
		}

		List<? extends AliyunOssSysFile> result = aliyunOssService.search(AliyunOssSysFile.class, provider, sync,
				keyword, fromDate, toDate, null);
		zipOut(Jsons.toJson(result));
	}
}
