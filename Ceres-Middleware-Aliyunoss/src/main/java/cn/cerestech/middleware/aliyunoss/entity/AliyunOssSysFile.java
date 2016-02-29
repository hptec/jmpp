package cn.cerestech.middleware.aliyunoss.entity;

import java.util.Date;

import cn.cerestech.framework.core.StringTypes;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.enums.ColumnDataType;
import cn.cerestech.framework.support.localstorage.entity.SysFile;

@SuppressWarnings("serial")
@Table("$$sys_file")
public class AliyunOssSysFile extends SysFile {

	@Column(title = "站点")
	public String oss_provider;

	@Column(type = ColumnDataType.CHAR, title = "是否OSS已同步", defaultValue = "'N'")
	public String oss_synced = YesNo.NO.key();

	@Column(type = ColumnDataType.DATETIME, title = "OSS同步时间")
	public Date oss_sync_time;

	@Column(title = "OSS同步错误消息", type = ColumnDataType.TEXT)
	public String oss_sync_errormsg;

	@Column(title = "OSS同步结果")
	public String oss_result_etag;

	public static AliyunOssSysFile from(SysFile sysfile) {
		if (sysfile == null || sysfile.getBytes() == null || sysfile.getBytes().length == 0) {
			return null;
		}
		AliyunOssSysFile aliyunsf = new AliyunOssSysFile();
		aliyunsf.setBytes(sysfile.getBytes());
		aliyunsf.setContent_type(sysfile.getContent_type());
		aliyunsf.setCreate_time(sysfile.getCreate_time());
		aliyunsf.setFile_ext(sysfile.getFile_ext());
		aliyunsf.setFile_id(sysfile.getFile_id());
		aliyunsf.setFile_name(sysfile.getFile_name());
		aliyunsf.setId(sysfile.getId());
		aliyunsf.setOriginal_filename(sysfile.getOriginal_filename());
		aliyunsf.setSize(sysfile.getSize());
		return aliyunsf;
	}

	public Boolean isOssSynchronized() {
		return new StringTypes(getOss_synced()).boolValue();
	}

	public String getOss_synced() {
		return oss_synced;
	}

	public void setOss_synced(String oss_synced) {
		this.oss_synced = oss_synced;
	}

	public Date getOss_sync_time() {
		return oss_sync_time;
	}

	public void setOss_sync_time(Date oss_sync_time) {
		this.oss_sync_time = oss_sync_time;
	}

	public String getOss_result_etag() {
		return oss_result_etag;
	}

	public void setOss_result_etag(String oss_result_etag) {
		this.oss_result_etag = oss_result_etag;
	}

	public String getOss_sync_errormsg() {
		return oss_sync_errormsg;
	}

	public void setOss_sync_errormsg(String oss_sync_errormsg) {
		this.oss_sync_errormsg = oss_sync_errormsg;
	}

	public String getOss_provider() {
		return oss_provider;
	}

	public void setOss_provider(String oss_provider) {
		this.oss_provider = oss_provider;
	}

}
