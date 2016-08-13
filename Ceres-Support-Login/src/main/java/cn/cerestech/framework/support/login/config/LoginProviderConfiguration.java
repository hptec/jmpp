package cn.cerestech.framework.support.login.config;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.entity.LoginField;
import cn.cerestech.framework.support.login.enums.ErrorCodes;
import cn.cerestech.framework.support.login.provider.LoginProvider;

@Configuration
public class LoginProviderConfiguration {

	@Bean
	@ConditionalOnMissingBean(LoginProvider.class)
	public LoginProvider getLoginProvider() {
		return new LoginProvider() {

			@Override
			public Result validate() {
				return Result.error(ErrorCodes.LOGIN_FAILED);
			}

			@Override
			public List<LoginField> getLoginFields() {
				return null;
			}
		};
	}

}
