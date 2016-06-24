package cn.cerestech.middleware.baidu.map.lbs.entity;
/**
 * POI 检索结果基本类
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年6月18日
 */
public class PoiResult {
	private String uid;//	数据id	String	
	private Long geotable_id;//	geotable_id	String	
	private String title;//	Poi名称	String	
	private String adderss;//	Poi地址	String	
	private String province;//	Poi所属省	String(20)	
	private String city;//	Poi所属城市	String(20)	
	private String district;//	Poi所属区	String(20)	 
	private double[] location;//	经纬度	Array	同nearby
	private int coord_type;//	坐标系定义	Int32	"3代表百度经纬度坐标系统 4代表百度墨卡托系统"
	private String tags;//coord_type	Poi的标签	String	
	private int distance;//	距离	Int32 单位 米	
	private int weight;//	权重	Int32	
//	{column}  型  	 自定义列	自定义类	自定义列/値，云存储未添加値时不返回
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Long getGeotable_id() {
		return geotable_id;
	}
	public void setGeotable_id(Long geotable_id) {
		this.geotable_id = geotable_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAdderss() {
		return adderss;
	}
	public void setAdderss(String adderss) {
		this.adderss = adderss;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}
	public int getCoord_type() {
		return coord_type;
	}
	public void setCoord_type(int coord_type) {
		this.coord_type = coord_type;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public double lat(){
		if(this.getLocation() != null && this.getLocation().length >0){
			return this.getLocation()[0];
		}
		return 0;
	}
	public double lng(){
		if(this.getLocation() != null && this.getLocation().length > 1){
			return this.getLocation()[1];
		}
		return 0;
	}
}
