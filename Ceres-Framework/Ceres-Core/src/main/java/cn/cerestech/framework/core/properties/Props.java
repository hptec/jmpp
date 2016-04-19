package cn.cerestech.framework.core.properties;

import java.io.File;
import java.util.Map;
import java.util.Properties;


import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.Files;

import cn.cerestech.framework.core.Resources;
import cn.cerestech.framework.core.log.Logable;
import cn.cerestech.framework.core.strings.StringTypes;
import cn.cerestech.framework.core.utils.Classpaths;

public class Props implements Logable{
	private Properties prop;
	private static Map<String, Props> cache = Maps.newHashMap();

	static {
		// 扫描所有的属性文件
		Classpaths.getResources(ln -> {
			String ext = Strings.nullToEmpty(Files.getFileExtension(ln));
			return ext.equalsIgnoreCase("properties");
		} , ".").forEach(str -> {
			if (str.contains(File.separator) && !str.startsWith("com.cerestech")) {
				log.trace("Ignore properties [ " + str + " ]");
				return;
			}

			log.debug("Load properties [ " + str + " ]");
			try {
				Props.cache.put(str, new Props(Resources.getResourceAsProperties(str)));
			} catch (Exception e) {
				log.catching(e);
			}
		});

	}
	
	private Props(){}
	private Props(Properties properties){
		this.prop = properties;
	}
	
	public static Props of(){
		return new Props();
	}
	
	public static Props of(Properties properties){
		return new Props(properties);
	}
	
	public StringTypes get(String key){
		StringTypes type = new StringTypes("");
		if(this.prop != null){
			type = new StringTypes(this.prop.getProperty(key,""));
		}else{
			for (Props pp : this.cache.values()) {
				StringTypes tmp = pp.get(key);
				if(tmp == null || !Strings.isNullOrEmpty(tmp.stringValue())){
					type = tmp;
				}
			}
		}
		return type;
	}
	
	public StringTypes get(String uri, String key){
		Props p = load(uri);
		return p.get(key);
	}
	
	public static Props load(String uri){
		if(Strings.isNullOrEmpty(uri)){
			return Props.of();
		}
		Props p = Props.cache.get(uri);
		if(p == null){
			try {
				Properties pro = Resources.getResourceAsProperties(uri);
				if(pro != null){
					p = Props.of(pro);
					Props.cache.put(uri, p);
				}else{
					p = Props.of();	
				}
			} catch (Exception e) {
				p = Props.of();
			}
		}
		return p;
	}
	
	
	public static void main(String[] args) {
//		Classpaths.getResources(ln -> {
//			String ext = Strings.nullToEmpty(Files.getFileExtension(ln));
//			return ext.equalsIgnoreCase("properties");
//		} , ".").forEach(str -> {
//			System.out.println(str);
//			if (str.contains(File.separator) && !str.startsWith("com.cerestech")) {
//				System.out.println("ignore: "+ str);
//				log.trace("Ignore properties [ " + str + " ]");
//				return;
//			}
//
//			log.debug("Load properties [ " + str + " ]");
//			try {
//				System.out.println(Resources.getResourceAsProperties(str));
//			} catch (Exception e) {
//				log.catching(e);
//			}
//		});
		
		System.out.println(Props.of().get("name").stringValue());
	}

}
