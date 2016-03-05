package cn.cerestech.support.web.console.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.requirejs.annotation.ClasspathPath;
import cn.cerestech.framework.support.requirejs.annotation.RequireJsBootstrap;
import cn.cerestech.support.classpath.ClasspathService;

@RestController
@RequestMapping("$$ceres_sys/console")
@ClasspathPath(id = "ceres-console", uri = "support/web/console")
public class DefaultWeb extends AbstractConsoleWeb {
	private Logger log = LogManager.getLogger();

	@Autowired
	ClasspathService classpathService;

	@RequestMapping("")
	@RequireJsBootstrap(platform = PlatformCategory.CONSOLE, bootstrapJs = "ceres-console/js/console-starter", html5mode = true)
	public byte[] index() {
		return themeResource("index.html");
	}

}
