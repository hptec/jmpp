package cn.cerestech.framework.support.mp.mpapi;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Longs;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.entity.MpUser;
import cn.cerestech.framework.support.mp.entity.base.MpGroup;
import cn.cerestech.framework.support.mp.entity.base.Status;

public class UserAPI extends API{

	public UserAPI(String appid, String appsecret) {
		super(appid, appsecret);
	}
	
	public static UserAPI of(String appid, String appsecret){
		return new UserAPI(appid, appsecret);
	}
	
	public Status<MpUser> get(String access_token, String open_id){
		StringBuffer url = new StringBuffer();
		url.append("https://api.weixin.qq.com/cgi-bin/user/info?");
		url.append("access_token=" + access_token);
		url.append("&openid=" + open_id);
		url.append("&lang=zh_CN");
		
		String res = Https.of().post(url.toString()).readString();
		
		Status<MpUser> statusReturn = Status.as(res);
		if (statusReturn.isSuccess()) {
			MpUser user = Jsons.from(res).to(MpUser.class);
			statusReturn.setObject(user);
		}
		return statusReturn;
	}
	
	/**
	 * 批量获取openid
	 * @param server_access_token
	 * @return， 最后一条为next_openid， 当粉丝数量超过10000 的时候作为凭证传入
	 * 
	 */
	public Status<List<String>> batchOpenides(String server_access_token, String next_openid){
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+server_access_token+"&next_openid="+Strings.nullToEmpty(next_openid);
		String ret = Https.of().post(url).readString();
		
		Status<List<String>> status = Status.as(ret);
		if(status.isSuccess()){//{"total":2,"count":2,"data":{"openid":["","OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}
			if(Strings.isNullOrEmpty(status.jsonValue("data"))){
				status.setObject(Lists.newArrayList());
			}else{
				JsonObject json = Jsons.from(status.jsonValue("data")).getRoot().getAsJsonObject();
				if(json.has("openid")){
					List<String> openides = Jsons.from(json.get("openid")).to(new TypeToken<List<String>>(){});
					status.setObject(openides);
				}
			}
		}
		return status;
	}
	
	
	/**
	 * 批量拉取用户信息，已经关注的用户才可以
	 * 
	 * @param appid
	 * @param appsecret
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public Status<List<MpUser>> batchGet(String access_token, List<String> openids) throws UnsupportedEncodingException {
		if (openids == null || openids.isEmpty() || openids.size() > 100) {
			throw new IllegalArgumentException("获取粉丝数量太多，不能超过100");
		}
		StringBuffer url = new StringBuffer("https://api.weixin.qq.com/cgi-bin/user/info/batchget?");
		url.append("access_token=" + access_token);
		
		JsonObject root = new JsonObject();
		JsonArray lst = new JsonArray();
		root.add("user_list", lst);
		openids.forEach(oid -> {
			JsonObject mp = new JsonObject();
			mp.addProperty("openid", oid);
			mp.addProperty("lang", "zh-CN");
			lst.add(mp);
		});
		System.out.println(root.toString());
		HttpEntity entity = new StringEntity(Jsons.from(root).disableUnicode().toJson());
		String ret = Https.of().post(url.toString(), entity).readString();
		
		Status<List<MpUser>> statusReturn = Status.as(ret);
		if (statusReturn.isSuccess()) {
			String mplst = statusReturn.jsonValue("user_info_list");
			List<MpUser> retList = Jsons.from(mplst).to(new TypeToken<List<MpUser>>() {});
			statusReturn.setObject(retList);
		}
		return statusReturn;
	}
	
	/**
	 * 创建分组
	 * @param server_access_token
	 * @param gp_names
	 * @return
	 */
	public Status<MpGroup> createGroup(String server_access_token, String gp_names){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token="+server_access_token;
		JsonObject group = new JsonObject();
		JsonObject gp = new JsonObject();
		gp.addProperty("name", gp_names);
		group.add("group", gp);
		String gpStr = Jsons.from(group).disableUnicode().toJson();
		String ret = Https.of().post(url, new ByteArrayEntity(gpStr.getBytes())).readString();
		Status<MpGroup> status = Status.as(ret);
		if(status.isSuccess()){
			MpGroup g = Jsons.from(status.jsonValue("group")).to(MpGroup.class);
			status.setObject(g);
		}
		
		return status;
	}
	
