package cn.cerestech.framework.core.model;

/**
 * Data Table 用来表示列的
 * 
 * @author harryhe
 *
 */
public class DataColumn {
	private String property;// ognl 表达式，用于取值
	private String title;// 显示标题

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public static DataColumn from(String property, String title) {
		DataColumn dc = new DataColumn();
		dc.setProperty(property);
		dc.setTitle(title);
		return dc;
	}

}
