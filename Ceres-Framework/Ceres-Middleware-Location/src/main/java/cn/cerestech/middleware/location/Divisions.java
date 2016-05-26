package cn.cerestech.middleware.location;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.middleware.location.entity.Division;
import cn.cerestech.middleware.location.enums.AdminLevel;

public class Divisions {
	private static String jsonStr;
	private static Logger log = LogManager.getLogger();
	private static Jsons jsonData;
	private static Map<String, Jsons> dataPool = Maps.newHashMap();

	// private static Logger log = LogManager.getLogger(Division.class);
	public static final String CHARSET = "UTF-8";
	private static Map<String, Divisions> buffer = Maps.newHashMapWithExpectedSize(750000);// 数据文件中大概条数
	/**
	 * 使用缓存，List的原始数据用于保持数据的顺序，这里额外添加一个UseBuffer保存使用过数据便于快速回调，读取信息
	 */
	static {

		try {
			jsonStr = Resources.toString(Resources.getResource("divisions_zh-CN.json"), Charset.defaultCharset());
			jsonData = Jsons.from(jsonStr);
			jsonData.asList().forEach(p -> {
				dataPool.put(p.get("code").asString(), p);
				p.get("children").asList().forEach(c -> {
					dataPool.put(c.get("code").asString(), c);
					c.get("children").asList().forEach(o -> {
						dataPool.put(o.get("code").asString(), o);
					});
				});
			});
		} catch (IOException e) {
			log.throwing(e);
		}

	}

	public static Division findByCode(String code) {
		Jsons div = dataPool.get(code);
		Division division = null;
		if (div != null) {
			division = div.to(Division.class);
			division.setLevel(AdminLevel.codeOf(code));
		}
		return division;
	}

	public static Division findByLevel(AdminLevel level) {
		return null;
	}

	// /**
	// * 获得行政区划的简单代码（要去除右边的0)<br/>
	// * 如北京代码为11
	// *
	// * @return
	// */
	// public String simpleCode() {
	// StringBuffer full = new StringBuffer(fullCode());
	// if ("000".equals(this.village())) {
	// full.delete(9, 12);
	// }
	// if ("000".equals(this.town())) {
	// full.delete(6, 9);
	// }
	// if ("00".equals(this.county())) {
	// full.delete(4, 6);
	// }
	// if ("00".equals(this.city())) {
	// full.delete(2, 4);
	// }
	// return full.toString();
	// }
	//
	// /**
	// * 返回该区域的上级编码<br/>
	// * 实现原理:通过替换当级编码为0来构建上级编码
	// *
	// * @return
	// */
	// public Divisions parent() {
	// String parentCode = ROOT.fullCode();
	//
	// // 如果没有关联则计算出上级代码
	// StringBuffer fullCode = new StringBuffer(fullCode());
	// switch (level()) {
	// case PROVINCE:
	// parentCode = fullCode.replace(0, 2, "00").toString();
	// break;
	// case CITY:
	// parentCode = fullCode.replace(2, 4, "00").toString();
	// break;
	// case COUNTY:
	// parentCode = fullCode.replace(4, 6, "00").toString();
	// break;
	// case TOWN:
	// parentCode = fullCode.replace(6, 9, "000").toString();
	// break;
	// case VILLAGE:
	// parentCode = fullCode.replace(9, 12, "000").toString();
	// break;
	// }
	//
	// // 读取上级区域，如果没有返回根
	// Divisions parent = buffer.get(parentCode);
	// if (parent == null) {
	// parent = ROOT;
	// }
	// return parent;
	// }

	/**
	 * 删除掉字符串右边的0
	 * 
	 * @param str
	 * @return
	 */
	protected static String trimRightZero(String str) {
		StringBuffer buf = new StringBuffer(str);
		while (buf.charAt(buf.length() - 1) == '0') {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	/**
	 * 在代码右边补足0
	 * 
	 * @param str
	 * @return
	 */
	protected static String patchRightZero(String str, Integer len) {
		if (str == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() < len) {
			buf.append('0');
		}
		return buf.toString();
	}

	// /**
	// * 获得代码节点下所有的根行政代码
	// *
	// * @param parent
	// * @return
	// */
	// public static List<Divisions> children(String parent) {
	// parent = patchRightZero(parent, 12);
	// Divisions parentDiv = buffer.get(parent);
	// if (parentDiv == null) {
	// return ROOT.children;
	// } else {
	// return parentDiv.children;
	// }
	//
	// }
	//
	// public List<Divisions> children() {
	// List<Divisions> divs = Lists.newArrayList();
	// this.children.forEach(d -> {
	// divs.add(new Divisions(d.data));
	// });
	//
	// return divs;
	// }

	/**
	 * 获取起源节点信息
	 * 
	 * @param code
	 * @return
	 */
	public static Divisions root(String code) {
		code = patchRightZero(code, 12);
		return buffer.get(code);
	}

	// /**
	// * 获得n位编码 n=6,9,12
	// *
	// * @return
	// */
	// public String byteCode(Integer len) {
	// return data.substring(0, len);
	// }
	//
	// /**
	// * 根据上一级code,字符串模糊查询出下级行政区划，如果parentCode为空，默认识别为全国
	// *
	// * @param pCode
	// * @param cQuery
	// * @return
	// */
	// public static List<Divisions> fuzzyQuery(String parentCode, String query)
	// {
	// List<Divisions> children = null;
	// if (!Strings.isNullOrEmpty(parentCode)) {
	// parentCode = "000000000000";
	// }
	// children = Divisions.children(parentCode);
	// List<Divisions> result = new ArrayList<Divisions>();
	// if (!CollectionUtils.isEmpty(children)) {
	// for (Divisions d : children) {
	// if (d.fullCode().contains(query) || d.name().contains(query)) {
	// result.add(d);
	// }
	// }
	// }
	// return result;
	// }
	//
	// public static void main(String[] args) throws Throwable {
	// System.out.println(Divisions.ROOT.children.size());
	//
	// // List<Division> res = Division.fuzzyQuery("510104","德阳");
	// StringBuffer buffer = new StringBuffer();
	// Divisions.ROOT.children.forEach(d1 -> {
	// buffer.append(d1.data + "FF");
	// System.out.println(d1.data);
	// d1.children.forEach(d2 -> {
	// buffer.append(d2.data + "FF");
	// System.out.println(d2.data);
	// d2.children.forEach(d3 -> {
	// buffer.append(d3.data + "FF");
	// System.out.println(d3.data);
	// });
	// });
	// });
	//
	// }

	/*
	 * @Override public String toString() { return "Division [data=" + data +
	 * "]"; }
	 */

}
