package cn.cerestech.framework.core.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.utils.KV;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

/**
 * 整合采集枚举的方法
 * 
 * @author harryhe
 *
 */
public class EnumCollector {
	private static Map<String, EnumCollector> bufferPool = Maps.newHashMap();

	static {
		// 加载所有继承 DescribableEnum的类
		if (bufferPool.isEmpty()) {
			new FastClasspathScanner().//
					scan().getNamesOfClassesImplementing(DescribableEnum.class).forEach(str -> {
						try {
							EnumCollector collector = new EnumCollector(
									(Class<? extends DescribableEnum>) Class.forName(str));
							bufferPool.put(str, collector);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
		}
	}

	private Class<? extends DescribableEnum> clazz;
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

	private EnumCollector(Class<? extends DescribableEnum> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public <T> T keyOf(String key) {
		DescribableEnum[] values = values();
		for (DescribableEnum v : values) {
			if (v.key().equalsIgnoreCase(key)) {
				return (T) v;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T nameOf(String key) {
		DescribableEnum[] values = values();
		for (DescribableEnum v : values) {
			if (v.toString().equalsIgnoreCase(key)) {
				return (T) v;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T descOf(String desc) {
		DescribableEnum[] values = values();
		for (DescribableEnum v : values) {
			if (v.desc().equalsIgnoreCase(desc)) {
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
					KV kv = KV.on();
					if (v instanceof DescribableEnum) {
						DescribableEnum de = (DescribableEnum) v;
						kv.put("key", de.key()).put("desc", de.desc());
					}
					list.add(kv);
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

	public Boolean isInterfaceOf(Class<? extends DescribableEnum> itface) {
		Class<?>[] inters = clazz.getInterfaces();
		Boolean found = Boolean.FALSE;
		if (inters != null && inters.length > 0) {
			for (Class<?> c : inters) {
				if (c.equals(itface)) {
					found = Boolean.TRUE;
					break;
				}
			}
		}
		return found;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getName() {
		return clazz.getName();
	}

}
