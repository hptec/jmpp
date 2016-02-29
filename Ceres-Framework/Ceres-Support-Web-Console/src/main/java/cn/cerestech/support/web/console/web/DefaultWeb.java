package cn.cerestech.support.web.console.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.entity.LoginEntity;
import cn.cerestech.framework.support.requirejs.annotation.ClasspathPath;
import cn.cerestech.framework.support.web.ContentType;
import cn.cerestech.support.classpath.ClasspathService;
import cn.cerestech.support.web.console.service.ConsoleLoginService;

@RestController
@RequestMapping("$$ceres_sys/console")
@ClasspathPath(id = "ceres-console", uri = "support/web/console")
public class DefaultWeb extends AbstractConsoleWeb {
	private Logger log = LogManager.getLogger();

	@Autowired
	ConsoleLoginService loginService;

	@Autowired
	ClasspathService classpathService;

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

	@RequestMapping("requirejs/config.js")
	public void reqiureJsConfig() {
		byte[] bytes = classpathService.findByUri("support/web/console/js/angular-requirejs-config.js");
		log.trace(new String(bytes));
		zipOut(bytes, ContentType.getByExtension("js"));
	}

}
