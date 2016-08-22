package cn.cerestech.middleware.codetable.provider;

import java.util.Collection;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.support.persistence.Owner;

public interface CodeTableProvider {

	/**
	 * 解析在那种情况下应该使用那种owner
	 * 
	 * @return
	 */
	Owner resolveOwner();

	Collection<DescribableEnum> getDefaultCategoryList();

	Collection<DescribableEnum> getDefaultCodeByCategory(DescribableEnum de);
}
