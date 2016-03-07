package cn.cerestech.middleware.sms.console.enums;

import cn.cerestech.support.web.console.menu.Menu;
import cn.cerestech.support.web.console.menu.MenuSet;
import cn.cerestech.support.web.console.menu.Page;

@Menu(parent = "TOP_SYSTEM_CONFIGURATION", key = "MIDDLEWARE_SMS_MENU", caption = "短信服务")
public class SmsMenuPages extends MenuSet {
	@Menu(caption = "短信供应商") //
	@Page(//
			uri = "/middleware/sms/icp", //
			tpl = "/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/sms/sms_icp.html", //
			ctrl = "sysSmsIcpCtrl"//
	) //
	public static String MIDDLEWARE_SMS_SUPPLIER; //
	@Menu(caption = "发送记录") //
	@Page(//
			uri = "/sys/sms/records", //
			tpl = "/$$ceres_sys/console/base/res/cp?id=/console/res/middleware/sms/sms_records.html", //
			ctrl = "sysSmsRecordsCtrl"//
	) //
	public static String MIDDLEWARE_SMS_RECORED;

}
