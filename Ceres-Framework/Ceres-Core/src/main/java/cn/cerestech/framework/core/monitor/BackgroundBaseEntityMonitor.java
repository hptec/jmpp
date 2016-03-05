package cn.cerestech.framework.core.monitor;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import cn.cerestech.framework.persistence.entity.BaseEntity;

public interface BackgroundBaseEntityMonitor<T extends BaseEntity> {

	public Supplier<List<T>> getReloader();

	/**
	 * 对单个的BaseEntity进行处理，并将是否成功处理返回<br/>
	 * true: 处理成功，会将该对象从序列中remove掉 <br/>
	 * false:处理失败，等待下一次循环
	 * 
	 * @return
	 */
	public Function<T, Boolean> getMonitor();

	/**
	 * 本次是否可以执行
	 * 
	 * @param lastTimeInMillis
	 *            上次执行时间
	 * @return
	 */
	public Boolean isRunable();
	
	public Boolean isReloadable();
	
	public Date getLastReloadTime();
	
	public Date getLastRunTime();

	public BackgroundBaseEntityMonitor<T> add(T t);

	public BackgroundBaseEntityMonitor<T> remove(T t);

	public BackgroundBaseEntityMonitor<T> clear();
}
