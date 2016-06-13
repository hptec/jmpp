package cn.cerestech.framework.support.login.entity;

public class LoginField {

	private String type;
	private String name;
	private String title;

	public LoginField() {
		super();
	}

	public LoginField(String type, String name, String title) {
		super();
		this.type = type;
		this.name = name;
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
