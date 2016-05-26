package cn.cerestech.framework.support.persistence;

import java.util.Calendar;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableTransactionManagement
class PersistenceContext {

	@Bean
	DateTimeProvider dateTimeProvider() {
		return new DateTimeProvider() {

			@Override
			public Calendar getNow() {
				return Calendar.getInstance();
			}
		};
	}
}