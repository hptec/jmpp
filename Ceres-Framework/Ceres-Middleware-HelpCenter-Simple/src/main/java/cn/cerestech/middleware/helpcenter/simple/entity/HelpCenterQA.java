package cn.cerestech.middleware.helpcenter.simple.entity;

import cn.cerestech.framework.persistence.annotation.Column;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.enums.ColumnDataType;

@SuppressWarnings("serial")
@Table(value = "$$helpcenter_question", comment = "帮助中心")
public class HelpCenterQA extends BaseEntity {

	@Column(title = "问题", length = 200)
	public String question;

	@Column(title = "解答", type = ColumnDataType.TEXT)
	public String answer;

	@Column(title = "父级")
	public Long category_id;

	@Column(title = "排序序号")
	public Integer sort = 99999;

	@Column(title = "是否可见")
	public String visible;


	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}
}
