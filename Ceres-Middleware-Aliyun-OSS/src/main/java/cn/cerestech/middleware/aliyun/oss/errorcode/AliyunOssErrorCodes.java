package cn.cerestech.middleware.aliyun.oss.errorcode;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum AliyunOssErrorCodes implements DescribableEnum {

	BUCKET_NOT_FOUND("BUCKET_NOT_FOUND", "未能查询到Bucket"), //
	;

	private String key, desc;

	private AliyunOssErrorCodes(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String desc() {
		return desc;
	}
}