	/**
	 * 更新group
	 * @param server_access_token
	 * @param group_id
	 * @param name
	 * @return
	 */
	public Status<MpGroup> updateGroup(String server_access_token, long group_id, String name){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token="+server_access_token;
		JsonObject groups = new JsonObject();
		JsonObject gp = new JsonObject();
		gp.addProperty("id", group_id);
		gp.addProperty("name", name);
		groups.add("group", gp);
		
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(groups).disableUnicode().toJson().getBytes())).readString();
		
		Status<MpGroup> status = Status.as(ret);
		if(status.isSuccess()){
			MpGroup g = new MpGroup();
			g.setId(group_id);
			g.setName(name);
			status.setObject(g);
		}
		return status;
	}
	
	/**
	 * 删除分组
	 * @param server_access_token
	 * @param group_id
	 * @return
	 */
	public Status delGroup(String server_access_token, long group_id){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token="+server_access_token;
		JsonObject group = new JsonObject();
		JsonObject gp = new JsonObject();
		gp.addProperty("id", group_id);
		group.add("group", gp);
		
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(group).disableUnicode().toJson().getBytes())).readString();
		
		Status status = Status.as(ret);
		return status;
		
	}
	
	/**
	 * 查询当前微信所有分组
	 * @param server_access_token
	 * @return
	 */
	public Status<List<MpGroup>> groups(String server_access_token){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token="+server_access_token;
		String ret = Https.of().post(url).readString();
		
		Status<List<MpGroup>> status = Status.as(ret);
		
		if(status.isSuccess()){//groups
			List<MpGroup> groups = Jsons.from(status.jsonValue("groups")).to(new TypeToken<List<MpGroup>>(){});
			status.setObject(groups);
		}
		return status;
	}
	
	/**
	 * 查询用户在哪个分组
	 * @param server_access_token
	 * @param openid
	 * @return
	 */
	public Status<MpGroup> inGroup(String server_access_token, String openid){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token="+server_access_token;
		JsonObject oids = new JsonObject();
		oids.addProperty("openid", openid);//{"openid":"od8XIjsmk6QdVTETa9jLtGWA6KBc"}
		
		String ret = Https.of().post(url, new ByteArrayEntity(oids.toString().getBytes())).readString();
		
		Status<MpGroup> status = Status.as(ret);
		
		if(status.isSuccess()){
			Long id = Longs.tryParse(status.jsonValue("groupid"));
			if(id != null){
				MpGroup g = new MpGroup();
				g.setId(id);
				status.setObject(g);
			}
		}
		return status;
	}
	
	/**
	 * 
	 * @param server_access_token
	 * @param openid
	 * @param group_id
	 * @return
	 */
	public Status assignGroup(String server_access_token, String openid, long group_id){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token="+server_access_token;
		JsonObject agp = new JsonObject();
		agp.addProperty("openid", openid);
		agp.addProperty("to_groupid", group_id);
		
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(agp).disableUnicode().toJson().getBytes())).readString();
		Status status = Status.as(ret);
		
		return status;
	}
	
	/**
	 * 批量分配用户分组
	 * @param server_access_token
	 * @param openides
	 * @param group_id
	 * @return
	 */
	public Status assignGroupBatch(String server_access_token, Set<String> openides, long group_id){
		String url = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token="+server_access_token;
		Map<String, Object> post = Maps.newHashMap();
		post.put("openid_list", openides);
		post.put("to_groupid", group_id);
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(post).disableUnicode().toJson().getBytes())).readString();
		Status status = Status.as(ret);
		
		return status;
	}
	
	/**
	 * 设置用户备注名称
	 * @param server_access_token
	 * @param openid
	 * @param nick
	 * @return
	 */
	public Status assignNick(String server_access_token , String openid, String nick){
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token="+server_access_token;
		JsonObject post = new JsonObject();
		post.addProperty("openid", openid);
		post.addProperty("remark", nick);
		
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(post).disableUnicode().toJson().getBytes())).readString();
		
		Status status = Status.as(ret);
		return status;
	}
}
