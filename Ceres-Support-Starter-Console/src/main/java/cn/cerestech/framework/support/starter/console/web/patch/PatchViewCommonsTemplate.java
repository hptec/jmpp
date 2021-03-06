package cn.cerestech.framework.support.starter.console.web.patch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.support.classpath.service.ClasspathService;
import cn.cerestech.framework.support.starter.web.WebSupport;

/**
 * 不得以而为之，抢时间打的补丁。
 * @author harryhe
 *
 */
@RestController
@RequestMapping("views/common")
public class PatchViewCommonsTemplate extends WebSupport {
	@Autowired
	ClasspathService classpathService;

	/**
	 * angular ui boostrap modal不可以加载模板的补丁
	 */
	@RequestMapping("**")
	public void uibPatch() {
		String srcKey = getRequest().getRequestURI();
		srcKey = srcKey.substring("/views/common".length());
		
		byte[] bytes=classpathService.findByUri("support/web/console/theme/homer/views/common/"+srcKey);
		zipOut(bytes,"text/html");
	}
}
