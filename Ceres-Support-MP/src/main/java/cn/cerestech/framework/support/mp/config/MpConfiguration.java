package cn.cerestech.framework.support.mp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.cerestech.framework.support.mp.listener.EventListener;
import cn.cerestech.framework.support.mp.listener.MessageListener;
import cn.cerestech.framework.support.mp.msg.client.passive.MpPassiveTextMsg;
import cn.cerestech.framework.support.mp.msg.client.passive.comm.MpPassiveMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpImageMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpLinkMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpLocationMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpShortvideoMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpTextMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpVideoMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.MpVoiceMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.comm.MpMsg;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpClickEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpLocationEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpSubscribeEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpTempMsgStateEvt;
import cn.cerestech.framework.support.mp.msg.mpserver.event.MpViewEvt;

@Configuration
public class MpConfiguration {
	
	public static MpPassiveMsg welcome(MpMsg msg){
		MpPassiveTextMsg text = new MpPassiveTextMsg();
		text.setFrom(msg.getTo());
		text.setTo(msg.getFrom());
		text.setContent("欢迎！！");
		text.setCreateTime(System.currentTimeMillis()/1000);
		return text;
	}
	
	@Bean
	@ConditionalOnMissingClass
	public MessageListener getNormalMessageListener() {
		return new MessageListener() {

			@Override
			public MpPassiveMsg onText(MpTextMsg textMsg) {
				return welcome(textMsg);
			}

			@Override
			public MpPassiveMsg onImage(MpImageMsg imgMsg) {
				return null;
			}

			@Override
			public MpPassiveMsg onVoice(MpVoiceMsg voiceMsg) {
				return null;
			}

			@Override
			public MpPassiveMsg onVideo(MpVideoMsg videoMsg) {
				return null;
			}

			@Override
			public MpPassiveMsg onShortVideo(MpShortvideoMsg shortVideoMsg) {
				return null;
			}

			@Override
			public MpPassiveMsg onLocation(MpLocationMsg locMsg) {
				return null;
			}

			@Override
			public MpPassiveMsg onLink(MpLinkMsg linkMsg) {
				return null;
			}

		};
	}
	
	@Bean
	@ConditionalOnMissingClass
	public EventListener getEventListener(){
		return new EventListener() {
			@Override
			public void onUnSubscribe(MpSubscribeEvt e) {
			}
			@Override
			public MpPassiveMsg onSubscribe(MpSubscribeEvt e) {
				return null;
			}
			
			@Override
			public MpPassiveMsg onScan(MpSubscribeEvt e) {
				return null;
			}
			
			@Override
			public MpPassiveMsg onMenuView(MpViewEvt e) {
				return null;
			}
			
			@Override
			public MpPassiveMsg onMenuClick(MpClickEvt e) {
				return null;
			}
			@Override
			public MpPassiveMsg onLocation(MpLocationEvt e) {
				return null;
			}
			@Override
			public void onTemplateMsgState(MpTempMsgStateEvt e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
