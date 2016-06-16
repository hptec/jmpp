package cn.cerestech.middleware.baidu.map.lbs.api;

import java.util.Map;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.middleware.baidu.map.lbs.entity.Col;
import cn.cerestech.middleware.baidu.map.lbs.status.Status;

/**
 * 自定义数据列APi
 * 
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月16日
 */
public class ColApi extends BdApi {
	private static final String URL_CREATE = "http://api.map.baidu.com/geodata/v3/column/create";

	public ColApi(String access_key) {
		super(access_key);
	}

	/**
	 * @param name
	 *            column的属性中文名称 string(45) 必选
	 * @param key
	 *            column存储的属性key string(45)必选，同一个geotable内的名字不能相同
	 * @param type
	 *            存储的值的类型 uint32 必选，枚举值1:Int64, 2:double, 3:string, 4:在线图片url
	 * @param max_length
	 *            最大长度 uint32
	 *            最大值2048，最小值为1。当type为string该字段有效，此时该字段必填。此值代表utf8的汉字个数，不是字节个数
	 * @param default_value
	 *            默认值 string(45) 设置默认值
	 * @param is_sortfilter_field
	 *            是否检索引擎的数值排序筛选字段 uint32 必选 1代表是，0代表否。设置后，在请求
	 *            LBS云检索时可针对该字段进行排序。该字段只能为int或double类型，最多设置15个
	 * @param is_search_field
	 *            是否检索引擎的文本检索字段 uint32 必选
	 *            1代表支持，0为不支持。只有type为string时可以设置检索字段，只能用于字符串类型的列且最大长度不能超过512个字节
	 * @param is_index_field
	 *            是否存储引擎的索引字段 uint32 必选 用于存储接口查询:1代表支持，0为不支持 注：is_index_field=1
	 *            时才能在根据该列属性值检索时检索到数据
	 * @param is_unique_field
	 *            是否云存储唯一索引字段，方便更新，删除，查询 uint32
	 *            可选，1代表是，0代表否。设置后将在数据创建和更新时进行该字段唯一性检查，并可以以此字段为条件进行数据的更新、删除和查询。
	 *            最多设置1个
	 * @param geotable_id
	 *            所属于的geotable_id string(50)
	 * @param ak
	 *            用户的访问权限key string(50) 必选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * 
	 * @return
	 */
	public Status<String> create(String sn, Col col) {
		Map<String, Object> params = col.def(sn);
		params.put("ak", this.getAccess_key());
		String ret = Https.of().post(URL_CREATE, params).readString();
		return Status.as(ret);
	}
	
	

}
