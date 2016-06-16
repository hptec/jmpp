package cn.cerestech.middleware.baidu.map.lbs.api;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.middleware.baidu.map.lbs.status.Status;

/**
 * 位置数据API
 * 
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月16日
 */
public class GeoApi extends BdApi {
	private static final String URL_CREATE = "http://api.map.baidu.com/geodata/v3/geotable/create";
	private static final String URL_QUERY = "http://api.map.baidu.com/geodata/v3/geotable/list";
	private static final String URL_QUERY_ONE = "http://api.map.baidu.com/geodata/v3/geotable/detail";
	private static final String URL_UPDATE = "http://api.map.baidu.com/geodata/v3/geotable/update";
	private static final String URL_DELETE = "http://api.map.baidu.com/geodata/v3/geotable/delete";

	public GeoApi(String access_key) {
		super(access_key);
	}

	/**
	 * @param name
	 *            geotable的中文名称 string(45) 必选
	 * @param geotype
	 *            geotable持有数据的类型 int32 必选 1：点；2：线；3：面。默认为1（当前不支持“线”）
	 * @param is_published
	 *            是否发布到检索 int32 必选 0：未自动发布到云检索， 1：自动发布到云检索；
	 *            注：1）通过URL方式创建表时只有is_published=1时 在云检索时表内的数据才能被检索到。
	 *            2）可通过数据管理模块设置，在设置中将是否发送到检索一栏中选定为是即可。
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * @param timestamp
	 *            时间戳 uint32 可选，配合sn使用，增加时间戳安全验证
	 * 
	 * @return
	 */
	public Status<String> create(String name, String geotype, String sn, Long timestamp, boolean is_published) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("name", name);
		params.put("geotype", geotype);
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("is_published", is_published ? "1" : "0");
		params.put("ak", this.getAccess_key());
		params.put("timestamp", timestamp != null ? timestamp : "");

		String ret = Https.of().post(URL_CREATE, params).readString();

		return Status.as(ret);
	}

	/**
	 * @param name
	 *            geotable的名字 string(45) 可选 ak 用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * 
	 * @return
	 */
	public Status<String> query(String name, String sn) {
		Map<String, String> params = Maps.newHashMap();
		params.put("ak", this.getAccess_key());
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("name", name);

		String ret = Https.of().get(URL_QUERY, params).readString();

		return Status.as(ret);
	}

	/**
	 * 查询某一个geo 表
	 * 
	 * @param id
	 * @param sn
	 * @return
	 */
	public Status<String> queryById(long id, String sn) {
		Map<String, String> params = Maps.newHashMap();
		params.put("ak", this.getAccess_key());
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("id", id + "");

		String ret = Https.of().get(URL_QUERY_ONE, params).readString();
		return Status.as(ret);
	}

	/**
	 * @param id
	 *            geotable主键 uint32 必选 is_published 是否发布到检索 int32 会引起批量操作
	 * @param name
	 *            geotable的中文名称 string(45) 可选
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * 
	 * @return
	 */
	public Status<String> update(long id, String name, String sn) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		params.put("name", name);
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("ak", this.getAccess_key());

		String ret = Https.of().post(URL_UPDATE, params).readString();

		return Status.as(ret);
	}

	/**
	 * 删除数据表， 只有保重没有数据才能删除
	 * 
	 * @param id
	 *            表主键 uint32 必选
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * @return
	 */
	public Status<String> delete(long id, String sn) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("ak", this.getAccess_key());
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("id", id);

		String ret = Https.of().post(URL_DELETE, params).readString();
		return Status.as(ret);
	}

}
