package cn.cerestech.console.queryform;

public class QueryTerm {

	private String type;

	private String key;

	private String title;

	private String value;

	private String enumClass;

	private QueryTermRemoteList remoteList;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEnumClass() {
		return enumClass;
	}

	public void setEnumClass(String enumClass) {
		this.enumClass = enumClass;
	}

	public QueryTermRemoteList getRemoteList() {
		return remoteList;
	}

	public void setRemoteList(QueryTermRemoteList remoteList) {
		this.remoteList = remoteList;
	}

}
