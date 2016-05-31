package cn.cerestech.framework.core.parser;

import java.util.Properties;
import java.util.ResourceBundle;

import com.google.common.base.Strings;

public class DefaultPropertiesTemplateParser extends TemplateParser implements Parser {

	private static Properties prop = new Properties();
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("sms");

		bundle.keySet().forEach(k -> {
			String v = bundle.getString(k);
			prop.put(k, v);
		});
	}

	private String tplId;

	@Override
	String getTemplate() {
		return Strings.nullToEmpty(Strings.isNullOrEmpty(tplId) ? null : prop.getProperty(tplId));
	}

	public DefaultPropertiesTemplateParser(String templateId) {
		super();
		this.tplId = templateId;
	}

	public DefaultPropertiesTemplateParser() {
		super();
	}

}
