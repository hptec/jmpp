package cn.cerestech.middleware.location.mobile;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.regexp.RegExp;

@Embeddable
public class Mobile {

	@Transient
	private static int maskLength = 3;
	@Transient
	private static char maskChar = '*';

	private CallingCode code = CallingCode.CHINA;

	private String number;

	public Boolean legal() {
		switch (code) {
		case CHINA:
			if (Strings.isNullOrEmpty(number)) {
				return Boolean.FALSE;
			}
			return RegExp.match(RegExp.REGEXP_PHONE, number);
		default:
			throw new IllegalArgumentException("nation is not supported.");
		}
	}

	public static Mobile from(CallingCode code, String number) {
		Mobile m = new Mobile();
		m.number(number);
		m.code(code);
		return m;
	}

	public static Mobile fromChina(String number) {
		return Mobile.from(CallingCode.CHINA, number);
	}

	public CallingCode code() {
		return code;
	}

	public Mobile code(CallingCode code) {
		this.code = code;
		return this;
	}

	public String number() {
		return number;
	}

	public Mobile number(String number) {
		this.number = number;
		return this;
	}

	public Mobile maskLength(int len) {
		maskLength = len;
		return this;
	}

	public Mobile maskChar(char c) {
		maskChar = c;
		return this;
	}

	public String mask() {
		if (!legal()) {
			return "";
		}
		number = number.trim();
		int prePos = new BigDecimal(number.length() - maskLength).divide(new BigDecimal(2), RoundingMode.DOWN)
				.intValue();
		prePos = prePos < 0 ? 0 : prePos;

		return new StringBuffer(number).replace(prePos, maskLength + prePos, Strings.padEnd("", maskLength, maskChar))
				.toString();
	}

	public String fullNumber() {
		return "+" + code.key() + number;
	}

}
