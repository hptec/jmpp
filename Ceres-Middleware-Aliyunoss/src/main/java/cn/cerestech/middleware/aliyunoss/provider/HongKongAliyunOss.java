package cn.cerestech.middleware.aliyunoss.provider;

public class HongKongAliyunOss implements AliyunOssProvider {

	private String name = "香港";

	@Override
	public String getInternalEndPoint() {
		return "oss-cn-hongkong-internal.aliyuncs.com";
	}

	@Override
	public String getEndPoint() {
		return "oss-cn-hongkong.aliyuncs.com";
	}

	@Override
	public String getName() {
		return name;
	}

}
