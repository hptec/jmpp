package cn.cerestech.middleware.balance.config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationRunner;

import com.beust.jcommander.internal.Maps;

import cn.cerestech.middleware.balance.dataobject.BalanceDefinition;

/**
 * 对账户进行配置和定义
 * 
 * @author harryhe
 *
 */
public abstract class AbstractBalanceConfig implements ApplicationRunner {

	private static Map<String, BalanceDefinition> defPool = Maps.newHashMap();

	protected void define(BalanceDefinition def) {
		defPool.put(def.getType().key(), def);
	}

	public static BalanceDefinition get(String type) {
		return defPool.get(type);
	}

	public static List<BalanceDefinition> getDefinitions() {
		return defPool.values().stream().collect(Collectors.toList());
	}

}
