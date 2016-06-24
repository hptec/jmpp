package cn.cerestech.framework.support.classpath.webapi.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import cn.cerestech.framework.support.classpath.webapi.service.ClasspathComponentService;
import cn.cerestech.framework.support.web.ContentType;
import cn.cerestech.framework.support.web.web.WebApi;
import cn.cerestech.support.classpath.ClasspathService;

@RestController
@RequestMapping("/api/classpath")
public class ClasspathWebApi extends WebApi {

	private Logger log = LogManager.getLogger();

	@Autowired
	ClasspathService classpathService;

	@Autowired
	ClasspathComponentService componentService;

	private List<String> excludeExtension = Lists.newArrayList("java", "class", "properties", "xml", "mf", "");

	@RequestMapping("/query/**")
	public void query() throws Throwable {
		String srcKey = getRequest().getRequestURI();
		srcKey = srcKey.substring("/api/classpath/query/".length());
		log.trace("query classpath file: " + srcKey);

		byte[] bytesToRet = new byte[0];
		String ext = Strings.nullToEmpty(Files.getFileExtension(srcKey));

		if (!excludeExtension.contains(Strings.nullToEmpty(ext).toLowerCase())) {
			// 首先检查记录
			if (componentService.exist(srcKey)) {
				// 读取动态内容
				bytesToRet = componentService.getContent(srcKey).getBytes();
			} else {
				// 读取classpath文件内容
				bytesToRet = classpathService.findByUri(srcKey);
			}
		}
		// 文件记录
		if (bytesToRet == null || bytesToRet.length == 0) {
			getResponse().sendError(404);
		} else {

			zipOut(bytesToRet, ContentType.getByExtension(ext));
		}
	}

}
