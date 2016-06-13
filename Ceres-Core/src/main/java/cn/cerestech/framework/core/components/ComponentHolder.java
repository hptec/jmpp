package cn.cerestech.framework.core.components;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Component
public class ComponentHolder implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {
	protected static final Map<String, Object> componentMap = Maps.newHashMap();
	protected static final List<ComponentListener> dispatchers = Lists.newArrayList();

	private Logger log = LogManager.getLogger();

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		componentMap.put(beanName, bean);

		if (bean instanceof ComponentListener) {
			dispatchers.add((ComponentListener) bean);
		}
//		log.trace("Bean [" + beanName + "]:" + bean);
		return bean;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			dispatchComponent();
		}
	}

	/**
	 * 将系统加载过程中采集到的组件分发给对应的处理器
	 */
	public void dispatchComponent() {
		log.trace("Begin to dispatch Components...[Dispatcher: " + dispatchers.size() + " ] [Bean: "
				+ componentMap.size() + "]");
		componentMap.keySet().stream().forEach(key -> {
			Object bean = componentMap.get(key);
			for (ComponentListener d : dispatchers) {
				d.recive(key, bean);
			}
		});
		for (ComponentListener d : dispatchers) {
			d.onComplete();
		}
		log.trace("Complete dispatch component");
	}

}
