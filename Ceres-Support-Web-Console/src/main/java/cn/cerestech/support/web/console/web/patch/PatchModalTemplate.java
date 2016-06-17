package cn.cerestech.support.web.console.web.patch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.cerestech.framework.support.web.WebSupport;
import cn.cerestech.support.classpath.ClasspathService;

/**
 * 不得以而为之，抢时间打的补丁。
 * @author harryhe
 *
 */
@Controller
@RequestMapping("$$ceres_sys/console")
public class PatchModalTemplate extends WebSupport {
	@Autowired
	ClasspathService classpathService;

	/**
	 * angular ui boostrap modal不可以加载模板的补丁
	 */
	@RequestMapping("uib/**")
	public void uibPatch() {
		String srcKey = getRequest().getRequestURI();
		srcKey = srcKey.substring("/$$ceres_sys/console/uib/".length());
		
		byte[] bytes=classpathService.findByUri("support/web/console/theme/homer/bower_components/ui-bootstrap/"+srcKey);
		zipOut(bytes,"text/html");
	}
}
