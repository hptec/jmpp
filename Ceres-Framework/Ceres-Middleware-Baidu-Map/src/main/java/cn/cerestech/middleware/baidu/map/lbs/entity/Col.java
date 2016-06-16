package cn.cerestech.middleware.baidu.map.lbs.entity;

import java.util.Map;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.Strings;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月16日
 */
public class Col {
	public static enum Type{
		INT64(1,"Int64"),
		DOUBLE(2,"Int64"),
		STRING(3,"Int64"),
		URL(4,"Int64")
		;
		long key; String desc;
		private Type(long key, String desc){
			this.key = key;
			this.desc = desc;
		}
		public long key(){
			return key;
		}
		public String desc(){
			return desc;
		}
	}
	
	private String name;//	column的属性中文名称	string(45)	必选
	private String key;//	column存储的属性key	string(45)	必选，同一个geotable内的名字不能相同
	private Type type = Type.STRING;//	存储的值的类型	uint32	必选，枚举值1:Int64, 2:double, 3:string, 4:在线图片url
	private int max_length = 50;//	最大长度	uint32	最大值2048，最小值为1。当type为string该字段有效，此时该字段必填。此值代表utf8的汉字个数，不是字节个数
	private String default_value;//	默认值	string(45)	设置默认值
	private boolean is_sortfilter_field = false;//	是否检索引擎的数值排序筛选字段	uint32	必选 1代表是，0代表否。设置后，在请求 LBS云检索时可针对该字段进行排序。该字段只能为int或double类型，最多设置15个
	private boolean is_search_field = false;//	是否检索引擎的文本检索字段	uint32	必选 1代表支持，0为不支持。只有type为string时可以设置检索字段，只能用于字符串类型的列且最大长度不能超过512个字节
	private boolean is_index_field = false;//	是否存储引擎的索引字段		uint32	必选 用于存储接口查询:1代表支持，0为不支持 注：is_index_field=1 时才能在根据该列属性值检索时检索到数据
	private boolean is_unique_field = false;//	是否云存储唯一索引字段，方便更新，删除，查询	uint32	可选，1代表是，0代表否。设置后将在数据创建和更新时进行该字段唯一性检查，并可以以此字段为条件进行数据的更新、删除和查询。最多设置1个
	private long geotable_id;//	所属于的geotable_id	string(50)	
	
	private Col(String name, String key){
		this.key = key;
		this.name = name;
	}
	public static Col of(String name, String key){
		return new Col(name, key);
	}
	
	public Col type(Type type){
		this.type = type;
		return this;
	}
	
	public Col length(int length){
		this.max_length = length;
		return this;
	}
	
	public Col defaultVal(String defVal){
		this.default_value = defVal;
		return this;
	}
	
	public Col sortAble(boolean sorted){
		this.is_sortfilter_field = sorted;
		return this;
	}
	
	public Col searchedAble(boolean searched){
		this.is_search_field = searched;
		if(this.is_search_field){
			this.is_index_field = true;
		}
		return this;
	}
	
	public Col unique(boolean unique){
		this.is_unique_field = unique;
		return this;
	}
	
	public Map<String, Object> def(String sn){
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", Strings.nullToEmpty(this.name));
		params.put("key", Strings.nullToEmpty(this.key));
		params.put("type", this.type.key());
		if(Type.STRING.equals(this.type)){
			params.put("max_length", this.max_length);
		}else{
			this.is_search_field = false;
		}
		if(!Strings.isNullOrEmpty(this.default_value)){params.put("default_value", Strings.nullToEmpty(this.default_value));}
		if(!this.type.equals(Type.DOUBLE) && Type.INT64.equals(this.type)){
			this.is_sortfilter_field = false;
		}
		params.put("is_sortfilter_field", this.is_sortfilter_field?1:0);
		params.put("is_search_field", this.is_search_field?1:0);
		if(this.is_search_field){
			this.is_index_field = true;
		}
		params.put("is_index_field", this.is_index_field?1:0);
		params.put("is_unique_field", this.is_unique_field?1:0);
		params.put("geotable_id", this.geotable_id);
		
		params.put("sn", Strings.nullToEmpty(sn));
		return params;
	}
}
