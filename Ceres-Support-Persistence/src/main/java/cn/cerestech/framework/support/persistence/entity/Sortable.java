package cn.cerestech.framework.support.persistence.entity;

/**
 * 标识一记录是支持排序显示的
 * 
 * @author harryhe
 *
 */
public interface Sortable {

	void setSortIndex(Integer sortIndex);

	Integer getSortIndex();
}
