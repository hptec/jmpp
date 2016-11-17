package cn.cerestech.framework.support.mp.support;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.date.Dates;
import cn.cerestech.framework.support.mp.dao.MpUserDao;
import cn.cerestech.framework.support.mp.entity.MpUser;
import cn.cerestech.framework.support.mp.operator.MpOperator;
import cn.cerestech.framework.support.mp.schema.SyncSchema;
import cn.cerestech.framework.support.starter.web.WebSupport;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月6日
 */
public class MpWebSupport extends WebSupport implements MpOperator {
	@Autowired
	MpUserDao mpUserDao;

	protected MpUser getCurMpUser() {
		MpUser mpuser = mpUserDao.findOne(getMpUserId());
		if(Strings.isNullOrEmpty(mpuser.getNicknameStr()) 
				|| (mpuser.getUpdateTime() != null && Dates.from(mpuser.getUpdateTime()).addDate(30).toDate().before(new Date()))){
			SyncSchema.put(mpuser.getId());
		}
		return mpuser;
	}
}
