package cn.cerestech.framework.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 中国行政区划代码表，总数741672个行政区划。<br/>
 * 行政编码规则：全编码，12+3位。如: 510000000000000
 * 省级-地市级-县级-乡级-村级-城乡类型(00-00-00-000-000-000)<br/>
 * 行政等级(Level)共五级,详情见Level枚举。<br/>
 * 城乡类型(Type)只有村级才有，详情见Type枚举<br/>
 * 内容包括如下:<br/>
 * 
 * 可使用方法，详情见说明。<br/>
 * Division.children("xxx"); //列出某个代码下的直接下级行政区划，不可直接用于JSON，因为下级关联太多。<br/>
 * 
 * locale(); //所属国家<br/>
 * name(); //名称: 如四川<br/>
 * fullCode(); //全编码，12位。如: 510000000000<br/>
 * simpleCode();//简短编码，全编码去0，如:51<br/>
 * province(); //所属省级编码。<br/>
 * city(); //所属市级编码。<br/>
 * county(); //所属县级编码。<br/>
 * town(); //所属乡级编码。<br/>
 * village(); //所属村级编码。<br/>
 * level(); //所属等级。见Level。<br/>
 * type(); //城乡类型，只有村级才有。其余为NONE。<br/>
 * parent(); //上级行政区域<br/>
 * root(); //获取起源节点<br/>
 * byteCode(); //获取指定编码长度<br/>
 * 
 * @author Harry He
 * 
 */
public class Division {

	// private static Logger log = LogManager.getLogger(Division.class);
	public static final String CHARSET = "UTF-8";
	public static Division ROOT = new Division("000000000000000中华人民共和国");
	private static Map<String, Division> buffer = Maps.newHashMapWithExpectedSize(750000);// 数据文件中大概条数
	/**
	 * 使用缓存，List的原始数据用于保持数据的顺序，这里额外添加一个UseBuffer保存使用过数据便于快速回调，读取信息
	 */
	static {
		String line = "";
		try {
			InputStream is = Division.class.getClassLoader().getResourceAsStream("CHN_Divisions2.txt");
			InputStreamReader isr = new InputStreamReader(is);
			// 为字符流增加缓冲功能
			BufferedReader br = new BufferedReader(isr);
			line = br.readLine();
		} catch (IOException e) {
		}

		// 将所有数据加载到缓存中，并构建层次关系
		Iterable<String> split = Splitter.on("FF").omitEmptyStrings().split(line.trim());
		// 去重复数据
		Set<String> set = Sets.newHashSet(split.iterator());
		// 对数据重新排序，便于构建层次关系
		List<String> tempList = Lists.newArrayListWithCapacity(set.size());
		tempList.addAll(set);
		Collections.sort(tempList);
		Pattern pattern = Pattern.compile("^(\\d+)(.*)");
		for (String dStr : tempList) {
			// 如果字符串不是以数字开头，则说明是脏数据
			Matcher matcher = pattern.matcher(dStr);
			if (matcher.matches()) {// 数字开头,干净数据才放入
				Division d = new Division(dStr);
				buffer.put(d.fullCode(), d);
				d.parent().addChild(d);
			}
		}

	}

	// 作为实例时使用的变量
	private List<Division> children = Lists.newArrayList();
	private Locale locale = Locale.CHINA;
	private String data = "";
	private String simpleCode = "";
	private String name = "";

	/**
	 * 行政区划的五级等级
	 * 
	 * @author Harry He
	 * 
	 */
	public enum Level {
		PROVINCE("省级"), CITY("地区级"), COUNTY("县级"), TOWN("乡级"), VILLAGE("村级");

		private String desc = "";

		Level(String desc) {
			this.desc = desc;
		}

		public String desc() {
			return desc;
		}

	}

	/**
	 * 城乡分类代码
	 * 
	 * @author Harry He
	 * 
	 */
	public enum Type {
		NONE("000", "未识别"), DOWNTOWN("111", "主城区"), URBANRURAL("112", "城乡结合区"), TOWNCENTER("121", "镇中心区"), TOWNVILLAGE("122", "镇乡结合区"), SPECIAL("123", "特殊区域"), VILLAGECENTER("210", "乡中心区"), VILLAGE(
				"220", "村庄");
		private String code = "", desc = "";

		Type(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String code() {
			return code;
		}

		public String desc() {
			return desc;
		}

		/**
		 * 通过代码识别返回类型
		 * 
		 * @param val
		 * @return
		 */
		public static Type from(String val) {
			for (Type t : Type.values()) {
				if (t.code.equals(val)) {
					return t;
				}
			}
			return Type.NONE;
		}

	}

	/**
	 * 
	 * 添加一条数据到数据池中
	 * 
	 * @return
	 */
	public void addChild(Division child) {
		Division parent = child.parent();
		parent.children.add(child);
	}

	private Division(String data) {
		this.data = data;
		this.simpleCode = simpleCode();
		this.name = name();
	}

	/**
	 * 返回当前行政区域的国家
	 */
	public Locale locale() {
		return locale;
	}

	/**
	 * 得到省级行政区划代码
	 * 
	 * @return
	 */
	public String province() {
		return data.substring(0, 2);
	}

	/**
	 * 得到地市级行政区代码
	 * 
	 * @return
	 */
	public String city() {
		return data.substring(2, 4);
	}

	/**
	 * 得到县级行政区代码
	 * 
	 * @return
	 */
	public String county() {
		return data.substring(4, 6);
	}

