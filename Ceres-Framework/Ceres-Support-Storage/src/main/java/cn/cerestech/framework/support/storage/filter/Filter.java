package cn.cerestech.framework.support.storage.filter;

import cn.cerestech.framework.support.storage.QueryRequest;

public interface Filter {

	Boolean extMatchs(String ext);

	byte[] process(byte[] inputBuffer, String paramString, QueryRequest context);

	String getName();

}
