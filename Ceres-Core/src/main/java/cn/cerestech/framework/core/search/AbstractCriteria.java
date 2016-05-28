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
	protected Integer count = 0;
	protected List<T> data = Lists.newArrayList();

	public Criteria<T> page(Integer page, Integer pageSize) {
		if (page == null || pageSize == null) {
			throw new IllegalArgumentException("page or page size is null");
		}

		this.page = page;
		this.pageSize = pageSize;
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

	public Stream<T> stream() {
		return data.stream();
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


	public List<T> getData() {
		return data;
	}

	public void addData(List<T> list) {
		data.clear();
		data.addAll(list);
	}

}
