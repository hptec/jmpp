package cn.cerestech.framework.support.classpath.webapi;

/**
 * 提供自定义的classpath内容提供
 * 
 * @author harryhe
 *
 */
public interface ClasspathUriProvider {

	String defineUri();

	String getContent();
}
