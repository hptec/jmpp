package cn.cerestech.framework.support.starter.interceptor;

import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cn.cerestech.framework.support.starter.provider.MainPageProvider;

@EnableWebMvc
@ControllerAdvice
@Configuration
public class AngularJsHtml5ModeSupport extends ResponseEntityExceptionHandler {

	private Logger log = LogManager.getLogger();

	@Autowired
	MainPageProvider mainPageProvider;

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ResponseEntity<Object> entity = null;

		if (mainPageProvider.isHtml5Mode()) {
			String content = mainPageProvider.get();
			// content = new String(content.getBytes(),
			// Charset.forName("ISO8859-1"));
			headers.setContentType(MediaType.parseMediaType("text/html;charset=UTF-8"));
			entity = new ResponseEntity<Object>(content, headers, HttpStatus.FOUND);
		}

		return entity;
	}

	@Bean // 启用404报错
	public DispatcherServlet dispatcherServlet() {
		DispatcherServlet ds = new DispatcherServlet();
		ds.setThrowExceptionIfNoHandlerFound(true);
		return ds;
	}

}