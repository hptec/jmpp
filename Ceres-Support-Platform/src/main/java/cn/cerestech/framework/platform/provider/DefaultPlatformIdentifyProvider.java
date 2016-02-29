package cn.cerestech.framework.platform.provider;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.platform.dao.PlatformDao;
import cn.cerestech.framework.platform.entity.Platform;

@Service
public class DefaultPlatformIdentifyProvider implements PlatformIdentifyProvider {

	@Autowired
	PlatformDao platformDao;

	@Override
	public Platform get() {
		Platform pf = platformDao.findAll(new PageRequest(0, 1)).getContent().stream().findFirst().orElse(null);
		if (pf == null) {
			// 没有就创建默认的
			pf = new Platform();
			pf.setDeveloperName("成都塞瑞斯科技责任有限公司");
			pf.setDeveloperWebsite("http://www.cerestech.cn");
			pf.setDeveloperCopyright("Copyright@2014-2016");
			pf.setFrontendFullName("塞瑞斯框架测试系统");
			pf.setFrontendCopyright("Copyright@2014-2016");
			pf.setFrontendShortName("框架测试系统");
			platformDao.save(pf);
		}
		return pf;
	}

}
