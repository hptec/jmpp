package cn.cerestech.middleware.codetable.provider;

import java.util.Collection;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.starter.operator.PlatformOperator;

public interface CodeTableProvider extends PlatformOperator {

	/**
	 * 解析在那种情况下应该使用那种owner
	 * 
	 * @return
	 */
	Owner resolveOwner();

	Collection<DescribableEnum> getDefaultCategoryList();

	Collection<DescribableEnum> getDefaultCodeByCategory(DescribableEnum de);
}
