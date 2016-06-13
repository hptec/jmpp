package cn.cerestech.framework.core.components;

public interface ComponentListener {

	void recive(String beanName, Object bean);

	void onComplete();
}
