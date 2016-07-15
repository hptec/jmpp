package cn.cerestech.support.web.console.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.utils.KV;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.web.enums.ModuleType;
import cn.cerestech.framework.support.web.service.ManifestService;
import cn.cerestech.framework.support.web.web.WebApi;

@RestController
@RequestMapping("/api/queryform")
public class QueryFormWebApi extends WebApi implements UserSessionOperator {

	@Autowired
	ManifestService manifestService;

	@RequestMapping("/query")
	@LoginRequired
	public void query() {
		KV retMap = KV.on();

		manifestService.getModule(ModuleType.QUERYFORM).forEach(json -> {
			if (json.get("id").isNull()) {
				throw new IllegalArgumentException("QueryForm id missing! " + json.toPrettyJson());
			}
			String id = json.get("id").asString();
			retMap.put(id, json.getRoot());
		});

		zipOutRequireJson(retMap);
	}

}
