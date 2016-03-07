package cn.cerestech.support.web.console.menu;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.components.ComponentDispatcher;
import cn.cerestech.framework.core.json.Jsons;

@Service
public class MenuService implements ComponentDispatcher {

	private Map<String, KV> menuMap = Maps.newHashMap();

	private Logger log = LogManager.getLogger();

	private void putMenu(Class<?> classMenu) {

		Menu typedMenu = classMenu.getAnnotation(Menu.class);

		// 在class上的menu,如果没有指定parent,则其下级可能为顶层菜单，忽略本级

		if (Strings.isNullOrEmpty(typedMenu.parent())) {
			// 忽略
		} else {
			KV menu = fromMenu(typedMenu);
			// 在class上的Menu注解必须指定key
			if (Strings.isNullOrEmpty(typedMenu.key())) {
				throw new IllegalArgumentException("menu key should be specified as " + classMenu.toString());
			}

			log.trace(Jsons.toPrettyJson(menu));
		}

		Field[] fs = classMenu.getFields();
		for (Field f : fs) {
			if (f.isAnnotationPresent(Menu.class)) {
				Menu menu = f.getAnnotation(Menu.class);
				KV fieldMenu = fromMenu(menu);
				fieldMenu.put("key", f.getName());

				log.trace("Found Field Menu: [ " + f.getName() + " ]" + Jsons.toPrettyJson(fieldMenu));
			}
		}

	}

	public Boolean exist(String key) {
		return menuMap.containsKey(key);
	}

	public void addChild(String key, KV childMenu) {
		KV menuParent = menuMap.get(key);
		if (menuParent == null) {
			menuParent = KV.on();
			menuMap.put(key, menuParent);
		}
		List<KV> children = (List<KV>) menuParent.get("children");
		if (children == null) {
			children = Lists.newArrayList();
			menuParent.put("children", children);
		}
		children.add(childMenu);
	}

	private KV fromMenu(Menu m) {
		KV menuThis = KV.on();
		menuThis.put("caption", m.caption());
		menuThis.put("icon", m.icon());
		menuThis.put("sort", m.sort());
		menuThis.put("parent", m.parent());
		return menuThis;
	}

	@Override
	public void recive(String beanName, Object bean) {

		if (bean instanceof MenuSet) {
			putMenu(bean.getClass());
		}

	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub

	}
}
