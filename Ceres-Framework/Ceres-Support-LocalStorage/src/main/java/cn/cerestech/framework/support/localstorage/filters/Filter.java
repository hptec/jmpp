package cn.cerestech.framework.support.localstorage.filters;

import cn.cerestech.framework.support.localstorage.QueryRequest;

public interface Filter {

	Boolean extMatchs(String ext);

	byte[] process(byte[] inputBuffer, String paramString, QueryRequest context);

	String getName();

}
