package cn.cerestech.middleware.aliyunoss.provider;

public class SingaporeAliyunOss implements AliyunOssProvider {

	private String name = "亚太新加坡";

	@Override
	public String getInternalEndPoint() {
		return "oss-ap-southeast-1-internal.aliyuncs.com";
	}

	@Override
	public String getEndPoint() {
		return "oss-ap-southeast-1.aliyuncs.com";
	}

	@Override
	public String getName() {
		return name;
	}

}
