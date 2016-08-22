package cn.cerestech.middleware.baidu.map.lbs.api;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.middleware.baidu.map.lbs.entity.PoiResult;
import cn.cerestech.middleware.baidu.map.lbs.status.Status;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月18日
 */
public class PoiSearchApi extends BdApi {
	private static final String URL_SEARCH = "http://api.map.baidu.com/geosearch/v3/nearby";

	public PoiSearchApi(String access_key) {
		super(access_key);
	}
	
	public static PoiSearchApi of(String access_key){
		return new PoiSearchApi(access_key);
	}

	/**
	 * @param ak
	 *            access_key string(50) 字符串 必须
	 * @param geotable_id
	 *            geotable主键 uint32 数字 必须
	 * @param q
	 *            检索关键字 string(45) 任意汉字或数字，英文字母，可以为空字符 可选
	 * @param location
	 *            检索的中心点 string(25) 逗号分隔的经纬度 必须 样例：116.4321,38.76623
	 * 
	 * @param coord_type
	 *            坐标系 uint32 数字 可选 3代表百度经纬度坐标系统 4代表百度墨卡托系统
	 * 
	 * @param radius
	 *            检索半径 uint32 单位为米，默认为1000 可选 样例：500
	 * 
	 * @param tags
	 *            标签 string(45) 空格分隔的多字符串 可选 样例：美食 小吃
	 * 
	 * @param sortby
	 *            排序字段 string ”分隔的多个检索条件。
	 *            格式为sortby={key1}:value1|{key2:val2|key3:val3}。 最多支持16个字段排序
	 *            {keyname}:1 升序 {keyname}:-1 降序 以下keyname为系统预定义的： distance 距离排序
	 *            weight 权重排序
	 * 
	 *            可选 默认为按weight排序 如果需要自定义排序则指定排序字段 样例：按照价格由便宜到贵排序 sortby=price:1
	 * 
	 * @param filter
	 *            过滤条件 string(50) 竖线分隔的多个key-value对 key为筛选字段的名称(存储服务中定义)
	 *            支持连续区间或者离散区间的筛选： a:连续区间 key:value1,value2 b:离散区间
	 *            key:[value1,value2,value3,...]
	 * 
	 *            可选 样例: a:连续区间 样例：筛选价格为9.99到19.99并且生产时间为2013年的项
	 *            price:9.99,19.99|time:2012,2012 b:离散区间
	 *            筛选价格为8,9,13，并且生产时间为2013年的项 price:[8,9,13]|time:2012,2012
	 *            注：符号为英文半角中括号
	 * 
	 * @param page_index
	 *            分页索引 uint32 当前页标，从0开始 可选 默认为0
	 * 
	 * @param page_size
	 *            分页数量 uint32 当前页面最大结果数 可选 默认为10，最多为50
	 * 
	 * @param callback
	 *            回调函数 string(20) js回调函数 可选
	 * @param sn
	 *            用户的权限签名 string(50) 可选
	 * @return
	 */
	public <T extends PoiResult> Status<List<T>> search(Class<T> clzz, long geotable_id, int radius, double lng, double lat, int page_index,
			int page_size, String sn) {
		if (radius <= 0) {
			radius = 1000;
		}
		Map<String, String> params = Maps.newHashMap();
		params.put("ak", this.getAccess_key());
		params.put("geotable_id", geotable_id + "");
		params.put("page_index", page_index + "");
		params.put("page_size", page_size + "");
		params.put("sn", Strings.nullToEmpty(sn));
		params.put("radius", radius + "");
		params.put("location", lng + "," + lat);

		String ret = Https.of().get(URL_SEARCH, params).readString();
		
		Status<List<T>> s = Status.as(ret);
		if (s.isSuccess()) {
			String prst = s.jsonValue("contents");
			System.out.println(prst);
			if (!Strings.isNullOrEmpty(prst)) {
				List<T> datas = Jsons.from(prst).asList().stream().map(json->json.to(clzz)).collect(Collectors.toList());
				s.setObject(datas);
			}
		}
		return s;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String access_key = "Lze0SUAqOAebwUuNP0TEIf43";
		PoiSearchApi s = new PoiSearchApi(access_key);
		Status<List<PoiResult>> res = s.search(PoiResult.class, 142975L, 5000, 104.070641, 30.662552, 0, 3, "");
		List<PoiResult> p = res.getObject();
		PoiResult dd = p.get(0);

		System.out.println(dd.getGeotable_id());

	}

}
