package cn.cerestech.framework.support.requirejs.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.support.requirejs.annotation.BootCdnPath;
import cn.cerestech.framework.support.requirejs.annotation.ClasspathPath;
import cn.cerestech.framework.support.requirejs.service.RequireJsService;

@RestController
@RequestMapping("api/requirejs")

@BootCdnPath(module = "angular.js", version = "1.5.0")
@BootCdnPath(module = "angular-ui-utils", version = "0.1.1")
@BootCdnPath(module = "flot", version = "0.8.3")
@BootCdnPath(module = "jquery-slimScroll", version = "1.3.7")
@BootCdnPath(module = "jqueryui", version = "1.11.4")
@BootCdnPath(module = "metisMenu", version = "2.4.0")
@BootCdnPath(module = "sweetalert", version = "1.1.3")
@BootCdnPath(module = "peity", version = "3.2.0")
@BootCdnPath(module = "moment", version = "2.11.2")
@BootCdnPath(module = "fullcalendar", version = "2.6.1")
@BootCdnPath(module = "angular-ui-calendar", version = "1.0.0")
@BootCdnPath(module = "summernote", version = "0.8.1")
@BootCdnPath(module = "angular-summernote", version = "0.7.1")
@BootCdnPath(module = "ng-grid", version = "2.0.11")
@BootCdnPath(module = "angular-ui-tree", version = "2.15.0")
@BootCdnPath(module = "bootstrap-tour", version = "0.10.3")
@BootCdnPath(module = "datatables", version = "1.10.11")
@BootCdnPath(module = "pdfmake", version = "0.1.20")
@BootCdnPath(module = "angular-xeditable", version = "0.1.10")
@BootCdnPath(module = "bootstrap-touchspine", version = "3.1.1")
@BootCdnPath(module = "blueimp-gallery", version = "2.18.1")
@BootCdnPath(module = "angular-ui-sortable", version = "0.13.4")
@BootCdnPath(module = "chartist", version = "0.9.7")
@BootCdnPath(module = "codemirror", version = "5.12.0")
@BootCdnPath(module = "d3", version = "3.5.16")
@BootCdnPath(module = "c3", version = "0.4.10")
@BootCdnPath(module = "ladda", version = "0.9.8")
@BootCdnPath(module = "require-css", version = "0.1.8/css.min")
@BootCdnPath(module = "jquery-cookie", version = "1.4.1/jquery.cookie.min")
@ClasspathPath(id = "ceres-requirejs", uri = "support/requirejs")
@ClasspathPath(id = "ceres-classpath", uri = ".")
@ClasspathPath(id = "http", uri = "support/requirejs/js/http")
public class RequireJsWeb extends RequireJsWebApi {

	private Logger log = LogManager.getLogger();

	@Autowired
	RequireJsService requireJsService;

	@RequestMapping("module_def.js")
	public void pathsjs() {

		KV defMap = KV.on();
		defMap.put("paths", requireJsService.getPaths());
		defMap.put("shim", requireJsService.getShim());

		zipOutRequrieJson(defMap);
	}

}
