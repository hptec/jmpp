package cn.cerestech.framework.platform.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class Seo implements Serializable {
	@Column(name = "[desc]")
	private String desc;

	private String keyword;

	public String getDesc() {
		return desc;
	}

	public Seo setDesc(String desc) {
		this.desc = desc;
		return this;
	}

	public String getKeyword() {
		return keyword;
	}

	public Seo setKeyword(String keyword) {
		this.keyword = keyword;
		return this;
	}

}
