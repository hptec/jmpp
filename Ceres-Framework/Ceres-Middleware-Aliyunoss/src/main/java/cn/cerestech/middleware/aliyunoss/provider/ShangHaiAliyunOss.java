package cn.cerestech.middleware.aliyunoss.provider;

public class ShangHaiAliyunOss implements AliyunOssProvider {

	private String name = "上海";
	@Override
	public String getInternalEndPoint() {
		return "oss-cn-shanghai-internal.aliyuncs.com";
	}

	@Override
	public String getEndPoint() {
		return "oss-cn-shanghai.aliyuncs.com";
	}

	@Override
	public String getName() {
		return name;
	}

}
