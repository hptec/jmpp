package cn.cerestech.middleware.baidu.map.lbs.api;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.Strings;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.middleware.baidu.map.lbs.entity.Poi;
import cn.cerestech.middleware.baidu.map.lbs.status.Status;

/**
 * 位置数据管理
 * 
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月17日
 */
public class PoiApi extends BdApi {

	private static final String URL_CREATE = "http://api.map.baidu.com/geodata/v3/poi/create";
	private static final String URL_QUERY = "http://api.map.baidu.com/geodata/v3/poi/list";
	private static final String URL_QUERY_ONE = "http://api.map.baidu.com/geodata/v3/poi/detail";
	private static final String URL_UPDATE = "http://api.map.baidu.com/geodata/v3/poi/update";
	private static final String URL_DELETE = "http://api.map.baidu.com/geodata/v3/poi/delete";
	private static final String URL_UPLOAD = "http://api.map.baidu.com/geodata/v3/poi/upload";// 批量上传数据接口
	private static final String URL_UPLOAD_STATUS = "http://api.map.baidu.com/geodata/v3/job/listimportdata";// 进度查询
	/**
	 * 批量操作任务查询（list job）接口 请求url
	 */
	private static final String URL_JOB_LIST_STATUS = "http://api.map.baidu.com/geodata/v3/job/list";// //

	/**
	 * 根据id查询批量任务（detail job）接口 请求url
	 */
	private static final String URL_JOB_STATUS = "http://api.map.baidu.com/geodata/v3/job/detail";// GET请求

	public PoiApi(String access_key) {
		super(access_key);
	}

	/**
	 * 存储数据
	 * 
	 * @param t
	 * @param sn
	 * @return
	 */
	public <T extends Poi> Status<String> create(T t, String sn) {
		Map<String, Object> params = t.toParams();
		params.put("ak", this.getAccess_key());
		params.put("sn", Strings.nullToEmpty(sn));
		
		System.out.println(Jsons.from(params).toPrettyJson());

		String ret = Https.of().post(URL_CREATE, params).readString();
		return Status.as(ret);
	}

	/**
	 * 查询指定条件的数据
	 * 
	 * @param {index
	 *            key} 用户在column定义的key/value对
	 *            column需要设置了is_index_field=1。对于string，是前缀匹配，如需精确匹配请在末尾加$。
	 *            对于int或者double，则是范围查找，传递的格式为最小值,最大值。当无最小值或者最大值时，用-代替，同时，
	 *            此字段最大长度不超过50，最小值与最大值都是整数
	 *            例：如加入一个命名为color数据类型为string的column，在检索是可设置为“color=red”
	 *            的形式来检索color字段为red的POI
	 * @param title
	 *            记录（数据）名称 string(256) 可选
	 * @param tags
	 *            记录的标签（用于检索筛选） string(256) 可选
	 * @param bounds
	 *            查询的矩形区域 string(100) 格式x1,y1;x2,y2分别代表矩形的左上角和右下角，可选
	 * @param geotable_id
	 *            geotable_id string(50) 必选
	 * @param page_index
	 *            分页索引 uint32 默认为0
	 * @param page_size
	 *            分页数目 uint32 默认为10，上限为200
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 */
	public Status<String> query(long geotable_id, Integer page_index, Integer page_size, String sn,
			Criteria... criterias) {
		Map<String, String> params = Maps.newHashMap();
		for (Criteria c : criterias) {
			params.put(c.getKey(), c.getVal());
		}
		params.put("ak", this.getAccess_key());
		params.put("sn", Strings.nullToEmpty(sn));
		if (page_index == null) {
			page_index = 0;
		}
		if (page_size == null || page_size.compareTo(200) > 0 || page_size.compareTo(0) <= 0) {
			page_size = 200;
		}
		params.put("page_index", page_index + "");
		params.put("page_size", page_size + "");
		params.put("geotable_id", geotable_id + "");

		String ret = Https.of().get(URL_QUERY, params).readString();

		return Status.as(ret);
	}

