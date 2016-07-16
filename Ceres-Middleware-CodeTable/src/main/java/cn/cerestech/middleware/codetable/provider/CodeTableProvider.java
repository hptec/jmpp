package cn.cerestech.middleware.codetable.provider;

import java.util.Collection;

import cn.cerestech.framework.core.enums.DescribableEnum;

public interface CodeTableProvider {

	Collection<DescribableEnum> getDefaultCategoryList();

	Collection<DescribableEnum> getDefaultCodeByCategory(DescribableEnum de);
}
