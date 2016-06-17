package cn.cerestech.middleware.baidu.map.lbs.entity;

import java.util.Map;

import com.google.gson.reflect.TypeToken;

import cn.cerestech.framework.core.json.Jsons;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月16日
 */
public class Poi{
	private String title;//	poi名称	string(256)	可选
	private String address;//	地址	string(256)	可选
	private String tags;//	tags	string(256)	可选
	private double latitude;//	用户上传的纬度	double	必选
	private double longitude;//	用户上传的经度	double	必选
	private double coord_type;//	用户上传的坐标的类型	uint32	必选 1：GPS经纬度坐标 2：国测局加密经纬度坐标 3：百度加密经纬度坐标 4：百度加密墨卡托坐标
	private long geotable_id;//	记录关联的geotable的标识	string(50)	必选，加密后的id
//	ak	用户的访问权限key	string(50)	必选
//	sn	用户的权限签名	string(50)	可选
//	{column key}	用户在column定义的key/value对	开发者自定义的类型（string、int、double）	唯一索引字段必选，且需要保证唯一，否则会创建失败
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getCoord_type() {
		return coord_type;
	}
	public void setCoord_type(double coord_type) {
		this.coord_type = coord_type;
	}
	public long getGeotable_id() {
		return geotable_id;
	}
	public void setGeotable_id(long geotable_id) {
		this.geotable_id = geotable_id;
	}
	
	public Map<String, Object> toParams(){
		Map<String, Object> params = Jsons.from(this).to(new TypeToken<Map<String, Object>>() {});
		return params;
	}
}
