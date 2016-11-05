package cn.cerestech.framework.support.mp.service;

import org.springframework.stereotype.Service;

import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.comm.MpServiceMsg;

/**
 * 客服消息 service
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月5日
 */
@Service
public class KfMsgSenderService extends MpConfigService {
	/**
	 * 发送客服消息
	 * @param msg
	 * @return
	 */
	public Status<String> send(MpServiceMsg msg){
		return MemoryStrategy.of(this.getAppid(), this.getAppsecret()).WAITERAPI().sendMsg(msg);
	}
	
}
