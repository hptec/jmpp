package cn.cerestech.middleware.aliyunoss.provider;

public class HangZhouAliyunOss implements AliyunOssProvider {

	private String name="杭州";
	@Override
	public String getInternalEndPoint() {
		return "oss-cn-hangzhou-internal.aliyuncs.com";
	}

	@Override
	public String getEndPoint() {
		return "oss-cn-hangzhou.aliyuncs.com";
	}

	@Override
	public String getName() {
		return name;
	}

}
