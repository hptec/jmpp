package cn.cerestech.middleware.location.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年5月26日
 */
public enum ErrorCodes implements DescribableEnum {
	PHONE_ILLEGAL("PHONE_ILLEGAL", "手机号码错误"),//
	;
	private String key, desc;

	private ErrorCodes(String key, String desc) {
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
