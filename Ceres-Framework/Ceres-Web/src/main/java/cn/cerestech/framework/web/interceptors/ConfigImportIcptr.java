package cn.cerestech.framework.web.interceptors;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.cerestech.framework.core.Props;
import cn.cerestech.framework.support.configuration.entity.SysConfig;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.framework.web.Web;
import cn.cerestech.framework.web.annotation.ConfigImport;
import cn.cerestech.framework.web.annotation.DBConfigImport;

public class ConfigImportIcptr implements HandlerInterceptor {
	private Logger log = LogManager.getLogger();

	@Autowired
	ConfigService configService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView ma) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String ctx = request.getContextPath();
		request.setAttribute("ctx", ctx);
		request.setAttribute("img_root", Web.imgRoot());

		if (obj instanceof HandlerMethod) {

			// 导入配置文件
			HandlerMethod m = (HandlerMethod) obj;
			ConfigImport ci = m.getMethodAnnotation(ConfigImport.class);
			if (ci != null) {
				String[] configs = ci.value();
				if (configs.length > 0) {
					for (String file : configs) {
						Props prop = Props.on(file);
						Set<String> keys = prop.stringPropertyNames();
						for (String key : keys) {
							String value = prop.getProperty(key);
							String keyStr = key.replace(".", "_");
							request.setAttribute(keyStr, value);
						}
						log.trace("Put " + keys.size() + " properties of file[" + file + "] at " + m.toString());
					}
				}
			}

			// 导入数据库配置
			DBConfigImport dbci = m.getMethodAnnotation(DBConfigImport.class);
			if (dbci != null) {
				List<SysConfig> list = configService.all();
				for (SysConfig c : list) {
					String keyStr = c.getKey().replace(".", "_");
					String value=c.getValue();
					request.setAttribute(keyStr, value);
				}
				log.trace("Put " + list.size() + " properties of DB Table[ sys_config ] at " + m.toString());
			}

		}
		return true;
	}
}
