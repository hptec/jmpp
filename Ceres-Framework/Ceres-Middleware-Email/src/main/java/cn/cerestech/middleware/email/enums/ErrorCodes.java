package cn.cerestech.middleware.email.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;
/**
 * 邮件发送错误码
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年11月22日下午9:12:13
 */
public enum ErrorCodes implements DescribableEnum {
	EMAIL_NFD("EMAIL_NFD","邮件内容未找到"),
	EMAIL_SEND_ERR("EMAIL_SEND_ERR","邮件发送失败"),
	EMAIL_ACC_ERR("EMAIL_ACC_ERR","接受账号错误"),
	EMAIL_SERVICE_DISABLE("EMAIL_SERVICE_DISABLE","邮件发送服务不可用"),
	;
	private String key, desc;
	
	private ErrorCodes(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	@Override
	public String desc() {
		return this.desc;
	}
	
	@Override
	public String key() {
		return this.key;
	}
	
	public static ErrorCodes keyOf(String key){
		for (ErrorCodes item : ErrorCodes.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
}
