package cn.cerestech.middleware.aliyunoss.provider;

public class QingDaoAliyunOss implements AliyunOssProvider {

	private String name = "青岛";

	@Override
	public String getInternalEndPoint() {
		return "oss-cn-qingdao-internal.aliyuncs.com";
	}

	@Override
	public String getEndPoint() {
		return "oss-cn-qingdao.aliyuncs.com";
	}

	@Override
	public String getName() {
		return name;
	}

}
