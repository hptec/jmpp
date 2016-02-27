package cn.cerestech.framework.support.requirejs.web;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.support.requirejs.annotation.BootCdnRepository;
import cn.cerestech.framework.support.requirejs.annotation.ClasspathRepository;
import cn.cerestech.framework.support.requirejs.service.RequireJsService;

@RestController
@RequestMapping("api/requirejs")

@BootCdnRepository(module = "angular.js", version = "1.5.0")
@BootCdnRepository(id = "angular", module = "angular.js", version = "1.5.0/angular.min")
@BootCdnRepository(module = "angular-ui-router", version = "1.0.0-alpha0/angular-ui-router.min")
@BootCdnRepository(module = "angular-ui-utils", version = "0.1.1")
@BootCdnRepository(module = "bootstrap", version = "3.3.6")
@BootCdnRepository(module = "jquery", version = "2.2.1/jquery.min")
@BootCdnRepository(module = "flot", version = "0.8.3")
@BootCdnRepository(module = "jquery-slimScroll", version = "1.3.7")
@BootCdnRepository(module = "jqueryui", version = "1.11.4")
@BootCdnRepository(module = "metisMenu", version = "2.4.0")
@BootCdnRepository(module = "sweetalert", version = "1.1.3")
@BootCdnRepository(module = "iCheck", version = "1.0.2")
@BootCdnRepository(module = "peity", version = "3.2.0")
@BootCdnRepository(module = "moment", version = "2.11.2")
@BootCdnRepository(module = "fullcalendar", version = "2.6.1")
@BootCdnRepository(module = "angular-ui-calendar", version = "1.0.0")
@BootCdnRepository(module = "summernote", version = "0.8.1")
@BootCdnRepository(module = "angular-summernote", version = "0.7.1")
@BootCdnRepository(module = "ng-grid", version = "2.0.11")
@BootCdnRepository(module = "angular-ui-tree", version = "2.15.0")
@BootCdnRepository(module = "bootstrap-tour", version = "0.10.3")
@BootCdnRepository(module = "datatables", version = "1.10.11")
@BootCdnRepository(module = "pdfmake", version = "0.1.20")
@BootCdnRepository(module = "angular-xeditable", version = "0.1.10")
@BootCdnRepository(module = "bootstrap-touchspine", version = "3.1.1")
@BootCdnRepository(module = "blueimp-gallery", version = "2.18.1")
@BootCdnRepository(module = "angular-ui-sortable", version = "0.13.4")
@BootCdnRepository(module = "chartist", version = "0.9.7")
@BootCdnRepository(module = "codemirror", version = "5.12.0")
@BootCdnRepository(module = "d3", version = "3.5.16")
@BootCdnRepository(module = "c3", version = "0.4.10")
@BootCdnRepository(module = "ladda", version = "0.9.8")
@BootCdnRepository(module = "require-css", version = "0.1.8/css.min")
@ClasspathRepository(id = "ceres-requirejs", uri = "support/requirejs")
@ClasspathRepository(id = "angular-async-loader", uri = "support/requirejs/js/angular-async-loader/angular-async-loader.min")
public class RequireJsWeb extends AbstractRequireJsWebApi {

	private Logger log = LogManager.getLogger();

	@Autowired
	RequireJsService requireJsService;

	@RequestMapping("module_def.js")
	public void pathsjs() {

		KV defMap = KV.on();
		defMap.put("paths", requireJsService.getPaths());
		defMap.put("shim", requireJsService.getShim());

		Map<String, String> starterJs = Maps.newHashMap();
		requireJsService.getStarter().forEach((cate, uri) -> {
			starterJs.put(cate.key(), uri);
		});
		defMap.put("starterJs", starterJs);

		zipOutRequrieJson(defMap);
	}

}
