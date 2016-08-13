package cn.cerestech.framework.support.theme.provider;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.cerestech.framework.support.classpath.service.ClasspathService;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class FreemarkerClasspathThemeProvider implements ThemeProvider {

	@Autowired
	ClasspathService classpathService;

	protected static StringTemplateLoader stringLoader;
	protected static Configuration configuration;

	static {
		stringLoader = new StringTemplateLoader();
		configuration = new Configuration();
		configuration.setTemplateLoader(stringLoader);
	}

	public String parse(String name, Map<String, Object> param) {
		Template tpl = null;
		try {
			Object o=stringLoader.findTemplateSource(name);
			if(o==null){
				// 模板不存在加载模板
				stringLoader.putTemplate(name, new String(classpathService.findByUri(getThemeRoot() + name)));
			}
			
			tpl = configuration.getTemplate(name);
			StringWriter writer = new StringWriter();
			tpl.process(param, writer);
			return writer.toString();
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		return "";
	}


}
