package cn.cerestech.middleware.balance.enums;

import cn.cerestech.framework.core.enums.DescribableEnum;

/**
 * 延时类交易状态
 * 
 * @author harryhe
 *
 */
public enum BufferedTransState implements DescribableEnum {
	NEW("NEW", "正常"), //
	CANCEL("CANCEL", "撤销"), //
	FINISH("FINISH", "完成"),//
	;
	private String key, desc;

	private BufferedTransState(String key, String desc) {
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