package cn.cerestech.middleware.weixin.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.middleware.weixin.entity.MpUser;
import cn.cerestech.middleware.weixin.mapper.MpUserMapper;

@Service
public class MpUserService extends BaseService {
	@Autowired
	MpUserMapper mpuserMapper;

	/**
	 * 查询数据库中是否存在openid，存在则返回，否则新建后返回
	 * 
	 * @param openid
	 * @return
	 */
	public synchronized MpUser checkForUpdate(String openid) {
		if (StringUtils.isBlank(openid)) {
			return null;
		}
		MpUser old = mpuserMapper.queryMpUserByOpenId(openid);
		if (old == null) {
			old = new MpUser();
			old.setOpenid(openid);
			mpuserMapper.insert(old);
		}
		return old;
	}

	/**
	 * 通过open id 进行查询，查询后更新数据库
	 * 
	 * @param newMpuser:
	 *            可以是从网络或者其他途径获取的mp信息，通过openid 进行对比更新， 该对象可以是没有 id的新对象
	 * @return
	 */
	public synchronized MpUser checkForUpdate(MpUser newMpuser) {
		MpUser old = checkForUpdate(newMpuser.getOpenid());
		if (old != null) {
			newMpuser.setId(old.getId());
			newMpuser.setUpdate_time(new Date());
			mpuserMapper.update(newMpuser);
			old = newMpuser;
		}
		return old;
	}

}
