package cn.cerestech.framework.core.json;

import java.util.Collection;
import java.util.List;

import com.beust.jcommander.internal.Lists;

/**
 * 作Json输出的时候用来打包剔除一些非必要信息
 * 
 * @author harryhe
 *
 */
public abstract class JsonWrapper<T> {

	private T data;
	private Collection<T> dataCol;
	private Boolean isList = Boolean.FALSE;

	public abstract T wrap(T t);

	public JsonWrapper(List<T> data) {
		put(data);
	}

	public JsonWrapper(T data) {
		put(data);
	}

	public JsonWrapper() {
	}

	public void put(T t) {
		data = t;
		isList = Boolean.FALSE;
	}

	public void put(Collection<T> col) {
		dataCol = col;
		isList = Boolean.TRUE;
	}

	public Boolean isList() {
		return isList;
	}

	/**
	 * 执行打包压缩
	 * 
	 * @return
	 */
	public Object doWrap() {
		if (isList) {
			List<T> result = Lists.newArrayList();
			if (dataCol != null && !dataCol.isEmpty()) {
				dataCol.forEach(t -> {
					result.add(wrap(t));
				});
			}
			return result;
		} else {
			return wrap(data);
		}
	}

}
