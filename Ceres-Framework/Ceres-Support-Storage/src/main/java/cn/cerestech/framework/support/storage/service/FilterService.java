package cn.cerestech.framework.support.storage.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.support.storage.QueryRequest;
import cn.cerestech.framework.support.storage.entity.StorageFile;
import cn.cerestech.framework.support.storage.filter.Channel;
import cn.cerestech.framework.support.storage.filter.Filter;
import cn.cerestech.framework.support.storage.filter.StretchCenterFilter;
import cn.cerestech.framework.support.storage.filter.WatermarkFilter;

@Service
public class FilterService {
	
	private Logger log=LogManager.getLogger();

	private static Map<String, Filter> filters = Maps.newHashMap();

	@Autowired
	private StretchCenterFilter sizeFilter;
	@Autowired
	private WatermarkFilter watermarkFilter;

	@PostConstruct
	protected void register() {
		filters.put(sizeFilter.getName(), sizeFilter);
		filters.put(watermarkFilter.getName(), watermarkFilter);
	}

	public StorageFile filter(StorageFile file, String filterString) {
		if (file == null) {
			return null;
		}

		List<Channel> channelList = parseChannels(filterString);
		for (Channel channel : channelList) {
			Filter filter = filters.get(channel.getName());
			if (filter != null && filter.extMatchs(file.getExtensionName())) {
				// 类型符合才处理
				byte[] buffer = filter.process(file.getBytes(), channel.getParameter(),
						QueryRequest.fromHttpUri(file.getHttpUri()));
				file.setBytes(buffer);
			}
		}

		// 拼装HttpUri
		StringBuffer strBuf = new StringBuffer(file.getLocalUri());
		strBuf.delete(strBuf.length() - file.getExtensionName().length() - 1, strBuf.length());
		strBuf.append("@" + filterString);
		strBuf.append("." + file.getExtensionName());
		log.trace("filter [" + file.getLocalUri() + "] to [" + file.getHttpUri() + "]");

		return file;
	}

	private List<Channel> parseChannels(String filterString) {
		List<Channel> retList = Lists.newArrayList();
		Splitter.on("|").omitEmptyStrings().trimResults().splitToList(Strings.nullToEmpty(filterString))
				.forEach(str -> {
					if (str.indexOf(":") != -1) {// 格式正确
						List<String> kv = Splitter.on(":").splitToList(str);
						retList.add(new Channel(kv.get(0), kv.get(1)));
					}
				});
		return retList;
	}

}
