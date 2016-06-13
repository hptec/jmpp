package cn.cerestech.framework.support.login.dao;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LoginDao<T> extends CrudRepository<T, Long> {
	/**
	 * 通过登录名进行查找
	 * 
	 * @param loginId
	 * @return
	 */
	T findUniqueByLoginIdIgnoreCase(String loginId);

	T findUniqueByIdAndLoginRememberTokenAndLoginRememberExpiredGreaterThan(Long id, String remmeberToken,
			Date rememberExpired);
}
