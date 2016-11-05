package cn.cerestech.framework.support.mp.mpapi;

import java.util.List;
import java.util.Set;

import org.apache.http.entity.ByteArrayEntity;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.common.primitives.Longs;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.build.MpMenuBuilder;
import cn.cerestech.framework.support.mp.build.MpMenuBuilder.Spec;
import cn.cerestech.framework.support.mp.entity.base.MpMenu;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.enums.MpGender;

/**
 * 个性化菜单API
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月22日
 */
public class MenuSpecAPI extends API{
	public MenuSpecAPI(String appid, String appsecret) {
		super(appid, appsecret);
	}
	
	public static MenuSpecAPI of(String appid, String appsecret){
		return new MenuSpecAPI(appid, appsecret);
	}
	/**
	 * 创建特殊的菜单
	 * @param server_access_token
	 * @param spec_menu_str
	 * @return
	 */
	public Status<Long> create(String server_access_token, String spec_menu_str){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token="+server_access_token;
		
		String ret = Https.of().post(url, new ByteArrayEntity(spec_menu_str.getBytes())).readString();
		Status<Long> status = Status.as(ret);
		if(status.isSuccess()){
			Long id = Longs.tryParse(status.jsonValue("menuid"));
			status.setObject(id);
		}
		return status;
	}
	
	/**
	 * 查询用户显示什么样的菜单
	 * @param server_access_token
	 * @param openid_or_wxh: openid或者微信号
	 * @return
	 */
	public Status<List<MpMenu>> queryMpuserMenu(String server_access_token, String openid_or_wxh){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token="+server_access_token;
		JsonObject obj = new JsonObject();
		obj.addProperty("user_id", openid_or_wxh);
		String res = Https.of().post(url, new ByteArrayEntity(Jsons.from(obj).disableUnicode().toJson().getBytes())).readString();
		
		Status<List<MpMenu>> status = Status.as(res);
		
		if(status.isSuccess()){
			if(!Strings.isNullOrEmpty(status.jsonValue("menu"))){
				List<MpMenu> menues = Jsons.from(status.asJson().getAsJsonObject().get("menu").getAsJsonObject().get("button")).to(new TypeToken<List<MpMenu>>(){});
				status.setObject(menues);
			}
		}
		return status;
	}
	
	public Status delMenu(String server_access_token, long menuid){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token="+server_access_token;
		JsonObject menu = new JsonObject();
		menu.addProperty("menuid", menuid);
		String res = Https.of().post(url, new ByteArrayEntity(menu.toString().getBytes())).readString();
		Status status = Status.as(res);
		
		return status;
	}
	
	public static void main(String[] args) {
		String appid = "wx9d02e7a1789151b2";
    	String appsecret = "6b96902b4fff22b974dc136277462509";
    	
//		System.out.println(AccessTokenAPI.of(appid, appsecret).accessToken().asString());
    	String at = "b1oNcEQmjzVCWdWp1gJRew5NjtUiTYvCpmWoerL1gPOhxFZ-jHLRqnooE6HtG8f1Kfej2pwmNDY0RsyNUo3BC4Obj0Ii8vw7seRmTPUF6dFyW3gaqs5oIp9XOWrctjOrJKZfAGAUVK";
    	
    	Set<String> openides = Sets.newHashSet();
    	openides.add("otwEVt4vYaxpcOili5yTBCwn_8DA");
    	openides.add("otwEVt-T63dncpcxHkGsnIwTZePE");
    	String res = MpMenuBuilder.of()
    			.addBottom(MpMenu.top("张三").addSub(MpMenu.pic_photo_or_album("选择照片", "take_photo")).addSub(MpMenu.pic_sysphoto("拍照", "take_photo")).addSub(MpMenu.pic_weixin("微信相册", "take_album")).addSub(MpMenu.click("按钮三", "ccc")))
    			.addBottom(MpMenu.top("李四").addSub(MpMenu.click("按钮一", "aaa")).addSub(MpMenu.click("按钮二", "bbb")).addSub(MpMenu.click("按钮三", "ccc")))
    			.addBottom(MpMenu.top("王五").addSub(MpMenu.click("按钮一", "aaa")).addSub(MpMenu.click("按钮二", "bbb")).addSub(MpMenu.click("按钮三", "ccc"))).builderSpec(new Spec().setSex(MpGender.F.key()));
    			
//    	System.out.println(MenuSpecAPI.of(appid, appsecret).create(at, res).asString());//{"menuid":418399338}
    	
//    	System.out.println(MenuSpecAPI.of(appid, appsecret).delMenu(at, 418399338L).asString());
    	
    	System.out.println(Jsons.from(MenuSpecAPI.of(appid, appsecret).queryMpuserMenu(at, "otwEVt4vYaxpcOili5yTBCwn_8DA").getObject()).toPrettyJson());
    	
    	
    	
    	
	}
	
}
