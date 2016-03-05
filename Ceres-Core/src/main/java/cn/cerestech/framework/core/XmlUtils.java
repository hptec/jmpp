package cn.cerestech.framework.core;


import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cn.cerestech.framework.core.json.Jsons;

public class XmlUtils {
	
	public static Map<String, Object> xmlToMap(Node node, boolean ignoreRoot){
		Map<String, Object> map = Maps.newHashMap();
		
		String nodeName = node.getNodeName();
		NodeList children = node.getChildNodes();
		Map<String, Object> parameters = Maps.newHashMap();
		NamedNodeMap ps = node.getAttributes();
		if(ps != null){
			for (int i = 0; i < ps.getLength(); i++) {
				parameters.put(ps.item(i).getNodeName(), ps.item(i).getNodeValue());
			}
		}
		
		if(children.getLength() == 1 && (children.item(0).getNodeType() == 3 || children.item(0).getNodeType() == 4)){
			map.put(nodeName, children.item(0).getNodeValue());
			map.putAll(parameters);
			return map;
		}else{
			for (int i = 0; i < children.getLength(); i++) {
				parameters.putAll(xmlToMap(children.item(i),false));
			}
		}
		if(ignoreRoot){
			map = parameters;
		}else{
			map.put(nodeName, parameters);
		}
		
		return map;
	}
	
	public static Map<String, Object> xmlToMap(String xml, boolean ignoreRoot){
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(new ByteArrayInputStream(xml.getBytes()));
			Map<String, Object> map = xmlToMap(dom.getDocumentElement(),ignoreRoot);
			return map;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T xmlToObject(Class<T> cls , String xml, boolean ignoreRoot){
		Map<String, Object> ret = xmlToMap(xml, ignoreRoot);
		String json = Jsons.toJson(ret, false);
		T t = Jsons.fromJson(json, TypeToken.of(cls));
		
		return t;
	}
	
	public static String jsonObjectToXml(JsonElement jele){
		StringBuffer sb = new StringBuffer();
		
		if(jele.isJsonPrimitive()){//纯键值对
			JsonPrimitive jp = jele.getAsJsonPrimitive();
			sb.append(jp.getAsString());
			return sb.toString();
		}else if(jele.isJsonObject()){
			JsonObject it = jele.getAsJsonObject();
			it.entrySet().forEach(itm->{
				sb.append("<").append(itm.getKey()).append(">");
				sb.append(jsonObjectToXml(itm.getValue()));
				sb.append("</").append(itm.getKey()).append(">");
			});
		}else if(jele.isJsonArray()){
			JsonArray array = jele.getAsJsonArray();
			array.forEach(itm->{
				sb.append(jsonObjectToXml(itm));
			});
		}
		
		return sb.toString();
	}
	
	public static <T> String objectToXml(T t, String root){
		if(StringUtils.isBlank(root)){
			root = "xml";
		}
		StringBuffer sb = new StringBuffer("");
		
		sb.append("<"+root+">").append(jsonObjectToXml(Jsons.from(Jsons.toJson(t, false)))).append("</"+root+">");
		return sb.toString();
	}
}
