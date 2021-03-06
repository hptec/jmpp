package cn.cerestech.framework.support.starter.entity;

public class RemoteList {

	private Long id;
	private String desc;

	public RemoteList(Long id, String desc) {
		super();
		this.id = id;
		this.desc = desc;
	}

	public RemoteList() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
