package cn.cerestech.console.web;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.cerestech.console.interceptor.ConsoleLoginIcptr;
import cn.cerestech.console.utils.AngularDateEditor;
import cn.cerestech.framework.web.support.WebSupport;

public abstract class WebConsoleSupport extends WebSupport {

	protected Long userId() {
		return (Long) session(ConsoleLoginIcptr.SK_CONSOLE_USER_ID);
	}

	protected String getCtx() {
		return getRequest().getContextPath();
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new AngularDateEditor());
	}
}
