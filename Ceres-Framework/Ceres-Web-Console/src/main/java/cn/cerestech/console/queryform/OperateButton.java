package cn.cerestech.console.queryform;

public class OperateButton {

	private String icon;
	private String title;
	private String action;
	private String href;
	private String comment;
	private String disableIf;

	public String getIcon() {
		return icon;
	}

	public OperateButton setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public OperateButton setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAction() {
		return action;
	}

	public OperateButton setAction(String action) {
		this.action = action;
		return this;
	}

	public String getHref() {
		return href;
	}

	public OperateButton setHref(String href) {
		this.href = href;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public OperateButton setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public String getDisableIf() {
		return disableIf;
	}

	public OperateButton setDisableIf(String disableIf) {
		this.disableIf = disableIf;
		return this;
	}

}
