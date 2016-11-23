package cn.cerestech.middleware.aliyun.oss.service;

import java.io.ByteArrayInputStream;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.storage.dao.StorageDao;
import cn.cerestech.framework.support.storage.entity.StorageFile;
import cn.cerestech.middleware.aliyun.oss.errorcode.AliyunOssErrorCodes;

@Service
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssService {

	@NotNull
	@NotBlank
	private String path;// 带bucketName的互联网访问前缀
	@NotNull
	@NotBlank
	private String endPoint;// 数据交换地址
	@NotNull
	@NotBlank
	private String accessKeyId;
	@NotNull
	@NotBlank
	private String accessKeySecret;
	@NotNull
	@NotBlank
	private String bucketName;

	@Autowired
	StorageDao storageDao;

	public OSSClient getClient() {
		return new OSSClient(endPoint, accessKeyId, accessKeySecret);
	}

	public Result<StorageFile> upload(StorageFile file) {
		OSSClient c = getClient();
		// 检查bucket是否存在
		if (!c.doesBucketExist(bucketName)) {
			return Result.error(AliyunOssErrorCodes.BUCKET_NOT_FOUND);
		}

		// 上传
		@SuppressWarnings("unused")
		PutObjectResult result = c.putObject(bucketName, removeSlash(file.getLocalUri()),
				new ByteArrayInputStream(file.getBytes()));
		// 暂不解决上传错误的问题，默认成功
		file.setHttpUri(path + file.getLocalUri());
		storageDao.save(file);
		return Result.success(file);
	}

	/**
	 * 移除前面的斜杠
	 * 
	 * @param uri
	 * @return
	 */
	private String removeSlash(String uri) {
		if (uri.startsWith("/")) {
			return uri.substring(1);
		} else {
			return uri;
		}
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
