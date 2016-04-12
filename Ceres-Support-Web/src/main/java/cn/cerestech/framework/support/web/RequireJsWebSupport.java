package cn.cerestech.framework.support.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.cerestech.framework.core.json.Jsons;

public abstract class RequireJsWebSupport extends WebSupport {

	private Logger log = LogManager.getLogger();

	/**
	 * 将JSON包装成为RequireJs 模块返回
	 * 
	 * @param str
	 */
	protected void zipOutRequireJson(Object obj) {
		StringBuffer buffer = new StringBuffer("define(function() {");
		buffer.append("return ");
		buffer.append(Jsons.from(obj).toJson());
		buffer.append("});");
		if (log.isTraceEnabled()) {
			log.trace(Jsons.from(obj).prettyPrint().toJson());
		}
		zipOut(buffer.toString(), "application/javascript");
	}
}
