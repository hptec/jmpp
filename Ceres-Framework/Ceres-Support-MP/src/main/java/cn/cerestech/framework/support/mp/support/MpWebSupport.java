package cn.cerestech.framework.support.mp.support;

import org.springframework.beans.factory.annotation.Autowired;

import cn.cerestech.framework.support.mp.dao.MpUserDao;
import cn.cerestech.framework.support.mp.entity.MpUser;
import cn.cerestech.framework.support.mp.operator.MpOperator;
import cn.cerestech.framework.support.starter.web.WebSupport;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月6日
 */
public class MpWebSupport extends WebSupport implements MpOperator{
	@Autowired
	MpUserDao mpUserDao;
	protected MpUser getCurMpUser() {
		return mpUserDao.findOne(getMpUserId());
	}
}
