package cn.cerestech.framework.support.mp.entity.base;

import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.mp.enums.MenuType;

/**
 * 微信菜单
 * 
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月21日
 */
public class MpMenu {
	private String name;
	private String type;
	private String url;
	private List<MpMenu> sub_button;
	private String key;
	private String media_id;
	
	private MpMenu(String name, MenuType type){
		this.name = name;
		if(type != null){
			this.type = type.key();
		}
	}
	
	public static MpMenu top(String name){
		return new MpMenu(name, null);
	}
	
	/**
	 * 点击后返回消息
	 * @param name
	 * @param key
	 * @return
	 */
	public static MpMenu click(String name, String key){
		MpMenu m = new MpMenu(name, MenuType.CLICK);
		m.setKey(key);
		return m;
	}
	
	/**
	 * 点击后跳转链接
	 * @param name
	 * @param url
	 * @return
	 */
	public static MpMenu view(String name, String url){
		MpMenu m = new MpMenu(name, MenuType.VIEW);
		m.setUrl(url);
		return m;
	}
	/**
	 * 点击后扫描二维码
	 * @return
	 */
	public static MpMenu scancode_push(String name, String key){
		MpMenu m = new MpMenu(name, MenuType.SCANCODE_PUSH);
		m.setKey(key);
		return m;
	}
	/**
	 * 点击后扫描二维码，扫码结束后显示等待消息的按钮
	 * @return
	 */
	public static MpMenu scancode_waitmsg(String name, String key){
		MpMenu m = new MpMenu(name, MenuType.SCANCODE_PUSH);
		m.setKey(key);
		return m;
	}
	/**
	 * 打开系统照相机牌照后发送照片给开发者
	 * @param name
	 * @return
	 */
	public static MpMenu pic_sysphoto(String name, String key){
		MpMenu m = new MpMenu(name, MenuType.PIC_SYSPHOTO);
		m.setKey(key);
		return m;
	}
	
	/**
	 * 从手机相册中选择或者牌照
	 * @param name
	 * @return
	 */
	public static MpMenu pic_photo_or_album(String name, String key){
		MpMenu m = new MpMenu(name, MenuType.PIC_PHOTO_OR_ALBUM);
		m.setKey(key);
		return m;
	}
	/**
	 * 从微信相册中选择照片
	 * @param name
	 * @return
	 */
	public static MpMenu pic_weixin(String name, String key){
		MpMenu m = new MpMenu(name, MenuType.PIC_WEIXIN);
		m.setKey(key);
		return m;
	}
	
	/**
	 * 选择位置发送给开发者
	 * 位置为用户手动选择的位置信息
	 * @param name
	 * @return
	 */
	public static MpMenu location_select(String name, String key){
		MpMenu m = new MpMenu(name, MenuType.LOCATION_SELECT);
		m.setKey(key);
		return m;
	}
	
	/**
	 * 点击后下发永久素材消息（除文本消息）
	 * @param name
	 * @param media_id
	 * @return
	 */
	public static MpMenu media_id(String name, String media_id){
		MpMenu m = new MpMenu(name, MenuType.MEDIA_ID);
		m.setMedia_id(media_id);
		return m;
	}
	/**
	 * 跳转图文永久素材URL
	 * @param name
	 * @param media_id： 永久图文素材ID
	 * @return
	 */
	public static MpMenu view_limited(String name, String media_id){
		MpMenu m = new MpMenu(name, MenuType.VIEW_LIMITED);
		m.setMedia_id(media_id);
		return m;
	}
	
	public MpMenu addSub(MpMenu sub){
		if(sub_button == null){
			sub_button = Lists.newArrayList();
		}
		this.sub_button.add(sub);
		return this;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MpMenu> getSub_button() {
		return sub_button;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
}
