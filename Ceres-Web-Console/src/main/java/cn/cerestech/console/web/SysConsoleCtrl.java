package cn.cerestech.console.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.StringTypes;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.configuration.enums.ConfigKey;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.framework.web.enums.WebConfigKey;

@RequestMapping("$$ceres_sys/console/sys")
@Controller
public class SysConsoleCtrl extends WebConsoleSupport {

	@Autowired
	ConfigService configService;

	@RequestMapping("site/init")
	@LoginRequired
	public @ResponseBody void commonRead() {
		Map<String, String> retMap = Maps.newHashMap();
		configService
				.querySet(WebConfigKey.SITE_HOST, WebConfigKey.SITE_LOGO, WebConfigKey.SITE_NAME,
						WebConfigKey.SITE_SHORT_NAME, WebConfigKey.SITE_TITLE, WebConfigKey.SITE_KEYWORD,
						WebConfigKey.SITE_DESC, WebConfigKey.SITE_ICP, WebConfigKey.SITE_IMG_BASE,
						WebConfigKey.SITE_CS_QQ, WebConfigKey.SITE_CS_PHONE, WebConfigKey.SITE_COPYRIGHT)
				.forEach((key, stringType) -> {
					retMap.put(key.key(), stringType.stringValue());
				});

		zipOut(Jsons.toJson(retMap));
	}

	@RequestMapping("/site/update")
	@LoginRequired
	public @ResponseBody void commonUpdate() {
		Map<ConfigKey, String> param = Maps.newHashMap();

		Set<String> keys = getRequest().getParameterMap().keySet();
		for (String k : keys) {
			String v = getRequest().getParameter(k);
			if (!Strings.isNullOrEmpty(v)) {
				ConfigKey de = WebConfigKey.keyOf(k);
				param.put(de, v);
			}
		}
		configService.update(param);

		zipOut(Result.success().toJson());

	}

	@RequestMapping("config/getall")
	@LoginRequired
	public @ResponseBody void configGetAll() {
		List<KV> retList = Lists.newArrayList();

		EnumCollector.allEnums().stream().filter(ec -> ec.isConfigKey()).forEach(ec -> {
			retList.addAll(ec.toList());
		});
		zipOut(Jsons.toJson(retList));
	}

	@RequestMapping("config/get")
	@LoginRequired
	public @ResponseBody void configGet(@RequestParam("key") String key) {
		List<KV> retList = Lists.newArrayList();

		EnumCollector.allEnums().stream().filter(ec -> ec.isConfigKey()).forEach(ec -> {
			List<KV> list = ec.toList();
			retList.addAll(list);
		});

		ConfigKey cfgKey = EnumCollector.allEnums().stream().filter(ec -> ec.isConfigKey())
				.filter(ec -> ec.keyOf(key) != null).map(ec -> (ConfigKey) ec.keyOf(key)).findFirst().orElse(null);

		if (cfgKey == null) {
			zipOut("{}");
		} else {
			StringTypes types = configService.query(cfgKey);
			KV retMap = KV.on().put("value", types.stringValue());
			zipOut(retMap);
		}

	}

	@RequestMapping("config/update")
	@LoginRequired
	public @ResponseBody void configUpdate(@RequestParam("key") String key, @RequestParam("value") String value) {
		List<KV> retList = Lists.newArrayList();

		EnumCollector.allEnums().stream().filter(ec -> ec.isConfigKey()).forEach(ec -> {
			List<KV> list = ec.toList();
			retList.addAll(list);
		});

		ConfigKey cfgKey = EnumCollector.allEnums().stream().filter(ec -> ec.isConfigKey())
				.filter(ec -> ec.keyOf(key) != null).map(ec -> (ConfigKey) ec.keyOf(key)).findFirst().orElse(null);

		configService.update(cfgKey, value);
		zipOut(Result.success());
	}
}
