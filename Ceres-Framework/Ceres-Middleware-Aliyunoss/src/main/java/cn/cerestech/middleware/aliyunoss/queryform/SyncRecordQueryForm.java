package cn.cerestech.middleware.aliyunoss.queryform;

import cn.cerestech.console.queryform.QueryForm;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.middleware.aliyunoss.enums.Providers;

public class SyncRecordQueryForm extends QueryForm {

	public SyncRecordQueryForm() {
		setTitle("同步记录");

		addColumnText("oss_provider", "服务商");
		addColumnText("file_id", "文件名");
		addColumnText("size", "大小");
		addColumnDate("create_time", "创建时间");
		addColumnEnum("oss_synced", "上传", YesNo.class);
		addColumnDate("oss_sync_time", "上传时间");
		addColumnText("oss_sync_errormsg", "错误消息");
		addColumnText("original_filename", "源文件名");

		addTermEnum("provider", "-请选择服务商-", null, Providers.class);
		addTermEnum("sync", "-请选择同步状态-", null, YesNo.class);
		addTermText("keyword", "文件名", null);
		addTermDateRange("fromto", "请选择日期范围", null);
	}

}
