package cn.cerestech.framework.support.mp.mpapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.http.entity.ByteArrayEntity;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;
import com.google.gson.JsonObject;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.http.Https.Response;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.entity.base.Status;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月23日
 */
public class OthersAPI extends API{
	public OthersAPI(String appid, String appsecret) {
		super(appid, appsecret);
	}
	
	public static OthersAPI of(String appid, String appsecret){
		return new OthersAPI(appid, appsecret);
	}
	
	/**
	 * 生成二维码图片
	 * @param server_access_token
	 * @param scene_str: 字符串形式的
	 * @param 1 ~ 10W 的整型数据
	 * @param 是否是临时的二维码
	 * POST: {"expire_seconds": 604800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 * POST: {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 * POST: {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
	 * @return
	 */
	public <T> Status<T> createQrImg(String server_access_token, String scene_str, Integer scene_id, boolean isTmp, Long tmpExpiredTimeSeconds){
		String action_name = "";
		if(isTmp){
			Long id = scene_str != null ?Longs.tryParse(scene_str): null;
			scene_str = "";
			if(scene_id == null && id != null){
				scene_id = id.intValue();
			}
			
			action_name =  "QR_SCENE";
			if(scene_id == null){
				return new Status<T>(Status.ABSENT_CODE, "参数scene_id为空", null);
			}
		}else{
			if(scene_id != null){
				action_name = "QR_LIMIT_SCENE";
			}else if(!Strings.isNullOrEmpty(scene_str)){
				action_name = "QR_LIMIT_STR_SCENE";
			}
			
			if(scene_id == null && Strings.isNullOrEmpty(scene_str)){
				return new Status<T>(Status.ABSENT_CODE, "参数scene_str 和 scene_id都为空", null);
			}
		}
		
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+server_access_token;
		JsonObject json = new JsonObject();
		json.addProperty("action_name", action_name);
		if(isTmp &&  tmpExpiredTimeSeconds != null){
			json.addProperty("expire_seconds", tmpExpiredTimeSeconds);
		}
		
		JsonObject action = new JsonObject();
		
		JsonObject scene = new JsonObject();
		if(!Strings.isNullOrEmpty(scene_str)){
			scene.addProperty("scene_str", scene_str);
		}else if(scene_id != null){
			scene.addProperty("scene_id", scene_id);
		}
		action.add("scene", scene);
		json.add("action_info", action);
		
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(json).disableUnicode().toJson().getBytes())).readString();
		
		Status<T> status = Status.as(ret);
		
		return status;
	}
	/**
	 * 创建临时二维码
	 * @param server_access_token
	 * @param scene_id
	 * @param expired_times
	 * @return
	 */
	public <T> Status<T> createTmpQr(String server_access_token, int scene_id, Long expired_times){
		return this.createQrImg(server_access_token, null, scene_id, true, expired_times);
	}
	
	public <T> Status<T> crateQr(String server_access_token, String scene_str, Integer or_scene_id){
		return this.createQrImg(server_access_token, scene_str, or_scene_id, false, null);
	}
	
	
	/**
	 * 获取二维码图片
	 * @param arimg_ticket
	 * @param filePath 图片存储位置,将图片保存在哪个位置
	 * @return
	 */
	public Status<String> getQrImg(String qrimg_ticket, String filePath){
		String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+qrimg_ticket;
		try {
			Response https = Https.of().get(url);
			if(https.success()){
				https.readFile(new FileOutputStream(new File(filePath)));
			}else{
				return new Status<String>(Status.ABSENT_CODE, "http请求失败:"+https.httpCode(), null);
			}
		} catch (FileNotFoundException e) {
			return new Status<String>(Status.ABSENT_CODE, "创建文件失败！！", null);
		};
		return new Status<String>(0).setObject(filePath);
	}
	
	/**
	 * 长短链接生成
	 * @param server_access_token
	 * @param long_url
	 * @return
	 */
	public Status<String> shorUrl(String server_access_token, String src_url){
		String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token="+server_access_token;
		JsonObject json = new JsonObject();
		json.addProperty("long_url", src_url);
		json.addProperty("action", "long2short");
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(json).disableUnicode().toJson().getBytes())).readString();
		Status<String> status = Status.as(ret);
		if(status.isSuccess()){
			status.setObject(status.jsonValue("short_url"));
		}
		return status;
	}
	
	
	public static void main(String[] args) {
		String appid = "wx9d02e7a1789151b2";
    	String appsecret = "6b96902b4fff22b974dc136277462509";
    	String at = "-IIshEVxM1nOsGWEN8Au0tfp37CHU3E2RKxVeBqdFHHf1QC-i3VrjiZ8rruXKWm6vYk-iq8LQE9PV5AIBjndrrfGyyTQeSdQeQZqOrkIpfZqzUFyxLb6Jz1E7bSWbNz2ULHcAIAHYU";
    	System.out.println(OthersAPI.of(appid, appsecret).shorUrl(at, "http://www.baidu.com").asString());
	}
}
