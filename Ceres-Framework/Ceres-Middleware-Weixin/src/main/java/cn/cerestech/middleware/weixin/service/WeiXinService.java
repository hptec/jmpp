package cn.cerestech.middleware.weixin.service;

import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.middleware.weixin.entity.Profile;

@Service
public class WeiXinService extends BaseService {

	public Profile getProfile() {
		Profile p = mysqlService.queryUnique(Profile.class, "is_default = 'Y'");
		if (p == null) {
			throw new IllegalArgumentException("default AppID & AppSecret is not configured.");
		} else {
			return p;
		}
	}

	public Profile getProfile(String name) {
		return mysqlService.queryUnique(Profile.class, "profile_name='" + name + "'");
	}
}
