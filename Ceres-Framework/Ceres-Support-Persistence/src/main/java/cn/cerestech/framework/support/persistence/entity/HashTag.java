package cn.cerestech.framework.support.persistence.entity;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.utils.Random;

/**
 * 标识一记录是支持排序显示的
 * 
 * @author harryhe
 *
 */
public interface HashTag {

	void setHashTag(String hashTag);

	String getHashTag();

	default void generateHashTag() {
		if (Strings.isNullOrEmpty(getHashTag())) {
			// 不存在则创建
			setHashTag(Random.uuid());
		}
	}
}
