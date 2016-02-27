package cn.cerestech.middleware.carouseladvertising.entity;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;

/**
 * 广告轮播
 * 
 * @author harry
 */
@SuppressWarnings("serial")
@Table("$$carousel_advertising")
public class CarouselAdvertising extends BaseEntity {

	@Column(title = "类别")
	public String namespace;

	@Column(title = "文件ID", length = 255)
	public String file_id;

	@Column(title = "跳转链接", length = 5000)
	public String href;

	@Column(title = "跳转链接")
	public String newwin = YesNo.NO.key();

	public String getNewwin() {
		return newwin;
	}

	public void setNewwin(String newwin) {
		this.newwin = newwin;
	}

	@Column(title = "排序")
	public Integer sort;

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

}
