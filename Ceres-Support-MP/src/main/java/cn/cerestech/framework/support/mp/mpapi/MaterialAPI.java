package cn.cerestech.framework.support.mp.mpapi;

import java.io.File;
import java.util.Map;

import org.apache.http.entity.ByteArrayEntity;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.entity.base.MpMaterial;
import cn.cerestech.framework.support.mp.entity.base.MpNewsMaterial;
import cn.cerestech.framework.support.mp.entity.base.Status;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月22日
 */
public class MaterialAPI extends API{
	/*
	 * 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
	 */
	public static enum MaterialType{
		IMAGE("image","图片"),
		VOICE("voice","语音"),
		VIDEO("video","视频"),
		THUMB("thumb","缩略图")
		;
		private String key, desc;
		private MaterialType(String key, String desc){
			this.key = key;
			this.desc = desc;
		}
		public String key(){
			return this.key;
		}
		public String desc(){
			return this.desc;
		}
	}

	public MaterialAPI(String appid, String appsecret) {
		super(appid, appsecret);
	}
	
	public static MaterialAPI of(String appid, String appsecret) {
		return new MaterialAPI(appid, appsecret);
	}
	
	
	/**
	 * 创建临时素材(微信服务器3天后自动删除)
	 * @param server_access_token
	 * @param file
	 * @param type
	 * @return
	 */
	public Status<MpMaterial> createTemp(String server_access_token, File file, MaterialType type){
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token="+server_access_token+"&type="+type.key();
		
		String ret = Https.of().upload(url, null, "media", file).readString();//{"type":"TYPE","media_id":"MEDIA_ID","created_at":123456789}
		
		Status<MpMaterial> status = Status.as(ret);
		if(status.isSuccess()){
			MpMaterial m = Jsons.from(status.asJson()).to(MpMaterial.class);
			status.setObject(m);
		}
		return status;
	}
	/**
	 * 创建永久性素材
	 * @param server_access_token
	 * @param file
	 * @param type
	 * @param video_title: 上传视频素材时有效
	 * @param video_desc: 上传视频素材有效
	 * @return
	 */
	public Status<MpMaterial> create(String server_access_token, File file, MaterialType type, String video_title, String video_desc){
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token="+server_access_token;
		Map<String, Object> params = Maps.newHashMap();
		Map<String, Object> p = Maps.newHashMap();
		if(type.equals(MaterialType.VIDEO)){
			p.put("title", video_title);
			p.put("introduction", video_desc);
			params.put("description", p);
		}
		String ret = Https.of().upload(url, params, "media", file).readString();
		
		Status<MpMaterial> status = Status.as(ret);
		if(status.isSuccess()){
			MpMaterial m = Jsons.from(status.asJson()).to(MpMaterial.class);
			status.setObject(m);
		}
		return status;
	}
	
