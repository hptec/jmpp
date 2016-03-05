package cn.cerestech.middleware.aliyunoss.provider;

public class ShenZhenAliyunOss implements AliyunOssProvider {

	private String name = "深圳";
	@Override
	public String getInternalEndPoint() {
		return "oss-cn-shenzhen-internal.aliyuncs.com";
	}

	@Override
	public String getEndPoint() {
		return "oss-cn-shenzhen.aliyuncs.com";
	}

	@Override
	public String getName() {
		return name;
	}

}
