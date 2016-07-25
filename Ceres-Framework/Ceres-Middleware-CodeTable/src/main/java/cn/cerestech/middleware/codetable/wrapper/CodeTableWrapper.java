package cn.cerestech.middleware.codetable.wrapper;

import java.util.List;

import cn.cerestech.framework.core.json.JsonWrapper;
import cn.cerestech.middleware.codetable.entity.Category;

public class CodeTableWrapper extends JsonWrapper<Category> {

	public CodeTableWrapper(Category data) {
		super(data);
	}

	public CodeTableWrapper(List<Category> data) {
		super(data);
	}

	@Override
	public Category wrap(Category t) {
		if (t != null && t.getCodes() != null && !t.getCodes().isEmpty()) {
			t.getCodes().forEach(c -> {
				c.setCategory(null);
			});
		}
		return t;
	}

}
