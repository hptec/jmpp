package cn.cerestech.framework.support.persistence.search;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.cerestech.framework.core.json.Jsonable;

public abstract class AbstractCriteria<T> implements Criteria<T>, Jsonable {

	protected Paginated<T> page;

	public void doSearch(JpaSpecificationExecutor<T> executer) {
		if (page == null) {
			page = new Paginated<T>();
		}

		PageRequest pageRequest = null;
		Sort sort = getSort();
		if (sort != null) {
			pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize(), sort);
		} else {
			pageRequest = new PageRequest(page.getPageNumber(), page.getPageSize());
		}

		Page<T> ret = executer.findAll(getSpecification(), pageRequest);

		page.setData(ret.getContent());
		page.setNumberOfElements(ret.getNumberOfElements());
		page.setPageNumber(ret.getNumber());
		page.setPageSize(ret.getSize());
		page.setTotalElements(ret.getTotalElements());
		page.setTotalPages(ret.getTotalPages());

	}

	@Override
	public Paginated<T> getPage() {
		return page;
	}

	public void setPage(Paginated<T> page) {
		this.page = page;
	}

	protected Predicate[] toArray(Collection<Predicate> col) {
		Predicate[] ret = new Predicate[col.size()];
		col.toArray(ret);
		return ret;
	}

	/**
	 * 过滤结果集
	 * 
	 * @param list
	 * @return
	 */
	protected List<T> filter(List<T> list) {
		return list;
	}

}
