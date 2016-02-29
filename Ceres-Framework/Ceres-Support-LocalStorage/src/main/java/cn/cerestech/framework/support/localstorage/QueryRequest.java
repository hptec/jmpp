package cn.cerestech.framework.support.localstorage;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.io.Files;

import cn.cerestech.framework.core.json.Jsonable;

public class QueryRequest implements Jsonable {
	private String local_uri;
	private String filterString;
	private String ext;

	private static Logger log = LogManager.getLogger();

	public static QueryRequest fromHttpUri(String httpUri) {
		httpUri = Strings.nullToEmpty(httpUri);
		QueryRequest context = new QueryRequest();
		String ext = Files.getFileExtension(httpUri);
		context.setExt(ext);
		int atPos = httpUri.indexOf('@');
		if (atPos == -1) {
			// 不包含过滤器
			context.setLocal_uri(httpUri);
		} else {
			StringBuffer buffer = new StringBuffer(httpUri);
			buffer.delete(httpUri.length() - (Strings.isNullOrEmpty(ext) ? 0 : (ext.length() + 1)), httpUri.length());// 删除扩展名
			log.trace("ext deleted:" + buffer.toString());
			List<String> strList = Splitter.on('@').trimResults().splitToList(buffer.toString());
			context.setFilterString(strList.get(1));
			log.trace("filter string: " + context.getFilterString());
			context.setLocal_uri(strList.get(0) + (Strings.isNullOrEmpty(ext) ? "" : ("." + ext)));
			log.trace("context: " + context.toPretty());
		}
		return context;
	}

	public String getLocal_uri() {
		return local_uri;
	}

	public void setLocal_uri(String local_uri) {
		this.local_uri = local_uri;
	}

	public String getFilterString() {
		return filterString;
	}

	public void setFilterString(String filterString) {
		this.filterString = filterString;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}
