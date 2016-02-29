package cn.cerestech.framework.support.requirejs.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.cerestech.framework.core.json.Jsonable;
import cn.cerestech.framework.support.webapi.WebApi;

public abstract class RequireJsWebApi extends WebApi {

	private Logger log = LogManager.getLogger();

	/**
	 * 将JSON包装成为RequireJs 模块返回
	 * 
	 * @param str
	 */
	protected void zipOutRequrieJson(Jsonable json) {
		StringBuffer buffer = new StringBuffer("define(function() {");
		buffer.append("return ");
		buffer.append(json.toJson());
		buffer.append("});");
		if (log.isTraceEnabled()) {
			log.trace(json.toPretty());
		}
		zipOut(buffer.toString(), "application/javascript");
	}
}
