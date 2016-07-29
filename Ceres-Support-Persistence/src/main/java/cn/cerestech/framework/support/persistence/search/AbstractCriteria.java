package cn.cerestech.framework.support.persistence.search;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.utils.KV;

public abstract class AbstractCriteria<T> implements Criteria<T> {

	protected Paginated<T> page;

	protected KV wrap = KV.on();

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

	@SuppressWarnings("unchecked")
	protected Predicate[] toArray(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
			Specification<T>... specs) {
		List<Predicate> buffer = Lists.newArrayList();
		for (Specification<T> spec : specs) {
			Predicate predicate = spec.toPredicate(root, query, builder);
			if (predicate != null) {
				buffer.add(predicate);
			}
		}
		Predicate[] ret = new Predicate[buffer.size()];
		buffer.toArray(ret);
		return ret;
	}
}
