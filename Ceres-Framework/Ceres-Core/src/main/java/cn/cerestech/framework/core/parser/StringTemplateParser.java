package cn.cerestech.framework.core.parser;

import java.util.Scanner;

import com.google.common.base.Strings;

import ognl.Ognl;
import ognl.OgnlException;

public class StringTemplateParser implements Parser {

	@Override
	public String parse(String tpl, Object param) {
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

}
