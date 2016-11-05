package cn.cerestech.framework.support.mp.msg.client.initiative.templatemsg;

import java.util.Map;

import com.beust.jcommander.internal.Maps;

import cn.cerestech.framework.core.json.Jsons;

/**
 * 模板消息
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月4日
 */
public class MpTemplateMsg {
	private String touser;
	private String template_id;
	private String url;
	private Map<String, ParamDesc> data;
	public void addParam(String fieldName, ParamDesc v){
		if(data == null){
			data = Maps.newHashMap();
		}
		data.put(fieldName, v);
	}
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Map<String, ParamDesc> getData() {
		return data;
	}
	public void setData(Map<String, ParamDesc> data) {
		this.data = data;
	}
	public String reply(){
		return Jsons.from(this).disableUnicode().toJson();
	}
	
	public static class ParamDesc{
		String value;
		String color;
		public ParamDesc(String v, String c){
			this.value = v;
			this.color = c;
		}
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
	}
}
