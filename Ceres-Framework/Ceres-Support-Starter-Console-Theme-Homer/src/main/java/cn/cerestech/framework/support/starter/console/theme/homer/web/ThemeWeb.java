package cn.cerestech.framework.support.starter.console.theme.homer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.google.common.io.Files;

import cn.cerestech.framework.support.starter.ContentType;
import cn.cerestech.framework.support.starter.console.theme.homer.provider.HomerThemeProvider;
import cn.cerestech.framework.support.starter.operator.ZipOutOperator;
import cn.cerestech.framework.support.starter.web.WebSupport;

@RestController
@RequestMapping("/api/theme")
public class ThemeWeb extends WebSupport implements ZipOutOperator {

	@Autowired
	HomerThemeProvider themeProvider;

	@RequestMapping("/query/**")
	public void query() throws Throwable {
		String srcKey = getRequest().getRequestURI();
		srcKey = srcKey.substring("/api/theme/query/".length());
		String ext = Strings.nullToEmpty(Files.getFileExtension(srcKey));
		byte[] content = themeProvider.get(srcKey);
		zipOut(content, ContentType.getByExtension(ext));
	}

}
