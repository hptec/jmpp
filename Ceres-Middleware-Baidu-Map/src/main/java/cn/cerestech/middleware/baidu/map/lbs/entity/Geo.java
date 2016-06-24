package cn.cerestech.middleware.baidu.map.lbs.entity;

import java.util.Date;

/**
 * 位置数据表
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月16日
 */

public class Geo {
	private Long id;//	id	uint32	即geotable_id，为唯一标识
	private Long user_id;//	用户id	uint64	
	private Long geotype;//	数据类型	uint32	1（点）、3（面）
	private Date create_time;//	创建的时间	datetime	
	private Date modify_time;//	最后一次修改的时间	datetime
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getGeotype() {
		return geotype;
	}
	public void setGeotype(Long geotype) {
		this.geotype = geotype;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
}
