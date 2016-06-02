package cn.cerestech.framework.support.storage.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.utils.FileExtensions;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.framework.support.storage.QueryRequest;
import cn.cerestech.framework.support.storage.enums.WatermarkConfigKey;
import cn.cerestech.framework.support.storage.service.StorageService;

/**
 * TODO 这个filter还要等小春开发
 * 
 * @author harryhe
 *
 */
@Service
public class WatermarkFilter implements Filter {

	private Logger log = LogManager.getLogger();

	@Autowired
	protected ConfigService configService;
	@Autowired
	StorageService localstorageService;

	/**
	 * 返回工程水印图片目录
	 * 
	 * @param path
	 * @return
	 */
	protected String watermarkPath() {
		return configService.query(WatermarkConfigKey.FRAMEWORK_SUPPORT_LOCALSTORAGE_WATERMARK_FILE_PATH).stringValue();
	}

	@Override
	public Boolean extMatchs(String ext) {
		return FileExtensions.isImage(ext);
	}

	@Override
	public byte[] process(byte[] inputBuffer, String paramString, QueryRequest context) {
		// Images img = new Images(inputBuffer, context.getExt());
		// return img.mask(watermarkPath(), 1.0f).toBytes();
		return null;
	}

	@Override
	public String getName() {
		return "watermark";
	}

}
