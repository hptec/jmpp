package cn.cerestech.framework.core.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.Classpaths;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.support.configuration.enums.ConfigKey;

/**
 * 整合采集枚举的方法
 * 
 * @author harryhe
 *
 */
public class EnumCollector {
	private static Map<String, EnumCollector> bufferPool = Maps.newHashMap();

	static {
		// 加载所有继承 DescribableEnum 和 ConfigKey 和 CategoryDescribableEnum的类
		Set<?> set = Classpaths.getSubTypeWith(DescribableEnum.class, Sets.newHashSet(""));
		for (Object de : set) {
			if (de instanceof Class) {
				Class<?> clazz = (Class<?>) de;
				EnumCollector collector = new EnumCollector(clazz);
				bufferPool.put(clazz.getName(), collector);
			}
		}

	}

	private Class<?> clazz;
	private List<KV> list;
	private DescribableEnum[] values;

	public static List<EnumCollector> allEnums() {
		return bufferPool.values().stream().collect(Collectors.toList());
	}

	public static EnumCollector forName(String className) {
		return bufferPool.get(className);
	}

	public static EnumCollector forClass(Class<?> clazz) {
		return forName(clazz.getName());
	}

	/**
	 * 找到第一个输入这个Category的类
	 * 
	 * @param name
	 * @return
	 * @return
	 */
	public static Class<?> queryClassByCategory(String name) {
		return bufferPool.values().stream().filter(ec -> {
			Class<?>[] inters = ec.clazz.getInterfaces();
			if (inters != null && inters.length > 0) {
				if (inters[0].equals(CategoryDescribableEnum.class)) {
					CategoryDescribableEnum[] cde = (CategoryDescribableEnum[]) ec.values();
					if (cde.length > 0) {
						if (name.equals(cde[0].getCategory())) {
							return true;
						}
					}
				}
			}
			return false;
		}).map(ec -> ec.clazz).findFirst().orElse(null);
	}

	public static EnumCollector queryEnumByCategory(String name) {
		return EnumCollector.forClass(EnumCollector.queryClassByCategory(name));
	}

	private EnumCollector(Class<?> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public <T> T keyOf(String key) {
		DescribableEnum[] values = values();
		for (DescribableEnum v : values) {
			DescribableEnum de = (DescribableEnum) v;
			if (de.key().equalsIgnoreCase(key)) {
				return (T) v;
			}
		}

		return null;
	}

	public List<KV> toList() {
		if (list == null) {
			list = Lists.newArrayList();
			Object[] values = (Object[]) values();
			if (values != null) {
				for (Object v : values) {
					if (isDescribableEnum()) {
						DescribableEnum de = (DescribableEnum) v;
						KV kv = KV.on().put("key", de.key()).put("desc", de.desc());
						list.add(kv);
					} else if (isConfigKey()) {
						ConfigKey ck = (ConfigKey) v;
						KV kv = KV.on().put("key", ck.key()).put("desc", ck.desc()).put("value", ck.defaultValue());
						list.add(kv);
					} else if (isCategoryDescribableEnum()) {
						CategoryDescribableEnum ck = (CategoryDescribableEnum) v;
						KV kv = KV.on().put("key", ck.key()).put("desc", ck.desc()).put("category", ck.getCategory());
						list.add(kv);
					} else {
						KV kv = KV.on().put(v.toString(), v);
						list.add(kv);
					}
				}
			}
		}
		return list;
	}

	public DescribableEnum[] values() {
		if (values == null) {
			try {
				Method valuesMethod = clazz.getMethod("values");
				if (valuesMethod != null) {
					values = (DescribableEnum[]) valuesMethod.invoke(null, null);
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
			}
		}
		return values;
	}

	public Boolean isConfigKey() {
		Class<?>[] inters = clazz.getInterfaces();
		if (inters != null && inters.length > 0) {
			return inters[0].equals(ConfigKey.class);
		}
		return Boolean.FALSE;
	}

	public Boolean isDescribableEnum() {
		Class<?>[] inters = clazz.getInterfaces();
		if (inters != null && inters.length > 0) {
			return inters[0].equals(DescribableEnum.class);
		}
		return Boolean.FALSE;
	}

	public Boolean isCategoryDescribableEnum() {
		Class<?>[] inters = clazz.getInterfaces();
		if (inters != null && inters.length > 0) {
			return inters[0].equals(CategoryDescribableEnum.class);
		}
		return Boolean.FALSE;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getName() {
		return clazz.getName();
	}
}
