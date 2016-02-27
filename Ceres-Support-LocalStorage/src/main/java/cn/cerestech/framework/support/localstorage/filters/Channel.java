package cn.cerestech.framework.support.localstorage.filters;

public class Channel {

	private String name;
	private String parameter;

	public Channel(String name, String parameter) {
		super();
		this.name = name;
		this.parameter = parameter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

}
