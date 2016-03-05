package cn.cerestech.framework.support.html5mode;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.Core;
import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.platform.enums.PlatformCategory;
import cn.cerestech.framework.support.web.Cookies;
import cn.cerestech.framework.support.web.WebSupport;
import cn.cerestech.framework.support.web.WebUtils;
import cn.cerestech.support.classpath.ClasspathService;

@EnableWebMvc
@ControllerAdvice
@Configuration
public class AngularJsHtml5ModeSupport extends ResponseEntityExceptionHandler implements ComponentDispatcher {

	private static Map<PlatformCategory, Object> indexPages = Maps.newHashMap();
	public static final String COOKIE_CERES_PLATFORM = "ceres_platform";
	private Logger log = LogManager.getLogger();

	protected static final String THEME_PATH_PREFIX = "support/web/console/theme/";

	@Autowired
	ClasspathService classpathService;

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		StringBuffer contents = new StringBuffer();

		Cookies cookies = Cookies.from(WebUtils.getCurrentRequest());
		if (cookies.exist(COOKIE_CERES_PLATFORM)) {
			PlatformCategory platform = EnumCollector.forClass(PlatformCategory.class)
					.keyOf(cookies.getValue(COOKIE_CERES_PLATFORM));
			if (platform != null && indexPages.containsKey(platform)) {
				Object obj = indexPages.get(platform);
				Method[] methods = obj.getClass().getMethods();
				for (Method m : methods) {
					if (m.isAnnotationPresent(Html5ModeIndexPage.class)) {
						try {
							Object result = m.invoke(obj);
							if (result instanceof byte[]) {
								byte[] bytes = (byte[]) result;
								contents.append(new String(bytes, Core.charsetEncoding()));
							} else {
								contents.append(result);
							}

						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
								| UnsupportedEncodingException e) {
							log.catching(e);
						}
					}
				}

			}

		}

		return new ResponseEntity<Object>(contents.toString(), headers, HttpStatus.FOUND);
	}

	@Bean // 启用404报错
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet ds = new DispatcherServlet();
		ds.setThrowExceptionIfNoHandlerFound(true);
		return ds;
	}

	@Override
	// 接收首页设置
	public void recive(String beanName, Object bean) {
		if (bean instanceof WebSupport) {
			Method[] mList = bean.getClass().getMethods();
			for (Method m : mList) {
				if (m.isAnnotationPresent(Html5ModeIndexPage.class)) {
					Html5ModeIndexPage page = m.getAnnotation(Html5ModeIndexPage.class);
					if (indexPages.get(page.value()) != null) {
						throw new IllegalArgumentException("Html5ModeIndexPage conflict: " + page.value());
					} else {
						indexPages.put(page.value(), bean);
					}
				}
			}
		}
	}

	@Override
	public void onComplete() {

	}

}