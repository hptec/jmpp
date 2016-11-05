package cn.cerestech.framework.support.mp.entity.base;
/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月22日
 */
public class MpGroup {
	private Long id;//微信服务器的分组id
	private String name;//分组名称
	private long count;//分组中用户数量
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}
