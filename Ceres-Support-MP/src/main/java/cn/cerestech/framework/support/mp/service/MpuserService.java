package cn.cerestech.framework.support.mp.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.dao.MpUserDao;
import cn.cerestech.framework.support.mp.entity.MpUser;
import cn.cerestech.framework.support.mp.entity.base.MpUserGov;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;

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
	
	private static final List<MpUser> pool = Lists.newArrayList();

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
				mpuserDb.copyNotNull(mpuser,false);
				mpuserDb = mpuserDao.save(mpuserDb);
				mpuser.copyNotNull(mpuserDb,true);
				return mpuserDb;
			} else {
				return null;
			}
		}
	}

	public MpUser findByOpenid(String openid) {
		return mpuserDao.findUniqueByAppIdAndOpenId(mpConfigService.getAppid(), openid);

	}
	
	public MpUser fetchMpUserInfo(String openid){
		try{
			Status<MpUserGov> s = MemoryStrategy.of(mpConfigService.getAppid(), mpConfigService.getAppsecret()).USERAPI().get(openid);
			if(s.getCode() == 0 || s.isSuccess()){//获取数据成功
				MpUser mpuser = s.getObject().toMpUser(mpConfigService.getAppid());
				return this.updateOrNew(mpuser);
			}else{
				//System.out.println("||||同步用户数据失败||||||||||:"+JsonUtils.toJson(s));
			}
		}catch(Exception e){
			//e.printStackTrace();
		}
		return null;
	}
	
	public MpUser fetchByMpuserId(Long mpuserId){
		if(mpuserId != null){
			MpUser mpuser = mpuserDao.findOne(mpuserId);
			if(mpuser != null){
				return this.fetchMpUserInfo(mpuser.getOpenId());
			}
		}
		return null;
	}

}
