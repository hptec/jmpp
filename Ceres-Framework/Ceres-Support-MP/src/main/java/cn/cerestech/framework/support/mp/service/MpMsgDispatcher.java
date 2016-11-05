package cn.cerestech.framework.support.mp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.support.mp.listener.EventListener;
import cn.cerestech.framework.support.mp.listener.MessageListener;
import cn.cerestech.framework.support.mp.msg.client.passive.comm.MpPassiveMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpImageMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpLinkMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpLocationMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpShortvideoMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpTextMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpVideoMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpVoiceMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpEventMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpClickEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpLocationEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpSubscribeEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpTempMsgStateEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpViewEvt;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月5日
 */
@Service
public final class MpMsgDispatcher {
	@Autowired
	EventListener eListener;
	@Autowired
	MessageListener msgListener;
	
	public MpPassiveMsg dispatch(MpMsg msg){
		if (msg != null) {
			switch (msg.getMsgType()) {
				case EVENT:
					MpEventMsg e = (MpEventMsg)msg;
					switch(e.getEvent()){
						case CLICK:
							return eListener.onMenuClick((MpClickEvt)msg);
						case LOCATION:
							return eListener.onLocation((MpLocationEvt)msg);
						case SCAN:
							return eListener.onScan((MpSubscribeEvt)msg);
						case SUBSCRIBE:
							return eListener.onSubscribe((MpSubscribeEvt)msg);
						case UNSUBSCRIBE:
							eListener.onUnSubscribe((MpSubscribeEvt)msg);
							return null;
						case VIEW:
							return eListener.onMenuView((MpViewEvt)msg);
						case TEMPLATESENDJOBFINISH:
							eListener.onTemplateMsgState((MpTempMsgStateEvt)msg);
							return null;
					}
					break;
				case IMAGE:
					return msgListener.onImage((MpImageMsg)msg);
				case LINK:
					return msgListener.onLink((MpLinkMsg)msg);
				case LOCATION:
					return msgListener.onLocation((MpLocationMsg)msg);
				case SHORTVIDEO:
					return msgListener.onShortVideo((MpShortvideoMsg)msg);
				case TEXT:
					return msgListener.onText((MpTextMsg)msg);
				case VIDEO:
					return msgListener.onVideo((MpVideoMsg)msg);
				case VOICE:
					return msgListener.onVoice((MpVoiceMsg)msg);
			}
		}
		return null;
	}
}
