package cn.cerestech.framework.core.parser;

import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.google.common.base.Strings;

import ognl.Ognl;
import ognl.OgnlException;

@SuppressWarnings("serial")
public class PropertiesTemplateParser extends Properties implements Parser {

	@Override
	public String parse(String templateId, Object param) {
		String tpl = getProperty(templateId);
		if (Strings.isNullOrEmpty(tpl)) {
			return "";
		}

		Scanner sc = new Scanner(tpl);
		while (sc.hasNext()) {
			String key = sc.findInLine("\\{[^\\}]+\\}");
			if (!Strings.isNullOrEmpty(key)) {
				String var = "$" + key;
				StringBuffer valName = new StringBuffer(var);
				valName.delete(0, 2);
				valName.delete(valName.length() - 1, valName.length());
				Object val = null;
				try {
					val = Ognl.getValue(valName.toString(), param);
				} catch (OgnlException e) {
					e.printStackTrace();
				}
				String valStr = val == null ? "" : val.toString();
				tpl = tpl.replace(var, valStr);
			} else {
				break;
			}
		}
		sc.close();

		return tpl;
	}

	public static PropertiesTemplateParser fromProperties(String filename) {
		PropertiesTemplateParser parser = new PropertiesTemplateParser();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(filename);

			bundle.keySet().forEach(k -> {
				String v = bundle.getString(k);
				parser.put(k, v);
			});
		} catch (MissingResourceException e) {

		}
		return parser;
	}

}
