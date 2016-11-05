package cn.cerestech.framework.support.mp.mpapi;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.entity.ByteArrayEntity;

import com.google.common.base.Strings;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.build.MpMenuBuilder;
import cn.cerestech.framework.support.mp.entity.base.MpMenu;
import cn.cerestech.framework.support.mp.entity.base.Status;

/**
 * 菜单API
 * 
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月21日
 */
public class MenuAPI extends API{
	public MenuAPI(String appid, String appsecret) {
		super(appid, appsecret);
	}
	
	public static MenuAPI of(String appid, String appsecret){
		return new MenuAPI(appid, appsecret);
	}
	
	/**
	 * 定义菜单并且返回状态码
	 * 
	 * @param accessToken
	 * @param menu
	 * @return
	 */
	public <T> Status<T> create(String server_access_token,final String menustr) {
		StringBuffer url = new StringBuffer("https://api.weixin.qq.com/cgi-bin/menu/create?");
		url.append("access_token=" + server_access_token);
		
		Status<T> statusReturn = Status.ABSENT;
		if (Strings.isNullOrEmpty(menustr)) {
			statusReturn.setMsg("菜单不能为空");
			return statusReturn;
		}
		ByteArrayEntity ent = new ByteArrayEntity(menustr.getBytes());
		String res = Https.of().post(url.toString(), ent).readString();
		statusReturn = Status.as(res);
		return statusReturn;
	}
	
	/**
	 * 返回当前公众号设置的菜单
	 * @param server_acces_token
	 * @return
	 */
	public Status<List<MpMenu>> cur(String server_acces_token){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+server_acces_token;
		
		String content = Https.of().post(url).readString();
		
		Status<List<MpMenu>> status = Status.as(content);
		
		if(status.isSuccess()){//获取成功
			JsonObject menu = status.asJson().getAsJsonObject();
			
			if(menu != null && menu.has("menu")){
				JsonObject m = menu.get("menu").getAsJsonObject();
				if(m != null && m.has("button")){
					List<MpMenu> mLst = Jsons.from(m.get("button")).to(new TypeToken<List<MpMenu>>(){});
					status.setObject(mLst);
				}
			}
		}
		
		return status;
	}
	
	/**
	 * 清除设置的菜单
	 * @param server_access_token
	 * @return
	 */
	public <T> Status<T> del(String server_access_token){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+server_access_token;
		String ret = Https.of().post(url).readString();
		return Status.as(ret);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String res = MpMenuBuilder.of()
		.addBottom(MpMenu.top("张三").addSub(MpMenu.pic_photo_or_album("选择照片", "take_photo")).addSub(MpMenu.pic_sysphoto("拍照", "take_photo")).addSub(MpMenu.pic_weixin("微信相册", "take_album")).addSub(MpMenu.click("按钮三", "ccc")))
		.addBottom(MpMenu.top("李四").addSub(MpMenu.click("按钮一", "aaa")).addSub(MpMenu.click("按钮二", "bbb")).addSub(MpMenu.click("按钮三", "ccc")))
		.addBottom(MpMenu.top("王五").addSub(MpMenu.click("按钮一", "aaa")).addSub(MpMenu.click("按钮二", "bbb")).addSub(MpMenu.click("按钮三", "ccc"))).builder();
		
		String appid = "wx9d02e7a1789151b2";
    	String appsecret = "6b96902b4fff22b974dc136277462509";
    	
//		System.out.println(AccessTokenAPI.of(appid, appsecret).accessToken().asString());
    	String at = "LIctHrwd_8ww63Bsa18AXwcH7E3TAaW_xO_5FLdXc0XXPJByiBwAe9zzPiGcM2sTADVqSuxzmTm7DXx1IVk8shS6QnzEeXZ84npxLUBTjJ_q5qro0YKI7R9w4dv3BwKlQGThAAALBO";
//    	
//    	System.out.println(MenuAPI.of(appid, appsecret).create(at, res).asString());
    	
//    	System.out.println(Jsons.from(MenuAPI.of(appid, appsecret).cur(at)).toPrettyJson());
//    	System.out.println(MenuAPI.of(appid, appsecret).del(at).asString());
	}
	
}
