package cn.cerestech.framework.core.monitor;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Maps;

import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.framework.persistence.service.MysqlService;

public abstract class AbstractMonitor<T extends BaseEntity> extends Thread implements BackgroundBaseEntityMonitor<T> {
	public static final Long RELOAD_INTERVAL = 30 * 60 * 1000L;

	@Autowired
	protected MysqlService mysqlService;

	protected Logger log = LogManager.getLogger();

	@SuppressWarnings("rawtypes")
	protected static ConcurrentMap<String, BackgroundBaseEntityMonitor> monitorPool = Maps.newConcurrentMap();

	protected ConcurrentMap<Long, T> data = Maps.newConcurrentMap();

	protected Date lastReloadTime;
	protected Date lastRunTime;
	protected Boolean isPaused = Boolean.FALSE;

	protected String monitorName = this.getClass().getCanonicalName();;

	@PostConstruct
	public void startup() {
		// 注册Monitor
		if (monitorPool.get(getMonitorName()) != null) {
			throw new IllegalArgumentException("Monitor conflict: " + getMonitorName());
		}
		monitorPool.put(getMonitorName(), this);
		log.trace("Monitor: Thread [" + getName() + "] register [" + getMonitorName() + "]");
		this.start();
	}

	public String getMonitorName() {
		return monitorName;
	}

	@Override
	public void run() {
		while (true) {
			if (!isPaused) {
				// 检测是否需要重载
				if (isReloadable()) {
					List<T> retList = getReloader().get();
					clear();
					if (retList != null) {
						for (T t : retList) {
							add(t);
						}

					}
					setLastReloadTime(new Date());
					log.trace("Monitor: [" + getMonitorName() + "] Reloaded " + data.size() + " record(s).");
				}

				// 检测是否需要执行监控程序
				if (isRunable()) {
					Function<T, Boolean> monitor = getMonitor();
					Set<Long> ids = data.keySet();
					for (Long id : ids) {
						T t = data.get(id);
						if (monitor.apply(t)) {
							remove(t);
						}
					}

					setLastRunTime(new Date());
				}
			}

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				log.catching(e);
			}
		}
	}

	public BackgroundBaseEntityMonitor<T> add(T t) {
		data.put(t.getId(), t);
		return this;
	}

	public BackgroundBaseEntityMonitor<T> remove(T t) {
		data.remove(t.getId());
		return this;
	}

	public BackgroundBaseEntityMonitor<T> clear() {
		data.clear();
		return this;
	}

	public Boolean isReloadable() {
		if (getLastReloadTime() == null) {
			return Boolean.TRUE;
		} else {
			return System.currentTimeMillis() >= getLastReloadTime().getTime() + RELOAD_INTERVAL;
		}
	}

	public Date getLastReloadTime() {
		return lastReloadTime;
	}

	public void setLastReloadTime(Date lastReloadTime) {
		this.lastReloadTime = lastReloadTime;
	}

	public void setLastRunTime(Date lastRunTime) {
		this.lastRunTime = lastRunTime;
	}

	public AbstractMonitor<T> pauseMonitor() {
		this.isPaused = Boolean.TRUE;
		return this;
	}

	public AbstractMonitor<T> resumeMonitor() {
		this.isPaused = Boolean.FALSE;
		return this;
	}

	public Boolean isPaused() {
		return isPaused;
	}

	public Date getLastRunTime() {
		return lastRunTime;
	}
	
	protected ConcurrentMap<Long, T> getData(){
		return this.data;
	}
}