	/**
	 * 根据poi 的id 查询记录
	 * 
	 * @param id
	 *            poi主键 uint64 必选
	 * @param geotable_id
	 *            表主键 int32 必选
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * 
	 * @return
	 */
	public Status<String> queryById(long geotable_id, long id, String sn) {
		Map<String, String> params = Maps.newHashMap();
		params.put("geotable_id", geotable_id + "");
		params.put("id", id + "");
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("ak", this.getAccess_key());

		String ret = Https.of().get(URL_QUERY_ONE, params).readString();

		return Status.as(ret);
	}

	/**
	 * 修改数据
	 * 
	 * @param id
	 *            poi的id uint64 当不存在唯一索引字段时必须，存在唯一索引字段可选 自定义唯一索引key Value
	 *            用户自定义类型 可选，若自定义索引字段和id共存时，将以id为准，且自定义索引key将被新的value
	 * @param title
	 *            poi名称 string(256)
	 * @param address
	 *            地址
	 * @param tags
	 *            tags
	 * @param latitude
	 *            用户上传的纬度 double
	 * @param longitude
	 *            用户上传的经度 double
	 * @param coord_type
	 *            用户上传的坐标的类型 uint32 必选 1．GPS经纬度坐标 2．测局加密经纬度坐标 3．百度加密经纬度坐标
	 *            4．百度加密墨卡托坐标
	 * @param geotable_id
	 *            记录关联的geotable的标识 string(50) 必选，加密后的id
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * @param {column
	 *            key} 用户在column定义的key/value对 用户自定义的的列类别
	 * 
	 */
	public <T extends Poi> Status<String> update(long id, T t, String sn) {
		Map<String, Object> params = t.toParams();
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("ak", this.getAccess_key());
		params.put("id", id);

		String ret = Https.of().post(URL_UPDATE, params).readString();

		return Status.as(ret);
	}

	/**
	 * 删除数据
	 * 
	 * @param id
	 *            被删除的id uint64 如果设置了这个参数，其它的删除条件会被忽略，只会根据id删除单条poi。此时此操作不是批量请求。
	 *            自定义唯一索引key Value 用户自定义类型 可选，若自定义索引字段和id共存时，优先选择根据id删除poi。
	 * @param ids
	 *            id列表 以,分隔的id 最多1000个id, 如果设置了批量删除is_total_del =
	 *            1并且没有设置id字段，则优先根据ids删除多条poi, 其它条件将被忽略.
	 * @param {index
	 *            key} 用户在column定义的key/value对
	 *            column需要设置了is_index_field=1。对于string，是前缀匹配，如需精确匹配请在末尾加$。
	 *            对于int或者double，则是范围查找，传递的格式为：最小值,最大值。当无最小值或者最大值时，用-代替，同时，
	 *            此字段最大长度不超过50，最小值与最大值都是整数.
	 * @param title
	 *            名称 string(256) 可选
	 * @param tags
	 *            标签 string(256) 可选
	 * @param bounds
	 *            查询的矩形区域 string(100) 格式x1,y1;x2,y2分别代表矩形的左上角和右下角
	 * @param geotable_id
	 *            geotable_id string(50) 必选
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * @param {column
	 *            key} 用户在column定义的key/value对
	 * @param is_total_del
	 *            标记为批量删除 int32 如需删除一条以上数据，在设定数据范围的条件时，还需要将该字段设为1。
	 *            注意：若仅设为1，而不设定数据范围的条件（如不指定ids、tag等），则默认为删除全表数据
	 * @return
	 */
	public Status<String> delete(long geotable_id, long[] ids, boolean is_total_del, String sn, Criteria... criterias) {
		Map<String, Object> params = Maps.newHashMap();
		for (Criteria c : criterias) {
			params.put(c.getKey(), c.getVal());
		}
		params.put("ak", this.getAccess_key());
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("geotable_id", geotable_id);

		params.put("is_total_del", is_total_del ? 1 : 0);

		if (ids != null && ids.length > 0) {
			if (ids.length == 1) {
				params.put("id", ids[0]);
			} else {
				params.put("ids", Arrays.toString(ids).replaceAll("[ |\\[|\\]]", ""));
			}
		}

		String ret = Https.of().post(URL_DELETE, params).readString();

		return Status.as(ret);
	}

