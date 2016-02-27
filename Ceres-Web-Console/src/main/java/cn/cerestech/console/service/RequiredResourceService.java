package cn.cerestech.console.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cerestech.console.annotation.RequiredResource;
import cn.cerestech.framework.core.components.ComponentDispatcher;

/**
 * 后台工程引入js/css文件的方法
 * 
 * @author harryhe
 *
 */
@Service
public class RequiredResourceService implements ComponentDispatcher {
	private static List<String> css = Lists.newArrayList();
	private static List<String> js = Lists.newArrayList();

	private static Logger log = LogManager.getLogger();

	public static List<String> getCss() {
		return css;
	}

	public static void addCss(String cssFile) {
		if (!Strings.isNullOrEmpty(cssFile) && !css.contains(cssFile)) {
			css.add(cssFile);
		}
	}

	public static List<String> getJs() {
		return js;
	}

	public static void addJs(String jsFile) {
		if (!Strings.isNullOrEmpty(jsFile) && !js.contains(jsFile)) {
			js.add(jsFile);
		}
	}

	@Override
	public void recive(String beanName, Object bean) {
		if (bean != null) {
			RequiredResource anno = bean.getClass().getAnnotation(RequiredResource.class);
			if (anno != null) {
				String[] jsArr = anno.js();
				for (String jsStr : jsArr) {
					RequiredResourceService.addJs(jsStr);
				}
				String[] cssArr = anno.css();
				for (String cssStr : cssArr) {
					RequiredResourceService.addJs(cssStr);
				}
			}
		}

	}

	@Override
	public void onComplete() {
	}

}
