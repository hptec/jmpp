package cn.cerestech.middleware.baidu.map;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.http.Https;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月10日
 */
public class LBSTable {
	
	public static void main(String[] args) {
		String ak = "khllc7E6PoLsgl6kpxY4SB9LVu9G7Akn";
//		getTables("khllc7E6PoLsgl6kpxY4SB9LVu9G7Akn", "宴会宝数据");
//		detail(ak, "142975");
		
	}
	
	
	/**
	 * 创建宴会宝数据表
	 * @param name
	 */
	public static void createTable(String name){
		String url = "http://api.map.baidu.com/geodata/v3/geotable/create";
		Map<String, Object> params = Maps.newHashMap();
		params.put("name","宴会宝数据");//geotable的中文名称	string(45)	必选
		params.put("geotype","1");//	geotable持有数据的类型	int32	必选 1：点；2：线；3：面。默认为1（当前不支持“线”）
		params.put("is_published","1");//	是否发布到检索	int32	必选 0：未自动发布到云检索，1：自动发布到云检索； 注：1）通过URL方式创建表时只有is_published=1时 在云检索时表内的数据才能被检索到。2）可通过数据管理模块设置，在设置中将是否发送到检索一栏中选定为是即可。
		params.put("ak","khllc7E6PoLsgl6kpxY4SB9LVu9G7Akn");//	用户的访问权限key	string(50)	必选
//		params.put("sn","");//	用户的权限签名	string(50)	可选
//		params.put("timestamp","");//	时间戳	uint32	可选，配合sn使用，增加时间戳安全验证
		
		String ret = Https.of().post(url, params).readString();//{"status":0,"id":142975,"message":"\u6210\u529f"}
	}
	
	public static void getTables(String ak, String tableName){
		String url = "http://api.map.baidu.com/geodata/v3/geotable/list";
		Map<String, String> params = Maps.newHashMap();
		params.put("ak", ak);
		if(!Strings.isNullOrEmpty(tableName)){
			params.put("name", tableName);
		}
//		params.put("sn", "");//签名
		
		String ret = Https.of().get(url, params).readString();
		System.out.println(ret);
	}
	
	public static void detail(String ak, String id){
		String url = "http://api.map.baidu.com/geodata/v3/geotable/detail";
		Map<String, String> params = Maps.newHashMap();
		params.put("ak", ak);
		params.put("id", id);
		String ret = Https.of().get(url, params).readString();
		System.out.println(ret);
	}
	
	public static void update(String ak, String id, String name, boolean isPublished){
		String url = "http://api.map.baidu.com/geodata/v3/geotable/update";
		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("is_published", isPublished?"1":"0");
		params.put("name", name);
		params.put("ak", ak);
//		params.put("sn", "");
		
		String ret = Https.of().post(url, params).readString();
		System.out.println(ret);
	}
	
	public static void delete(String ak, String id){
		String url = "http://api.map.baidu.com/geodata/v3/geotable/delete";
		Map<String, Object> params = Maps.newHashMap();
		params.put("ak", ak);
		params.put("id", id);
//		params.put("sn", "");
		
		String ret = Https.of().post(url, params).readString();
		System.out.println(ret);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
