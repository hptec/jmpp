package cn.cerestech.framework.support.storage.filter;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.primitives.Ints;

import cn.cerestech.framework.core.images.Images;
import cn.cerestech.framework.core.utils.FileExtensions;
import cn.cerestech.framework.support.storage.QueryRequest;
import cn.cerestech.framework.support.storage.service.StorageService;

@Component
public class StretchCenterFilter implements Filter {
	private Logger log = LogManager.getLogger();

	@Autowired
	StorageService localstorageService;

	@Override
	public Boolean extMatchs(String ext) {
		return FileExtensions.isImage(ext);
	}

	@Override
	public byte[] process(byte[] inputBuffer, String paramString, QueryRequest context) {
		if (inputBuffer == null || inputBuffer.length == 0) {
			log.error("image empty when StretchCenterFilter");
			return inputBuffer;
		}

		paramString = Strings.nullToEmpty(paramString);
		if (!paramString.contains("w") || !paramString.contains("h") || !paramString.contains("_")) {
			// log.error("parameter error for StretchCenterFilter: " +
			// paramString + "\nContext: " + context.toPretty());
			return inputBuffer;
		}
		Integer w = null, h = null;
		List<String> params = Splitter.on("_").trimResults().splitToList(paramString);
		for (String str : params) {
			if (str.startsWith("w")) {
				w = Ints.tryParse(str.substring(1));
			} else if (str.startsWith("h")) {
				h = Ints.tryParse(str.substring(1));
			}
		}

		if (w != null || h != null) {
			Images img = Images.of(inputBuffer, context.getExt());
			return img.stretchCenterClip(w, h).toBytes();
		} else {
			return inputBuffer;
		}
	}

	@Override
	public String getName() {
		return "stretchcenter";
	}

}
