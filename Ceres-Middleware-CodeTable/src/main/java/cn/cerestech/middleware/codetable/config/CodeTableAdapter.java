package cn.cerestech.middleware.codetable.config;

import java.util.Collection;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.beust.jcommander.internal.Lists;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.middleware.codetable.provider.CodeTableProvider;

@Configuration
public class CodeTableAdapter {

	@Bean
	@ConditionalOnMissingBean(CodeTableProvider.class)
	public CodeTableProvider getCodeTableProvider() {
		return new CodeTableProvider() {

			@Override
			public Owner resolveOwner() {
				return null;
			}

			@Override
			public Collection<DescribableEnum> getDefaultCodeByCategory(DescribableEnum de) {
				return Lists.newArrayList();
			}

			@Override
			public Collection<DescribableEnum> getDefaultCategoryList() {
				return Lists.newArrayList();
			}
		};
	}
}
