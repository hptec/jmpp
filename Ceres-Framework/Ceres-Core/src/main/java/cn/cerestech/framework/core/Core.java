package cn.cerestech.framework.core;

import java.nio.charset.Charset;

public class Core {

	public static String charsetEncoding() {
		return "UTF-8";
	}

	public static Charset charset() {
		return Charset.forName(charsetEncoding());
	}

}
