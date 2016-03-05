package cn.cerestech.framework.web;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;

import cn.cerestech.framework.core.HttpIOutils;
import cn.cerestech.framework.core.Images;
import cn.cerestech.framework.core.storage.FileStorage;
import cn.cerestech.framework.web.support.WebSupport;

@RequestMapping("")
@Controller
public class ResourceCtrl extends WebSupport{
	
	@RequestMapping("imgbase/**")
	public @ResponseBody void src(@RequestParam(value = "size", required = false) String size) throws IOException{
		String srcKey = getRequest().getRequestURI();
		srcKey = srcKey.substring(8);
		byte[] buffer = new byte[0];
		if (Strings.isNullOrEmpty(size)) {
			buffer = FileStorage.read(srcKey);
		} else {
			buffer = FileStorage.read(srcKey);
			
			Integer width = null, height = null;
			List<String> sizeList = Splitter.on("x").omitEmptyStrings().trimResults().splitToList(size);
			if (sizeList.size() == 2) {
				width = Ints.tryParse(sizeList.get(0));
				height = Ints.tryParse(sizeList.get(1));
			}
			
			if(width != null && height != null){
				buffer = new Images(buffer, "").stretchTo(width, height).toBytes();
			}
		}
		HttpIOutils.outputContent(getResponse(), buffer, "UTF-8");
	}
}
