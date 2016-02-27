package cn.cerestech.support.web.console.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.application.enums.ApplicationCategory;
import cn.cerestech.framework.support.login.entity.LoginEntity;
import cn.cerestech.framework.support.requirejs.RequireJsApplicationBootstrapJs;
import cn.cerestech.support.web.console.service.ConsoleLoginService;

@RestController
@RequestMapping("$$ceres_sys/console")
@RequireJsApplicationBootstrapJs(forCategory=ApplicationCategory.CONSOLE,jsUri="support/web/console/js/console-starter.js")
public class DefaultWeb extends AbstractConsoleWeb {
	private Logger log = LogManager.getLogger();

	@Autowired
	ConsoleLoginService loginService;

	@RequestMapping("")
	public void index() {
		Result<LoginEntity> ret = loginService.login(null);
		log.info(ret.toJson());

		themeOut("index.html");
		//
		// KV param = KV.on();
		// param.put("jslist", RequiredResourceService.getJs());
		// param.put("csslist", RequiredResourceService.getCss());
		// param.put("enums", enumMap());
		//
		// param.put("loginUrl", ConsoleLoginIcptr.LOGIN_URL);
		//
		// param.put("queryforms", Jsons.toJson(QueryForm.forms));
		//
		// param.put(ConsoleConfigKey.CONSOLE_DEVELOPER_NAME.key(),
		// configService.query(ConsoleConfigKey.CONSOLE_DEVELOPER_NAME).stringValue());
		// param.put(ConsoleConfigKey.CONSOLE_DEVELOPER_HREF.key(),
		// configService.query(ConsoleConfigKey.CONSOLE_DEVELOPER_HREF).stringValue());
		// param.put(ConsoleConfigKey.CONSOLE_DEVELOPER_COPYRIGHT_YEAR.key(),
		// configService.query(ConsoleConfigKey.CONSOLE_DEVELOPER_COPYRIGHT_YEAR).stringValue());
		//
		// zipOut(freemarker(new String(bytes), param));
	}

}
