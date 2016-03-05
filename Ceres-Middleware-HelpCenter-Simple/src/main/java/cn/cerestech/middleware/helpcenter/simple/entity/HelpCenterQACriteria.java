package cn.cerestech.middleware.helpcenter.simple.entity;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.search.AbstractCriteria;

public class HelpCenterQACriteria<T extends HelpCenterQA> extends AbstractCriteria<T> {
	private Long category_id;
	
	private String keyword;
	
	private YesNo visible;

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public YesNo getVisible() {
		return visible;
	}

	public void setVisible(YesNo visible) {
		this.visible = visible;
	}
	
	
}
