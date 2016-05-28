package cn.cerestech.framework.core.search;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Criteria<T> {
	public Criteria<T> page(Integer page, Integer pageSize);

	public Stream<T> stream();

	public Criteria<T> setPage(Integer page);

	public Criteria<T> setPageSize(Integer pageSize);

	public Criteria<T> setCount(Integer count);

	public Integer getCount();

	public Integer getPageSize();

	public Integer getPage();

	public List<T> getData();

	public void addData(List<T> list);

}