	/**
	 * 批量上传数据接口
	 * 
	 * @param geotable_id
	 *            导入的geotable的标识 uint32 必选
	 * @param poi_list
	 *            输入的poi列表名称 file 必选，小于8M
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * @param timestamp
	 *            时间戳 uint32 必选，配合sn使用，增加时间戳安全验证
	 * 
	 * @return
	 */
	public Status<String> upload(long geotable_id, String sn, File poi_list) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("ak", this.getAccess_key());
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("geotable_id", geotable_id);

		String ret = Https.of().upload(URL_UPLOAD, params, "poi_list", poi_list).readString();

		return Status.as(ret);
	}

	/**
	 * 批量上传数据的进度
	 * 
	 * @param geotable_id
	 *            导入的geotable的标识 uint32 必选
	 * @param job_id
	 *            导入接口返回的job_id string（50） 必选
	 * @param status
	 *            Poi导入的状态 uint32 默认为0，0为全部，1为失败，2为成功，已经废弃。
	 * @param page_index
	 *            分页索引 uint32 默认为0
	 * @param page_size
	 *            分页数目 uint32 默认为10，上限为100
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * @param timestamp
	 *            时间戳 uint32
	 * 
	 * @return
	 */
	public Status<String> uploadState(long geotable_id, long job_id, int status, Integer page_index, Integer page_size,
			String sn, String timestamp) {
		Map<String, String> params = Maps.newHashMap();
		params.put("ak", this.getAccess_key());
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("geotable_id", geotable_id + "");
		params.put("job_id", job_id + "");
		params.put("status", status + "");
		params.put("timestamp", timestamp + "");

		if (page_index == null) {
			page_index = 0;
		}
		if (page_size == null || page_size.compareTo(100) > 0 || page_size.compareTo(0) <= 0) {
			page_size = 100;
		}
		params.put("page_index", page_index + "");
		params.put("page_size", page_size + "");

		String ret = Https.of().get(URL_UPLOAD_STATUS, params).readString();

		return Status.as(ret);
	}
	
	public static void main(String[] args) {
		PoiApi poi = new PoiApi("khllc7E6PoLsgl6kpxY4SB9LVu9G7Akn");
		
//		Dinner dinn = new Dinner();
//		dinn.setCoord_type(3);
//		dinn.setGeotable_id(142975L);
//		dinn.setHotel_id(1);
//		dinn.setHotel_name("某某酒店");
//		dinn.setSearch_tag("宴会宝场地");
//		dinn.setLatitude(30.66541);//104.070354,30.66541
//		dinn.setLongitude(104.070354);
//		dinn.setTitle("某某酒店");
//		System.out.println(poi.create(dinn, null).asString());;
		
		System.out.println(poi.delete(142975L, null, true, null).asString());
	}
	public static class Dinner extends Poi{
		private String hotel_name;
		private long hotel_id;
		private String search_tag;
		public String getHotel_name() {
			return hotel_name;
		}
		public void setHotel_name(String hotel_name) {
			this.hotel_name = hotel_name;
		}
		public long getHotel_id() {
			return hotel_id;
		}
		public void setHotel_id(long hotel_id) {
			this.hotel_id = hotel_id;
		}
		public String getSearch_tag() {
			return search_tag;
		}
		public void setSearch_tag(String search_tag) {
			this.search_tag = search_tag;
		}
	}
}
