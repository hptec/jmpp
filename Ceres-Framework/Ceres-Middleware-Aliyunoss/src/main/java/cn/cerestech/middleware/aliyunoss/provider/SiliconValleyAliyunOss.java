package cn.cerestech.middleware.aliyunoss.provider;

public class SiliconValleyAliyunOss implements AliyunOssProvider {

	private String name = "美国硅谷";

	@Override
	public String getInternalEndPoint() {
		return "oss-us-west-1-internal.aliyuncs.com";
	}

	@Override
	public String getEndPoint() {
		return "oss-us-west-1.aliyuncs.com";
	}

	@Override
	public String getName() {
		return name;
	}

}
