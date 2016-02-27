package cn.cerestech.framework.core.search;

import java.util.List;

import com.google.common.collect.Lists;

public class Paginated<T> {
	public Integer page = 1;// 当前页
	public Integer pageSize = 15;// 每页记录数
	public Long count = 0L;//
	public Criteria criteria;

	public List<T> data = Lists.newArrayList();

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getOffset() {
		return (page - 1) * pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
