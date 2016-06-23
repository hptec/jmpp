package cn.cerestech.framework.support.persistence.search;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public abstract class AbstractCriteria<T> implements Criteria<T> {

	protected Paginated<T> page;

	public void doSearch(JpaSpecificationExecutor<T> executer) {
		if (page == null) {
			page = new Paginated<T>();
		}

		PageRequest pageRequest = null;
		Sort sort = sort();
		int pn = page.getPageNumber() == null ? Paginated.DEFAULT_PAGE_NUMBER : page.getPageNumber();
		int ps = page.getPageSize() == null ? Paginated.DEFAULT_PAGE_SIZE : page.getPageSize();
		if (sort != null) {
			pageRequest = new PageRequest(pn, ps, sort);
		} else {
			pageRequest = new PageRequest(pn, ps);
		}

		Page<T> ret = executer.findAll(specification(), pageRequest);

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
