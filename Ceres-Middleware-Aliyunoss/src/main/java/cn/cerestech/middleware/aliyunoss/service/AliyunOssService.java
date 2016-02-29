package cn.cerestech.middleware.aliyunoss.service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.framework.support.localstorage.service.ResourceService;
import cn.cerestech.middleware.aliyunoss.entity.AliyunOssSysFile;
import cn.cerestech.middleware.aliyunoss.enums.AliyunOssConfigKey;
import cn.cerestech.middleware.aliyunoss.provider.AliyunOssProvider;
import cn.cerestech.middleware.aliyunoss.provider.BeiJingAliyunOss;
import cn.cerestech.middleware.aliyunoss.provider.HangZhouAliyunOss;
import cn.cerestech.middleware.aliyunoss.provider.HongKongAliyunOss;
import cn.cerestech.middleware.aliyunoss.provider.QingDaoAliyunOss;
import cn.cerestech.middleware.aliyunoss.provider.ShangHaiAliyunOss;
import cn.cerestech.middleware.aliyunoss.provider.ShenZhenAliyunOss;
import cn.cerestech.middleware.aliyunoss.provider.SiliconValleyAliyunOss;
import cn.cerestech.middleware.aliyunoss.provider.SingaporeAliyunOss;

@Service
public class AliyunOssService extends BaseService {

	@Autowired
	private ConfigService configService;

	protected static List<AliyunOssProvider> providers = Lists.newArrayList(new BeiJingAliyunOss(),
			new HangZhouAliyunOss(), new HongKongAliyunOss(), new QingDaoAliyunOss(), new ShangHaiAliyunOss(),
			new ShenZhenAliyunOss(), new SiliconValleyAliyunOss(), new SingaporeAliyunOss());

	@Autowired
	protected ResourceService fileStorageService;

	public void upload(Long sysFileId) {

		if (!isAliyunOssAvailable()) {
			return;
		}

		AliyunOssSysFile sysFile = AliyunOssSysFile.from(fileStorageService.get(sysFileId));
		if (sysFile == null) {
			return;
		}

		// 创建上传Object的Metadata
		ObjectMetadata meta = new ObjectMetadata();

		// 必须设置ContentLength
		meta.setContentLength(sysFile.getBytes().length);

		// 上传Object.
		try {
			PutObjectResult result = getClient().putObject(getBucketName(), sysFile.getFile_id(),
					new ByteArrayInputStream(sysFile.getBytes()), meta);
			sysFile.setOss_result_etag(result.getETag());
			sysFile.setOss_synced(YesNo.YES.key());
		} catch (Throwable t) {
			sysFile.setOss_sync_errormsg(t.getMessage());
			sysFile.setOss_synced(YesNo.NO.key());
		} finally {
			sysFile.setOss_sync_time(new Date());
			sysFile.setOss_provider(getProvider().getName());
		}
		// 打印ETag

		mysqlService.update(sysFile);
	}

	/**
	 * 检测阿里云在系统配置中是否开启(是否开启且已经配置参数)
	 * 
	 * @return
	 */
	public Boolean isAliyunOssAvailable() {
		if (!configService.query(AliyunOssConfigKey.ALIYUN_OSS_OPEN).boolValue()) {
			// 开关没有开启
			return Boolean.FALSE;
		}

		if (getProvider() == null || Strings.isNullOrEmpty(accessKeyId()) || Strings.isNullOrEmpty(accessKeySecret())
				|| Strings.isNullOrEmpty(getBucketName())) {
			// 参数配置不完整
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	public OSSClient getClient() {
		AliyunOssProvider provider = getProvider();
		if (provider == null) {
			return null;
		}

		return new OSSClient(provider.getEndPoint(), accessKeyId(), accessKeySecret());
	}

	public String accessKeyId() {
		return configService.query(AliyunOssConfigKey.ALIYUN_OSS_ACCESSKEY_ID).stringValue();
	}

	public String accessKeySecret() {
		return configService.query(AliyunOssConfigKey.ALIYUN_OSS_ACCESSKEY_SECRET).stringValue();
	}

	public String getBucketName() {
		String name = configService.query(AliyunOssConfigKey.ALIYUN_OSS_BUCKET_NAME).stringValue();
		return name;
	}

	public void accessKeyId(String accessKeyId) {
		configService.update(AliyunOssConfigKey.ALIYUN_OSS_ACCESSKEY_ID, accessKeyId);
	}

	public void accessKeySecret(String accessKeySecret) {
		configService.update(AliyunOssConfigKey.ALIYUN_OSS_ACCESSKEY_SECRET, accessKeySecret);
	}

	public void setBucketName(String bucketName) {
		configService.update(AliyunOssConfigKey.ALIYUN_OSS_BUCKET_NAME, bucketName);
	}

	/**
	 * 获取当前正在使用的Provider
	 * 
	 * @return
	 */
	public AliyunOssProvider getProvider() {
		String name = configService.query(AliyunOssConfigKey.ALIYUN_OSS_PROVIDER).stringValue();
		if (Strings.isNullOrEmpty(name)) {
			return new BeiJingAliyunOss();
		}

		List<AliyunOssProvider> providers = getProviders();
		for (AliyunOssProvider provider : providers) {
			if (provider.getName().equalsIgnoreCase(name)) {
				return provider;
			}
		}

		return new BeiJingAliyunOss();
	}

	public void setProvider(AliyunOssProvider provider) {
		if (provider == null) {
			return;
		}

		configService.update(AliyunOssConfigKey.ALIYUN_OSS_PROVIDER, provider.getName());

	}

	public List<AliyunOssProvider> getProviders() {
		return providers;
	}

	/**
	 * 根据名称获取Provider
	 * 
	 * @param name
	 * @return
	 */
	public AliyunOssProvider getProvider(String name) {
		List<AliyunOssProvider> providers = getProviders();
		for (AliyunOssProvider p : providers) {
			if (p.getName().equalsIgnoreCase(name)) {
				return p;
			}
		}

		return new BeiJingAliyunOss();
	}

	public String visitPrefix() {

		return "http://" + getBucketName() + "." + getProvider().getEndPoint();
	}

	public List<? extends AliyunOssSysFile> search(Class<? extends AliyunOssSysFile> clazz, AliyunOssProvider provider,
			YesNo sync, String filename, Date fromDate, Date toDate, Integer maxRecords) {
		maxRecords = maxRecords == null ? BaseEntity.MAX_QUERY_RECOREDS : maxRecords;
		StringBuffer where = new StringBuffer(" 1=1 ");
		if (provider != null) {
			where.append(" AND oss_provider='" + provider.getName() + "'");
		}

		if (sync != null) {
			where.append(" AND `oss_synced` = '" + sync.key() + "'");
		}
		if (!Strings.isNullOrEmpty(filename) && !Strings.nullToEmpty(filename).contains("'")) {
			where.append(" AND file_id LIKE '%" + filename + "%'");
		}

		if (fromDate != null) {
			where.append(" AND create_time >= timestamp('" + FORMAT_DATE.format(fromDate) + " 00:00:00')");
		}

		if (toDate != null) {
			where.append(" AND create_time <= timestamp('" + FORMAT_DATE.format(toDate) + " 23:59:49')");
		}
		// 排序
		where.append(" ORDER BY create_time DESC ");

		// 组装分页
		where.append(" LIMIT " + maxRecords);
		List<? extends AliyunOssSysFile> result = mysqlService.queryBy(clazz, where.toString());
		return result;
	}

}
