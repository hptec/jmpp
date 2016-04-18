package cn.cerestech.framework.support.web;

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

import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.PlatformCategory;
import cn.cerestech.framework.support.web.annotation.PlatformDefaultPage;
import cn.cerestech.support.classpath.ClasspathService;

@EnableWebMvc
@ControllerAdvice
@Configuration
public class AngularJsHtml5ModeSupport extends ResponseEntityExceptionHandler implements ComponentDispatcher {

	private static Map<PlatformCategory, Object> indexPages = Maps.newHashMap();
	private Logger log = LogManager.getLogger();

	@Autowired
	ClasspathService classpathService;

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ResponseEntity<Object> entity = null;

		Cookies cookies = Cookies.from(WebUtils.getCurrentRequest());
		if (cookies.exist(WebSupport.COOKIE_CERES_PLATFORM)) {
			PlatformCategory platform = EnumCollector.forClass(PlatformCategory.class)
					.keyOf(cookies.getValue(WebSupport.COOKIE_CERES_PLATFORM));
			if (platform != null && indexPages.containsKey(platform)) {
				Object obj = indexPages.get(platform);
				Method[] methods = obj.getClass().getMethods();
				for (Method m : methods) {
					if (m.isAnnotationPresent(PlatformDefaultPage.class)) {
						try {
							Object result = m.invoke(obj);
							if (result instanceof byte[]) {
								byte[] bytes = (byte[]) result;
								entity = new ResponseEntity<Object>(bytes, headers, HttpStatus.FOUND);
							} else {
								new ResponseEntity<Object>(result, headers, HttpStatus.FOUND);
							}

						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						// | UnsupportedEncodingException
						e) {
							log.catching(e);
						}
					}
				}

			}

		}

		return entity;
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
				if (m.isAnnotationPresent(PlatformDefaultPage.class)) {
					PlatformDefaultPage boot = m.getAnnotation(PlatformDefaultPage.class);
					if (indexPages.get(boot.value()) != null) {
						throw new IllegalArgumentException("PlatformDefaultPage conflict: " + boot.value());
					} else {
						indexPages.put(boot.value(), bean);
					}

					// 将Boot放入requriejsService供输出启动配置用
					// requirejsService.putBoot(boot.platform(), boot);
				}
			}
		}
	}

	@Override
	public void onComplete() {

	}

}