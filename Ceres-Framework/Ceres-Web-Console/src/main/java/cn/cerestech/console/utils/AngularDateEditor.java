package cn.cerestech.console.utils;

import java.util.Date;

import org.springframework.beans.propertyeditors.PropertiesEditor;

import com.google.common.base.Strings;
import com.google.common.primitives.Ints;

import cn.cerestech.framework.core.Dates;

public class AngularDateEditor extends PropertiesEditor {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (Strings.isNullOrEmpty(text)) {
			setValue(null);
		} else {
			Date dt = null;

			if (Ints.tryParse(text.charAt(0) + "") == null) {
				// 以字母开头
				dt = new Date(text);
				setValue(dt);
				return;
			} else if (text.contains("T") && text.contains("Z")) {
				text = text.replace("T", " ");
				text = text.replace("Z", "");
			}

			if (text.contains(".")) {
				// 带毫秒
				dt = Dates.parseDateTimeMillisecond(text);
			} else if (text.contains(":")) {
				// 带时间
				dt = Dates.parse(text);
			} else {
				// 只有日期
				dt = Dates.parseDate(text);
			}
			setValue(dt);
		}

	}

	@Override
	public String getAsText() {
		return getValue().toString();
	}

	public static void main(String[] arugs) {
		Date dt = new Date("Sat Jan 30 2016 23:55:00 GMT+0800 (CST)");
		System.out.println(dt);
	}

}