	/**
	 * 获取永久素材
	 * @param server_access_token
	 * @param media_id
	 * @return
	 */
	public Status<Map<String, String>> get(String server_access_token, String media_id){
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token="+server_access_token;
		JsonObject get = new JsonObject();
		get.addProperty("media_id", media_id);
		
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(get).disableUnicode().toJson().getBytes())).readString();
		Status<Map<String, String>> status = Status.as(ret);
		if(status.isSuccess()){
			Map<String, String> m = Jsons.from(status.asJson()).to(new TypeToken<Map<String, String>>(){});
			status.setObject(m);
		}
		
		return status;
	}
	/**
	 * 批量获取微信号的所有素材信息
	 * @param server_access_token
	 * @param offset: 偏移量
	 * @param type: 素材类型
	 * @param count 1 - 20 之间
	 * @return
	 */
	public Status getBatch(String server_access_token, MaterialType type, int offset, int count){
		String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+server_access_token;
		Map<String, Object> params = Maps.newHashMap();
		params.put("type", type.key());
		params.put("offset", offset);
		params.put("count", count);
		
		String res = Https.of().post(url, new ByteArrayEntity(Jsons.from(params).disableUnicode().toJson().getBytes())).readString();
		Status status = Status.as(res);
		if(status.isSuccess()){
			
		}
		
		return status;
	}
	/**
	 * 删除永久素材
	 * @param server_access_token
	 * @param media_id
	 * @return
	 */
	public Status del(String server_access_token, String media_id){
		String url = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token="+server_access_token;
		JsonObject m = new JsonObject();
		m.addProperty("media_id", media_id);
		String res = Https.of().post(url, new ByteArrayEntity(m.toString().getBytes())).readString();
		Status status = Status.as(res);
		return status;
	}
	
	/**
	 * 上传图文消息文本中的图片，没有5000 的限制，图文消息中的内部图片
	 * 最大不超过1M
	 * @param server_access_token
	 * @param file
	 * @return 返回的Material 中只有 URL值
	 */
	public Status<MpMaterial> createNewsImg(String server_access_token, File file){
		String url = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token="+server_access_token;
		String ret = Https.of().upload(url,null, "media", file).readString();
		Status<MpMaterial> status = Status.as(ret);
		if(status.isSuccess()){
			MpMaterial m = Jsons.from(ret).to(MpMaterial.class);
			status.setObject(m);
		}
		return status;
	}
	
	/**
	 * 新增图文素材
	 * @param materials: 如果只传入一个表示单图文消息，如果多个表示多图文消息
	 * @return： 返回消息中包含值 media_id 其他不包含
	 */
	public Status<MpMaterial> createNews(String server_access_token, MpNewsMaterial... materials){
		String url = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token="+server_access_token;
		Map<String, Object> params = Maps.newHashMap();
		params.put("articles", materials);
		
		String res = Https.of().post(url, new ByteArrayEntity(Jsons.from(params).disableUnicode().toJson().getBytes())).readString();
		
		Status<MpMaterial> status = Status.as(res);
		if(status.isSuccess()){
			MpMaterial m = Jsons.from(res).to(MpMaterial.class);
			status.setObject(m);
		}
		return status;
	}
	
	/**
	 * 修改永久图文素材
	 * @return
	 */
	public Status updateNews(String server_access_token, String news_media_id, MpNewsMaterial material, int index){
		String url = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token="+server_access_token;
		Map<String, Object> params = Maps.newHashMap();
		params.put("media_id", news_media_id);
		params.put("articles", material);
		params.put("index", index);
		
		String res = Https.of().post(url, new ByteArrayEntity(Jsons.from(params).disableUnicode().toJson().getBytes())).readString();
		
		Status status = Status.as(res);
		return status;
	}
	
	/**
	 * 返回永久性素材数量
	 * @param server_access_token
	 * @return Map<String, Integer> 
	 *		 <"voice_count":COUNT>
	 *		 <"video_count":COUNT>
	 *		 <"image_count":COUNT>
	 *		 <"news_count":COUNT>
		}
	 */
	public Status<Map<String, Integer>> count(String server_access_token){
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token="+server_access_token;
		String res = Https.of().post(url).readString();
		Status<Map<String, Integer>> status = Status.as(res);
		if(status.isSuccess()){
			Map<String, Integer> ct = Jsons.from(status.asJson()).to(new TypeToken<Map<String, Integer>>(){});
			status.setObject(ct);
		}
		return status;
	}
	
	public static void main(String[] args) {
		String appid = "wx9d02e7a1789151b2";
    	String appsecret = "6b96902b4fff22b974dc136277462509";
    	
//		System.out.println(AccessTokenAPI.of(appid, appsecret).accessToken().asString());
    	String at = "PAD-DQJsv-GtwiOBX6h8TLqa65eEp9vYWUNa5R6cSLbsjOi0sZOODi-FGRLlp6msVe7KkY5Pbndenitk09u05HoAeIlcZZCvrWMs9dBwqH9Z_6kfUTPx7dtZrlk9sn5LJFKeABAOKP";
    	//{"type":"image","media_id":"RWbzvZGAOmgKs8UuLEZpezSorNqTjSh9gKrKWqcCwAdk5Nq5qXNhi2rpQQKWk-3_","created_at":1461322507}
    	//{"type":"image","media_id":"0Z-pSW08kjqg1ilRf22uz9g-t2QdN5OwYYWXQaXyvr05b3j9-NO05R-vVNuQReUs","created_at":1461321870}
    	//{"type":"image","media_id":"eQpWjqfiEUctBeqMRqJLWz2kCiF7izwb20ww7hudidDNNro-dqJTy_l-Wj-umW-L","created_at":1461321552}
    	//System.out.println(Jsons.from(MaterialAPI.of(appid, appsecret).createTemp(at, new File("/Users/bird/Desktop/logo.png"), MaterialType.IMAGE).getObject()).toPrettyJson());
    	
//    	System.out.println(Https.of().upload("http://www.e7db.com/$$ceres_sys/console/base/res/upload?pp=aaa", new File("/Users/bird/Desktop/logo.png")).readString());
    	
    	//{"media_id":"XTXBqJrWgMGdaPChDegApjST87ImtoBNCkmw5TkUcJQ","url":"https:\/\/mmbiz.qlogo.cn\/mmbiz\/JUJOXia6UuFbQSo0icwSnbIbLaQ3PnkD3YMF7D9wDzKJTsoW77tkyyGDXnzaY7y6scVYwwXyWiaMROIykwBgSRQ6w\/0?wx_fmt=png"}
//    	System.out.println(MaterialAPI.of(appid, appsecret).create(at, new File("/Users/bird/Desktop/logo.png"), MaterialType.IMAGE, null, null).asString());
    	
    	//{"media_id":"XTXBqJrWgMGdaPChDegApv6aSZgiQht01KTpTePBBq4"}视频素材
    	//System.out.println(MaterialAPI.of(appid, appsecret).create(at, new File("/Users/bird/Desktop/22.mp4"), MaterialType.VIDEO, "测试标题", "这是视频描述").asString());
    	
//    	System.out.println(MaterialAPI.of(appid, appsecret).createNewsImg(at, new File("/Users/bird/Desktop/logo.png")).asString());
    	
    	//{"title":"这是视频描述","description":"这是视频描述","down_url":"http:\/\/103.7.28.105\/vweixinp.tc.qq.com\/1007_1b7f8d61294f4acab7552df32b707122.f10.mp4?vkey=5B3266B58EC531F0D4AF396E2184026E96CFD3FB2C613A53C5AE6DE66E3D08524938CC08D84A2239&sha=0&save=1"}
//    	System.out.println(MaterialAPI.of(appid, appsecret).get(at, "XTXBqJrWgMGdaPChDegApv6aSZgiQht01KTpTePBBq4").asString());
    	
//    	System.out.println(MaterialAPI.of(appid, appsecret).del(at, "XTXBqJrWgMGdaPChDegApv6aSZgiQht01KTpTePBBq4").asString());
    	
//    	System.out.println(MaterialAPI.of(appid, appsecret).count(at).asString());
    	
    	System.out.println(MaterialAPI.of(appid, appsecret).getBatch(at, MaterialType.VIDEO, 0, 20).asString());
    	
	}
	
}
