package cn.cerestech.framework.core.json;

public interface Jsonable {

	public default Jsons getJson() {
		return Jsons.from(this);
	}

}
