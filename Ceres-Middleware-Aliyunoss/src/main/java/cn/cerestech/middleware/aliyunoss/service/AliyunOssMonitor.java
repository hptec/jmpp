package cn.cerestech.middleware.aliyunoss.service;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.monitor.AbstractMonitor;
import cn.cerestech.middleware.aliyunoss.entity.AliyunOssSysFile;

@Service
public class AliyunOssMonitor extends AbstractMonitor<AliyunOssSysFile> {

	@Autowired
	protected AliyunOssService aliyunOssService;

	@Override
	public Supplier<List<AliyunOssSysFile>> getReloader() {
		return () -> {
			if (aliyunOssService.isAliyunOssAvailable()) {
				return mysqlService.queryBy(AliyunOssSysFile.class, " oss_synced='" + YesNo.NO.key() + "'");
			} else {
				// 没有开启阿里云则返回空集合
				return Lists.newArrayList();
			}
		};
	}

	@Override
	public Function<AliyunOssSysFile, Boolean> getMonitor() {
		return sysfile -> {
			if (sysfile.getOss_synced() == null || YesNo.NO.key().equals(sysfile.getOss_synced())) {
				// 没有上传则上传
				aliyunOssService.upload(sysfile.getId());
				return Boolean.TRUE;
			} else {
				return Boolean.TRUE;
			}
		};

	}

	/**
	 * 10秒一次
	 */
	@Override
	public Boolean isRunable() {
		if (getLastRunTime() == null) {
			return Boolean.TRUE;
		} else {
			return System.currentTimeMillis() > (getLastRunTime().getTime() + 10 * 1000L);
		}
	}

}
