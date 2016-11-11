package cn.cerestech.framework.support.mp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.dao.MpUserDao;
import cn.cerestech.framework.support.mp.entity.MpUser;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月5日
 */
@Service
public class MpuserService {

	@Autowired
	MpUserDao mpuserDao;
	private static final Object locker = new byte[1];

	@Autowired
	MpConfigService mpConfigService;

	/**
	 * appid 和 appsecret 同时不能为空
	 * 
	 * 返回为空则表示异常
	 * 
	 * @param openid
	 * @param appid
	 * @return
	 */
	public MpUser findOrNew(String openid, String appid) {
		if (Strings.isNullOrEmpty(openid) || Strings.isNullOrEmpty(appid)) {
			return null;
		}
		synchronized (locker) {
			MpUser mpuser = new MpUser();
			mpuser.setOpenId(openid);
			mpuser.setAppId(appid);
			Example<MpUser> example = Example.of(mpuser, ExampleMatcher.matching());
			MpUser mpuserdb = mpuserDao.findOne(example);
			if (mpuserdb == null) {
				mpuser = mpuserDao.save(mpuser);
				return mpuser;
			} else {
				return mpuserdb;
			}
		}
	}

	/**
	 * 更新或者新增
	 * 
	 * @param mpuser:
	 *            属性 id 为空时： appid 和 openid 都不能为空； 属性id 不为空事， 随便啦
	 * @return
	 */
	public MpUser updateOrNew(MpUser mpuser) {
		if (mpuser == null || (mpuser.getId() == null
				&& (Strings.isNullOrEmpty(mpuser.getAppId()) || Strings.isNullOrEmpty(mpuser.getOpenId())))) {
			return null;
		}
		synchronized (locker) {
			MpUser mpuserDb = null;
			if (mpuser.getId() != null) {
				mpuserDb = mpuserDao.findOne(mpuser.getId());
			} else {
				mpuserDb = findOrNew(mpuser.getOpenId(), mpuser.getAppId());
			}
			if (mpuserDb != null) {
				mpuser.setId(mpuserDb.getId());
				mpuser.setAppId(mpuserDb.getAppId());
				mpuser.setOpenId(mpuserDb.getOpenId());
				return mpuserDao.save(mpuser);
			} else {
				return null;
			}
		}
	}

	public MpUser findByOpenid(String openid) {
		return mpuserDao.findUniqueByAppIdAndOpenId(mpConfigService.getAppid(), openid);

	}

}
