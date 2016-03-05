package cn.cerestech.support.web.console.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.platform.enums.PlatformCategory;
import cn.cerestech.framework.support.html5mode.Html5ModeIndexPage;
import cn.cerestech.framework.support.requirejs.annotation.ClasspathPath;
import cn.cerestech.framework.support.web.ContentType;
import cn.cerestech.support.classpath.ClasspathService;

@RestController
@RequestMapping("$$ceres_sys/console")
@ClasspathPath(id = "ceres-console", uri = "support/web/console")
public class DefaultWeb extends AbstractConsoleWeb {
	private Logger log = LogManager.getLogger();

	@Autowired
	ClasspathService classpathService;

	@RequestMapping("")
	@Html5ModeIndexPage(PlatformCategory.CONSOLE)
	public byte[] index() {

		return themeResource("index.html");
	}

	@RequestMapping("requirejs/config.js")
	public void reqiureJsConfig() {
		byte[] bytes = classpathService.findByUri("support/web/console/js/angular-requirejs-config.js");
		log.trace(new String(bytes));
		zipOut(bytes, ContentType.getByExtension("js"));
	}

}
