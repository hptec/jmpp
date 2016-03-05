package cn.cerestech.framework.core.search;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Criteria<T> {
	public Criteria<T> page(Integer page, Integer pageSize);

	public Criteria<T> orderBy(String column, Sort sort);

	public Criteria<T> orderBy(String column);

	public Criteria<T> setMaxRecords(Integer maxRecords);

	public String getOrderBy();

	public Criteria<T> allRecords();

	public Stream<T> stream();

	public Criteria<T> page(Integer page);

	public Criteria<T> setPage(Integer page);

	public Criteria<T> setPageSize(Integer pageSize);

	public Criteria<T> allPage();

	public Criteria<T> setCount(Integer count);

	public Integer getCount();

	public Integer getPageSize();

	public Integer getPage();

	public Integer getOffset();

	public List<T> getData();

	public void addData(List<T> list);

}
