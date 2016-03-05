package cn.cerestech.middleware.weixin.mp.service;

import java.util.List;

import org.dom4j.IllegalAddException;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.middleware.weixin.mp.msg.WeiXinEventListener;
import cn.cerestech.middleware.weixin.mp.msg.WeiXinMessageListener;

@Service
public class WeiXinComponentDispatcher implements ComponentDispatcher {

	protected final List<WeiXinMessageListener> msgListeners = Lists.newArrayList();
	protected final List<WeiXinEventListener> eventListeners = Lists.newArrayList();

	@Override
	public void recive(String beanName, Object bean) {
		if (bean instanceof WeiXinMessageListener) {
			if (msgListeners.size() >= 1) {
				// 限制为只能加载一个消息侦听器
				throw new IllegalAddException("Only one WeiXinMessageListener allowed!");
			} else {
				msgListeners.add((WeiXinMessageListener) bean);
			}

			if (eventListeners.size() >= 1) {
				// 限制为只能加载一个消息侦听器
				throw new IllegalAddException("Only one WeiXinEventListener allowed!");
			} else {
				eventListeners.add((WeiXinEventListener) bean);
			}
		}
	}

	@Override
	public void onComplete() {

	}

}
