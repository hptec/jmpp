package cn.cerestech.middleware.weixin.entity;

import java.util.Date;

public class Cache<T> {
	private Date time;
	public T cache;
	
	public long storeTime(){
		return new Date().getTime() - getTime().getTime();
	}
	
	public Cache(T t){
		this.cache = t;
		this.time = new Date();
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public T getCache() {
		return cache;
	}
	
	public void setCache(T cache) {
		this.cache = cache;
	}
}
