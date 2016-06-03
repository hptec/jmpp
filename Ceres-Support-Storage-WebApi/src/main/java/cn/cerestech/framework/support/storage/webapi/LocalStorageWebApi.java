package cn.cerestech.framework.support.storage.webapi;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.storage.entity.StorageFile;
import cn.cerestech.framework.support.storage.service.StorageService;
import cn.cerestech.framework.support.web.ContentType;
import cn.cerestech.framework.support.webapi.WebApi;

@RestController
@RequestMapping("api/storage")
public class LocalStorageWebApi extends WebApi {

	private Logger log = LogManager.getLogger();
	@Autowired
	StorageService storageService;

	@RequestMapping("/get/**")
	public void query() throws Throwable {
		String srcKey = getRequest().getRequestURI();
		srcKey = srcKey.substring("/api/storage/get/".length());
		log.trace("query local file: " + srcKey);

		Optional<StorageFile> optionFile = storageService.queryCache(srcKey);
		if (!optionFile.isPresent()) {
			getResponse().sendError(404);
		} else {
			StorageFile file = optionFile.get();
			if (file == null || file.getBytes() == null || file.getBytes().length == 0) {
				getResponse().sendError(404);
			} else {
				String ext = Files.getFileExtension(srcKey);
				zipOut(file.getBytes(), ContentType.getByExtension(ext));
			}
		}
	}

	@RequestMapping("/upload")
	public void upload(@RequestParam MultipartFile[] myfiles, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		List<MultipartFile> files = Lists.newArrayList();
		MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
		r.getMultiFileMap().forEach((k, v) -> {
			files.addAll(v);
		});

		List<StorageFile> ret = storageService.put(files);
		if (ret.size() == 0) {
			zipOut(Jsons.from(new Object()));
		} else if (ret.size() == 1) {
			zipOut(Jsons.from(ret.get(0)));
		} else {
			zipOut(ret);
		}

		zipOut(ret);
	}

}
