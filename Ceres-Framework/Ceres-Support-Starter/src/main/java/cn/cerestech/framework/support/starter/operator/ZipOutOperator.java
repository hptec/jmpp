package cn.cerestech.framework.support.starter.operator;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import cn.cerestech.framework.core.environment.OsInfo;
import cn.cerestech.framework.core.json.Jsonable;
import cn.cerestech.framework.core.json.Jsons;

/**
 * 操作Gzip 输出的相关操作
 * 
 * @author harryhe
 *
 */
public interface ZipOutOperator extends ResponseOperator {
	/**
	 * gzip 方式输出数据
	 * 
	 * @param content
	 */
	default void zipOut(String content) {
		zipOut(content, "text/html");
	}

	default void zipOut(Jsonable jsonObj) {
		zipOut(jsonObj.getJson().toJson(), "application/json");
	}

	default void zipOut(Jsons jsonObj) {
		zipOut(jsonObj.toJson(), "application/json");
	}

	default void zipOut(List<?> list) {
		zipOut(Jsons.from(list).toJson());
	}

	/**
	 * gzip 方式输出数据
	 * 
	 * @param content
	 */
	default void zipOut(String content, String contentType) {
		byte[] bytes = new byte[0];
		try {
			getResponse().setCharacterEncoding(OsInfo.charsetEncoding());
			bytes = content.getBytes(OsInfo.charsetEncoding());
			zipOut(bytes, contentType);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * gzip 方式输出数据
	 * 
	 * @param bytes
	 */
	default void zipOut(byte[] bytes, String contentType) {
		HttpServletResponse response = this.getResponse();
		try {
			response.addHeader("Content-Encoding", "gzip");
			response.setCharacterEncoding(OsInfo.charsetEncoding());
			response.setContentType(contentType);
			response.setLocale(Locale.CHINA);
			ByteArrayOutputStream bais = new ByteArrayOutputStream();
			// 压缩
			GZIPOutputStream gos = new GZIPOutputStream(bais, true);
			gos.write(bytes, 0, bytes.length);
			gos.finish();
			byte[] res = bais.toByteArray();
			// 关闭输出流
			bais.flush();
			bais.close();
			ServletOutputStream out = response.getOutputStream();
			out.write(res);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	default void zipOutRequireJson(Object obj) {
		StringBuffer buffer = new StringBuffer("define(function() {");
		buffer.append("return ");
		buffer.append(Jsons.from(obj).toJson());
		buffer.append("});");
		zipOut(buffer.toString(), "application/javascript");
	}

}
