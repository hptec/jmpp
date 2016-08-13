package cn.cerestech.framework.support.starter.console.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.support.starter.console.service.MenuService;
import cn.cerestech.framework.support.starter.web.WebSupport;

@RestController
@RequestMapping("/api/console")
public class DefaultApiWeb extends WebSupport {
	private Logger log = LogManager.getLogger();

	@Autowired
	MenuService menuService;

}
