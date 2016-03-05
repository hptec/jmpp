package cn.cerestech.framework.core.search;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.json.Jsonable;

public abstract class AbstractCriteria<T> implements Criteria<T>, Jsonable {
	protected Integer page;
	protected Integer pageSize = 15;
	protected Class<T> clazz;
	protected Integer count = 0;
	protected Map<String, Object> criteriaMap = Maps.newHashMap();
	protected List<T> data = Lists.newArrayList();
	protected String orderBy = "";

	public Criteria<T> page(Integer page, Integer pageSize) {
		if (page == null || pageSize == null) {
			throw new IllegalArgumentException("page or page size is null");
		}

		this.page = page;
		this.pageSize = pageSize;
		return this;
	}

	public Criteria<T> orderBy(String column, Sort sort) {
		StringBuffer buffer = new StringBuffer(orderBy);

		if (buffer.length() > 0) {
			buffer.append(", ");
		}
		buffer.append(column);
		if (Sort.DESC.equals(sort)) {
			buffer.append(" DESC");
		}
		orderBy = buffer.toString();
		return this;
	}

	public Criteria<T> orderBy(String column) {
		return orderBy(column, Sort.ASC);
	}

	public Criteria<T> allRecords() {
		this.page = null;
		this.pageSize = 15;
		return this;
	}

	public Stream<T> stream() {
		return data.stream();
	}

	public Criteria<T> setMaxRecords(Integer maxRecords) {
		page = 0;
		pageSize = maxRecords;
		return this;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Criteria<T> page(Integer page) {
		this.page = page;
		return this;
	}

	public Criteria<T> setPage(Integer page) {
		this.page = page;
		return this;
	}

	public Criteria<T> setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
		return this;
	}

	public Criteria<T> allPage() {
		this.page = null;
		return this;
	}

	public Criteria<T> setCount(Integer count) {
		this.count = count;
		return this;
	}

	public Integer getCount() {
		return count;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public Integer getOffset() {
		if (page == null || page == 0) {
			return 0;
		} else {
			return (page - 1) * pageSize;
		}
	}

	public List<T> getData() {
		return data;
	}

	public void addData(List<T> list) {
		data.clear();
		data.addAll(list);
	}

}
