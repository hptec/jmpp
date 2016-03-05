package cn.cerestech.framework.core.components;

public interface ComponentDispatcher {

	void recive(String beanName, Object bean);

	void onComplete();
}
