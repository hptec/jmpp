package cn.cerestech.middleware.aliyunoss.provider;

public class BeiJingAliyunOss implements AliyunOssProvider {
	
	private String name="北京";

	@Override
	public String getInternalEndPoint() {
		return "oss-cn-beijing-internal.aliyuncs.com";
	}

	@Override
	public String getEndPoint() {
		return "oss-cn-beijing.aliyuncs.com";
	}

	@Override
	public String getName() {
		return name;
	}

}
