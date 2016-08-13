package cn.cerestech.framework.support.starter.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.starter.annotation.PlatformRequired;
import cn.cerestech.framework.support.starter.dao.PlatformDao;
import cn.cerestech.framework.support.starter.entity.Platform;
import cn.cerestech.framework.support.starter.enums.ErrorCodes;
import cn.cerestech.framework.support.starter.operator.PlatformOperator;

@RestController
@RequestMapping("api/platform")
public class PlatformWebApi extends WebSupport implements PlatformOperator {

	@Autowired
	PlatformDao platformDao;

	@RequestMapping("query")
	@PlatformRequired
	public void query() {
		Platform pf = platformDao.findOne(getPlatformId());
		if (pf == null) {
			zipOut(Result.error(ErrorCodes.PLATFORM_AUTH_INCORRECT));
		} else {
			pf.setId(null);
			zipOut(Result.success(pf));
		}
	}

}
