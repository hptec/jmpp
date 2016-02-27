package cn.cerestech.console.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.localstorage.entity.SysFile;
import cn.cerestech.framework.support.localstorage.service.ResourceService;

@Controller
@RequestMapping("$$ceres_sys/console/base")
public class BaseConsoleCtrl extends WebConsoleSupport {

	@Autowired
	ResourceService resourceService;

//	@Autowired
//	AliyunOssMonitor aliyunOssMonitor;

	@RequestMapping("res/cp")
	public @ResponseBody void resourceClasspath(@RequestParam("id") String id) throws Throwable {
		// 文件记录
		byte[] res = resourceService.queryClasspath(id);
		if (res == null || res.length == 0) {
			getResponse().sendError(404);
		} else {
			String ext = Strings.nullToEmpty(Files.getFileExtension(id));
			zipOut(res, resourceService.resovleContentType(ext));
		}
	}

	@RequestMapping("res/fs/**")
	public @ResponseBody void resouce() throws Throwable {
		String srcKey = getRequest().getRequestURI();
		srcKey = srcKey.substring("/$$ceres_sys/console/base/res/fs/".length());

		SysFile sysfile = resourceService.queryFileStorage(srcKey);
		if (sysfile == null || sysfile.getBytes() == null || sysfile.getBytes().length == 0) {
			getResponse().sendError(404);
		} else {
			String ext = Files.getFileExtension(srcKey);
			zipOut(sysfile.getBytes(), resourceService.resovleContentType(ext));
		}
	}

	@RequestMapping("res/upload")
	public @ResponseBody String resourceUpload(@RequestParam MultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		List<SysFile> retList = Lists.newArrayList();

		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		r.getMultiFileMap().forEach((k, v) -> {
			v.forEach(file -> {
//				SysFile sysfile = resourceService.put(file);
				SysFile sysfile = resourceService.putImg(file, true, null);//TODO 打水印重新封装
				if (sysfile != null) {
					retList.add(sysfile);
//					aliyunOssMonitor.add(mysqlService.queryById(AliyunOssSysFile.class, sysfile.getId()));
				}
			});
		});

		return Jsons.toJson(retList);
	}

	/**
	 * 富文本上传文件框插件
	 * 
	 * @param myfiles
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("image/kindeditor/upload")
	@LoginRequired
	public @ResponseBody String upload(@RequestParam MultipartFile[] myfiles, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		List<MultipartFile> fileList = r.getMultiFileMap().values().stream().findFirst().orElse(null);
		if (fileList != null && !fileList.isEmpty()) {
			MultipartFile file = fileList.stream().findFirst().orElse(null);
			SysFile sysfile = resourceService.put(file);
			if (sysfile != null) {
//				aliyunOssMonitor.add(mysqlService.queryById(AliyunOssSysFile.class, sysfile.getId()));
				String url = "/$$ceres_sys/console/base/res/fs/" + sysfile.getFile_id();
				return "{" + "     \"error\" : 0," + "    \"url\" : \"" + url + "\"" + "}";
			}
		}

		return "{" + "     \"error\" : 1," + "    \"message\" : \"上传失败\"" + "}";
	}

	@RequestMapping("queryform/template")
	@LoginRequired
	public @ResponseBody void upload(@RequestParam("classname") String className) {

	}

}