	/**
	 * 得到乡级行政区代码
	 * 
	 * @return
	 */
	public String town() {
		return data.substring(6, 9);
	}

	/**
	 * 得到村级行政区代码
	 * 
	 * @return
	 */
	public String village() {
		return data.substring(9, 12);
	}

	/**
	 * 得到当前行政区的级别
	 * 
	 * @return
	 */
	public Level level() {
		switch (simpleCode().length()) {
		case 2:
			return Level.PROVINCE;
		case 4:
			return Level.CITY;
		case 6:
			return Level.COUNTY;
		case 9:
			return Level.TOWN;
		default:
			return Level.VILLAGE;
		}

	}

	/**
	 * 得到当前的城镇类型
	 * 
	 * @return
	 */
	public Type type() {
		return Type.from(data.substring(12, 15));
	}

	/**
	 * 得到当前行政区划名称
	 * 
	 * @return
	 */
	public String name() {
		return data.substring(15);
	}

	/**
	 * 获得行政区划的全代码。<br/>
	 * 如北京代码为110000000000
	 * 
	 * @return
	 */
	public String fullCode() {
		return data.substring(0, 12);
	}

	/**
	 * 获得行政区划的简单代码（要去除右边的0)<br/>
	 * 如北京代码为11
	 * 
	 * @return
	 */
	public String simpleCode() {
		StringBuffer full = new StringBuffer(fullCode());
		if ("000".equals(this.village())) {
			full.delete(9, 12);
		}
		if ("000".equals(this.town())) {
			full.delete(6, 9);
		}
		if ("00".equals(this.county())) {
			full.delete(4, 6);
		}
		if ("00".equals(this.city())) {
			full.delete(2, 4);
		}
		return full.toString();
	}

	/**
	 * 返回该区域的上级编码<br/>
	 * 实现原理:通过替换当级编码为0来构建上级编码
	 * 
	 * @return
	 */
	public Division parent() {
		String parentCode = ROOT.fullCode();

		// 如果没有关联则计算出上级代码
		StringBuffer fullCode = new StringBuffer(fullCode());
		switch (level()) {
		case PROVINCE:
			parentCode = fullCode.replace(0, 2, "00").toString();
			break;
		case CITY:
			parentCode = fullCode.replace(2, 4, "00").toString();
			break;
		case COUNTY:
			parentCode = fullCode.replace(4, 6, "00").toString();
			break;
		case TOWN:
			parentCode = fullCode.replace(6, 9, "000").toString();
			break;
		case VILLAGE:
			parentCode = fullCode.replace(9, 12, "000").toString();
			break;
		}

		// 读取上级区域，如果没有返回根
		Division parent = buffer.get(parentCode);
		if (parent == null) {
			parent = ROOT;
		}
		return parent;
	}

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

	/**
	 * 获得代码节点下所有的根行政代码
	 * 
	 * @param parent
	 * @return
	 */
	public static List<Division> children(String parent) {
		parent = patchRightZero(parent, 12);
		Division parentDiv = buffer.get(parent);
		if (parentDiv == null) {
			return ROOT.children;
		} else {
			return parentDiv.children;
		}

	}

	public List<Division> children() {
		List<Division> divs = Lists.newArrayList();
		this.children.forEach(d -> {
			divs.add(new Division(d.data));
		});

		return divs;
	}

	/**
	 * 获取起源节点信息
	 * 
	 * @param code
	 * @return
	 */
	public static Division root(String code) {
		code = patchRightZero(code, 12);
		return buffer.get(code);
	}

	/**
	 * 获得n位编码 n=6,9,12
	 * 
	 * @return
	 */
	public String byteCode(Integer len) {
		return data.substring(0, len);
	}

	/**
	 * 根据上一级code,字符串模糊查询出下级行政区划，如果parentCode为空，默认识别为全国
	 * 
	 * @param pCode
	 * @param cQuery
	 * @return
	 */
	public static List<Division> fuzzyQuery(String parentCode, String query) {
		List<Division> children = null;
		if (StringUtils.isBlank(parentCode)) {
			parentCode = "000000000000";
		}
		children = Division.children(parentCode);
		List<Division> result = new ArrayList<Division>();
		if (!CollectionUtils.isEmpty(children)) {
			for (Division d : children) {
				if (d.fullCode().contains(query) || d.name().contains(query)) {
					result.add(d);
				}
			}
		}
		return result;
	}

	public static void main(String[] args) throws Throwable {
		System.out.println(Division.ROOT.children.size());

		// List<Division> res = Division.fuzzyQuery("510104","德阳");
		StringBuffer buffer = new StringBuffer();
		Division.ROOT.children.forEach(d1 -> {
			buffer.append(d1.data + "FF");
			System.out.println(d1.data);
			d1.children.forEach(d2 -> {
				buffer.append(d2.data + "FF");
				System.out.println(d2.data);
				d2.children.forEach(d3 -> {
					buffer.append(d3.data + "FF");
					System.out.println(d3.data);
				});
			});
		});

		// File file = new File(Setting.Quick.filestorage() + File.separator +
		// "CHN_Divisions2.txt");

		// Files.write(buffer.toString(), file, Charset.forName(CHARSET));

		// System.out.println(res.size());
		//
		// System.out.println(Division.root("510000"));
		// for (Division d : res) {
		// System.out.println(d.byteCode(6) + " -" + d.name() + " -" +
		// d.level().desc() + " -" + d.type().code() + " -" + d.type().desc());
		// }

	}

	/*
	 * @Override public String toString() { return "Division [data=" + data +
	 * "]"; }
	 */

}
