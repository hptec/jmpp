package cn.cerestech.middleware.sms.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

public enum HuoniResponesCode implements DescribableEnum {
	ACC_ERR("-1", "账号不存在"), //
	PWD_ERR("-2", "密码错误"), //
	ACC_LOCKED("-3", "账号被锁定"), //
	BALANCE_ZERO("-4", "余额为0"), //
	CONTENT_ILLEGAL("-5", "短信内容含有关键字"), //
	TASKID_ERR("-6", "标示号taskId有误"), //
	MARK_ERR("-7", "签名错误,未加后缀"), //
	NUM_ERR("-8", "提交的短信号码低于账号最低配置"), //
	DAY_LIMIT("-9", "短信发送量超出当日规定数量"), //
	SUCCESS("0", "成功"), //
	UNKOWN("1", "其他错误"), //
	ACC_INFO_ERR("2", "账号信息有误"), //
	EMPTY("3", "短信内容或号码为空"), //
	BALANCE_NOT_ENOUGH("4", "账号余额不够发送当前短信"), //
	SEND_ERR("5", "发送短信过程中出错"), //
	OTHER("-99", "未知状态码");

	private String key, desc;

	private HuoniResponesCode(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String desc() {
		return desc;
	}

}
