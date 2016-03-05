package cn.cerestech.middleware.article.entity;

import java.util.Date;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;

/**
 * 文章内容管理
 * 
 * @author harry
 */
@SuppressWarnings("serial")
@Table("$$article")
public class Article extends BaseEntity {
	@Column(title = "创建者id")
	public Long creator_id;
	@Column(type = ColumnDataType.CHAR, defaultValue = "'Y'", title = "是否可见")
	public String visible;

	@Column(length = 200, title = "文章标题")
	public String title;
	@Column(length = 500, title = "摘要")
	public String summary;
	@Column(title = "封面")
	public String cover_img;
	@Column(type = ColumnDataType.TEXT, title = "内容")
	public String content;
	@Column(title = "作者")
	public String author;
	@Column(title = "发布时间")
	public Date publish_time;
	@Column(title = "文章来源")
	public String source;
	@Column(title = "文章分类")
	public String category;

	@Column(title = "浏览量")
	public Long view_count = 0L;

	public Long getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(Long creator_id) {
		this.creator_id = creator_id;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover_img() {
		return cover_img;
	}

	public void setCover_img(String cover_img) {
		this.cover_img = cover_img;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(Date publish_time) {
		this.publish_time = publish_time;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getView_count() {
		return view_count;
	}

	public void setView_count(Long view_count) {
		this.view_count = view_count;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
