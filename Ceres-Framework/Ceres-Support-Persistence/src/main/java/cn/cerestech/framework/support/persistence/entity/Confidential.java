package cn.cerestech.framework.support.persistence.entity;

/**
 * 标识一条信息和数据是敏感的
 * 
 * @author harryhe
 *
 */
public interface Confidential<T> {

	/**
	 * 隐藏敏感内容，输出安全内容
	 * 
	 * @return
	 */
	public T safty();
}
