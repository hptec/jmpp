package cn.cerestech.framework.support.mp.build;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.entity.base.MpMenu;

/**
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月21日
 */
public class MpMenuBuilder {
	private List<MpMenu> root;
	private Spec matchrule;
	
	private MpMenuBuilder(){
		this.root = Lists.newArrayList();
	}
	
	public static MpMenuBuilder of(){
		return new MpMenuBuilder();
	}
	
	public MpMenuBuilder addBottom(MpMenu m){
		this.root.add(m);
		return this;
	}
	
	public MpMenu get(int idx){
		return root.get(idx);
	}
	
	public String builder(){
		JsonObject container = new JsonObject();
		container.add("button", Jsons.from(this.root).getRoot());
		return Jsons.from(container).disableUnicode().toPrettyJson();
	}
	
	public String builderSpec(Spec spec){
		if(spec == null){
			spec = new Spec();
		}
		this.matchrule = spec;
		JsonObject container = new JsonObject();
		container.add("button", Jsons.from(this.root).getRoot());
		container.add("matchrule", Jsons.from(this.matchrule).getRoot());
		
		return Jsons.from(container).disableUnicode().toPrettyJson();
	}
	
	public static class Spec{
		private Long group_id;
		private String sex;
		private String country;
		private String province;
		private String city;
		private String client_platform_type;
		private String language;
		public Long getGroup_id() {
			return group_id;
		}
		public Spec setGroup_id(Long group_id) {
			this.group_id = group_id;
			return this;
		}
		public String getSex() {
			return sex;
		}
		public Spec setSex(String sex) {
			this.sex = sex;
			return this;
		}
		public String getCountry() {
			return country;
		}
		public Spec setCountry(String country) {
			this.country = country;
			return this;
		}
		public String getProvince() {
			return province;
		}
		public Spec setProvince(String province) {
			this.province = province;
			return this;
		}
		public String getCity() {
			return city;
		}
		public Spec setCity(String city) {
			this.city = city;
			return this;
		}
		public String getClient_platform_type() {
			return client_platform_type;
		}
		public Spec setClient_platform_type(String client_platform_type) {
			this.client_platform_type = client_platform_type;
			return this;
		}
		public String getLanguage() {
			return language;
		}
		public Spec setLanguage(String language) {
			this.language = language;
			return this;
		}
	}
	
	
	public static void main(String[] args) {
		String res = MpMenuBuilder.of()
		.addBottom(MpMenu.top("张三").addSub(MpMenu.click("按钮一", "aaa")).addSub(MpMenu.click("按钮二", "bbb")).addSub(MpMenu.click("按钮三", "ccc")))
		.addBottom(MpMenu.top("李四").addSub(MpMenu.click("按钮一", "aaa")).addSub(MpMenu.click("按钮二", "bbb")).addSub(MpMenu.click("按钮三", "ccc")))
		.addBottom(MpMenu.top("王五").addSub(MpMenu.click("按钮一", "aaa")).addSub(MpMenu.click("按钮二", "bbb")).addSub(MpMenu.click("按钮三", "ccc"))).builderSpec(null);
		System.out.println(res);
	}
}
