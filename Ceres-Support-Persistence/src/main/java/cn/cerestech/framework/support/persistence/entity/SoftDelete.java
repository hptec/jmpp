package cn.cerestech.framework.support.persistence.entity;

import java.util.Date;

/**
 * 标识一个实体类是否为逻辑删除
 * 
 * @author harryhe
 *
 */
public interface SoftDelete {
	public Date getDeleteTime();

	public void setDeleteTime(Date deleteTime);

	default public Boolean isDeleted() {
		return getDeleteTime() != null;
	}
}
