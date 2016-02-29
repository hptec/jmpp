package cn.cerestech.middleware.weixin.mp.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.DateUtil;
import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.middleware.weixin.entity.MpUser;
import cn.cerestech.middleware.weixin.entity.Profile;
import cn.cerestech.middleware.weixin.mp.api.MpApi;
import cn.cerestech.middleware.weixin.mp.msg.Event;
import cn.cerestech.middleware.weixin.mp.msg.Message;
import cn.cerestech.middleware.weixin.mp.msg.WeiXinEventListener;
import cn.cerestech.middleware.weixin.mp.msg.WeiXinMessage;
import cn.cerestech.middleware.weixin.mp.msg.WeiXinMessageListener;
import cn.cerestech.middleware.weixin.service.MpUserService;
import cn.cerestech.middleware.weixin.service.WeiXinService;

@Service
public class WeiXinMpService extends BaseService {

	@Autowired
	WeiXinService weixinService;

	@Autowired
	MpUserService mpUserService;

	@Autowired
	WeiXinComponentDispatcher dispather;

	public Message notify(Event event) {
		WeiXinEventListener l = dispather.eventListeners.stream().findFirst().orElse(null);// 只允许一个;

		Message ret = null;

		if (l != null) {
			ret = l.notify(event);
		}
		return ret;
	}

	public WeiXinMessage onMessage(Message msg) {
		WeiXinMessageListener l = dispather.msgListeners.stream().findFirst().orElse(null);// 只允许一个;
		Message ret = null;
		if (l != null) {
			ret = l.onMessage(msg);
		}
		return ret;
	}

	public synchronized void synchronizedMpuser(final String openid) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				log.trace("start a thread to sync mpuser");
				MpUser old = mpUserService.checkForUpdate(openid);
				if (old != null && (old.getUpdate_time() == null
						|| old.getUpdate_time().before(DateUtil.subDate(new Date(), 1)))) {
					Profile p = weixinService.getProfile();
					MpApi api = MpApi.on(p);
					api.USER.get(openid);
				} else {
					log.debug("更新数据时间不足一天，不进行数据信息更新");
				}
			}
		}).start();
	}
}
