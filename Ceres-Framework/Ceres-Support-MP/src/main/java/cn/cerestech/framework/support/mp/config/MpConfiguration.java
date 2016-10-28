package cn.cerestech.framework.support.mp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.cerestech.framework.support.mp.listener.MessageListener;
import cn.cerestech.framework.support.mp.msg.MpMsg;
import cn.cerestech.framework.support.mp.msg.normal.ImageMsg;
import cn.cerestech.framework.support.mp.msg.normal.LinkMsg;
import cn.cerestech.framework.support.mp.msg.normal.LocationMsg;
import cn.cerestech.framework.support.mp.msg.normal.ShortVideoMsg;
import cn.cerestech.framework.support.mp.msg.normal.TextMsg;
import cn.cerestech.framework.support.mp.msg.normal.VideoMsg;
import cn.cerestech.framework.support.mp.msg.normal.VoiceMsg;

@Configuration
public class MpConfiguration {

	@Bean
	@ConditionalOnMissingClass
	public MessageListener getNormalMessageListener() {
		return new MessageListener() {

			@Override
			public MpMsg onText(TextMsg textMsg) {
				return null;
			}

			@Override
			public MpMsg onImage(ImageMsg imgMsg) {
				return null;
			}

			@Override
			public MpMsg onVoice(VoiceMsg voiceMsg) {
				return null;
			}

			@Override
			public MpMsg onVideo(VideoMsg videoMsg) {
				return null;
			}

			@Override
			public MpMsg onShortVideo(ShortVideoMsg shortVideoMsg) {
				return null;
			}

			@Override
			public MpMsg onLocation(LocationMsg locMsg) {
				return null;
			}

			@Override
			public MpMsg onLink(LinkMsg linkMsg) {
				return null;
			}

		};
	}
}
