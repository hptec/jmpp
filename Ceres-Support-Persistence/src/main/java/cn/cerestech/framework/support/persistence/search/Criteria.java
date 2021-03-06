package cn.cerestech.framework.support.persistence.search;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public interface Criteria<T> {

	/**
	 * 返回当前查询的排序条件
	 * 
	 * @return
	 */
	default Sort sort(){
		return null;
	};

	/**
	 * 返回当前查询的组合查询条件
	 * 
	 * @return
	 */
	Specification<T> specification();

	Paginated<T> getPage();

	void setPage(Paginated<T> page);
}
