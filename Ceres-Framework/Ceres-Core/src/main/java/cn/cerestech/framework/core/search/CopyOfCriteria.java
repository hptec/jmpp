package cn.cerestech.framework.core.search;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CopyOfCriteria<T> {
	public CopyOfCriteria<T> page(Integer page, Integer pageSize);

	public CopyOfCriteria<T> orderBy(String column, Sort sort);

	public CopyOfCriteria<T> orderBy(String column);

	public CopyOfCriteria<T> setMaxRecords(Integer maxRecords);

	public String getOrderBy();

	public CopyOfCriteria<T> allRecords();

	public Stream<T> stream();

	public CopyOfCriteria<T> page(Integer page);

	public CopyOfCriteria<T> setPage(Integer page);

	public CopyOfCriteria<T> setPageSize(Integer pageSize);

	public CopyOfCriteria<T> allPage();

	public CopyOfCriteria<T> setCount(Integer count);

	public Integer getCount();

	public Integer getPageSize();

	public Integer getPage();

	public Integer getOffset();

	public List<T> getData();

	public void addData(List<T> list);

}
