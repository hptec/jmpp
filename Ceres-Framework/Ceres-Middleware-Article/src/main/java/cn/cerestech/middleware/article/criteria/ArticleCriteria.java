package cn.cerestech.middleware.article.criteria;

import java.util.Date;

import cn.cerestech.framework.core.enums.CategoryDescribableEnum;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.search.AbstractCriteria;
import cn.cerestech.middleware.article.entity.Article;

public class ArticleCriteria<T extends Article> extends AbstractCriteria<T> {

	private Long creator_id;
	private String visible;

	private String contentLike;
	private String category;

	private Date dateFrom;
	private Date dateTo;

	public Date getDateTo() {
		return dateTo;
	}

	public ArticleCriteria<T> setDateTo(Date dateTo) {
		this.dateTo = dateTo;
		return this;
	}

	public Long getCreator_id() {
		return creator_id;
	}

	public ArticleCriteria<T> setCreator_id(Long creator_id) {
		this.creator_id = creator_id;
		return this;
	}

	public String getVisible() {
		return visible;
	}

	public ArticleCriteria<T> setVisible(String visible) {
		this.visible = visible;
		return this;
	}

	public ArticleCriteria<T> setVisible(YesNo yesno) {
		return setVisible(yesno.key());
	}

	public String getContentLike() {
		return contentLike;
	}

	public ArticleCriteria<T> setContentLike(String contentLike) {
		this.contentLike = contentLike;
		return this;
	}

	public String getCategory() {
		return category;
	}

	public ArticleCriteria<T> setCategory(String category) {
		this.category = category;
		return this;
	}

	public ArticleCriteria<T> setCategory(CategoryDescribableEnum cate) {
		return setCategory(cate.key());
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public ArticleCriteria<T> setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
		return this;
	}

}
