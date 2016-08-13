package cn.cerestech.framework.support.starter.provider;

import java.util.function.Supplier;

public interface MainPageProvider extends Supplier<String> {

	default Boolean isHtml5Mode() {
		return Boolean.FALSE;
	}

}
