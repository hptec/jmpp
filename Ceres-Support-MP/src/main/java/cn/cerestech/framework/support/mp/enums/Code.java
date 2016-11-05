package cn.cerestech.framework.support.mp.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.regexp.RegExp;

public enum Code {
	sys_busying(-1,"系统繁忙，此时请开发者稍候再试"),
	success(0, "请求成功"),
	secret_err(40001,"获取access_token时AppSecret错误，或者access_token无效。请开发者认真比对AppSecret的正确性，或查看是否正在为恰当的公众号调用接口"),
	ill_proof_type(40002,"不合法的凭证类型"),
	ill_openid(40003,"不合法的OpenID，请开发者确认OpenID（该用户）是否已关注公众号，或是否是其他公众号的OpenID"),
	ill_media_type(40004,"不合法的媒体文件类型"),
	ill_file_type(40005,"不合法的文件类型"),
	ill_file_size(40006,"不合法的文件大小"),
	ill_media_id(40007,"不合法的媒体文件id"),
	ill_msg_type(40008,"不合法的消息类型"),
	ill_img_size(40009,"不合法的图片文件大小"),
	ill_voice_size(40010,"不合法的语音文件大小"),
	ill_video_size(40011,"不合法的视频文件大小"),
	ill_thumb_size(40012,"不合法的缩略图文件大小"),
	ill_appid(40013,"不合法的AppID，请开发者检查AppID的正确性，避免异常字符，注意大小写"),
	ill_at(40014,"不合法的access_token，请开发者认真比对access_token的有效性（如是否过期），或查看是否正在为恰当的公众号调用接口"),
	ill_menu_type(40015,"不合法的菜单类型"),
	ill_btn_num(40016,"不合法的按钮个数"),
	ill_btn_numb(40017,"不合法的按钮个数"),
	ill_btn_len(40018,"不合法的按钮名字长度"),
	ill_btn_key(40019,"不合法的按钮KEY长度"),
	ill_btn_url_len(40020,"不合法的按钮URL长度"),
	ill_menu_ver(40021,"不合法的菜单版本号"),
	ill_menu_level(40022,"不合法的子菜单级数"),
	ill_menu_sub_num(40023,"不合法的子菜单按钮个数"),
	ill_menu_sub_type(40024,"不合法的子菜单按钮类型"),
	ill_menu_sub_len(40025,"不合法的子菜单按钮名字长度"),
	ill_menu_sub_key_len(40026,"不合法的子菜单按钮KEY长度"),
	ill_menu_sub_url_len(40027,"不合法的子菜单按钮URL长度"),
	ill_menu_tap(40028,"不合法的自定义菜单使用用户"),
	ill_oauth_code(40029,"不合法的oauth_code"),
	ill_refresh_token(40030,"不合法的refresh_token"),
	ill_openid_lst(40031,"不合法的openid列表"),
	ill_openid_len(40032,"不合法的openid列表长度"),
	ill_has_unicode(40033,"不合法的请求字符，不能包含\\uxxxx格式的字符"),
	ill_parameter(40035,"不合法的参数"),
	ill_req_format(40038,"不合法的请求格式"),
	ill_url_len(40039,"不合法的URL长度"),
	ill_gp_id(40050,"不合法的分组id"),
	ill_gp_name(40051,"分组名字不合法"),
	ill_gp_nameb(40117,"分组名字不合法"),
	ill_media_id_len(40118,"media_id大小不合法"),
	ill_btn_type(40119,"button类型错误"),
	ill_btn_typeb(40120,"button类型错误"),
	ill_media_id_type(40121,"不合法的media_id类型"),
	ill_no_at(41001,"缺少access_token参数"),
	ill_no_appid(41002,"缺少appid参数"),
	ill_no_refreshtoken(41003,"缺少refresh_token参数"),
	ill_no_secret(41004,"缺少secret参数"),
	ill_no_media(41005,"缺少多媒体文件数据"),
	ill_no_mediaid(41006,"缺少media_id参数"),
	ill_no_menu(41007,"缺少子菜单数据"),
	ill_no_authcode(41008,"缺少oauth code"),
	ill_no_openid(41009,"缺少openid"),
	ill_at_timeout(42001,"access_token超时，请检查access_token的有效期，请参考基础支持-获取access_token中，对access_token的详细机制说明"),
	ill_rt_timeout(42002,"refresh_token超时"),
	ill_oc_timeout(42003,"oauth_code超时"),
	ill_do_get(43001,"需要GET请求"),
	ill_do_post(43002,"需要POST请求"),
	ill_https(43003,"需要HTTPS请求"),
	ill_no_subscribe(43004,"需要接收者关注"),
	ill_is_not_friend(43005,"需要好友关系"),
	ill_media_empty(44001,"多媒体文件为空"),
	ill_post_empty(44002,"POST的数据包为空"),
	ill_sc_empty(44003,"图文消息内容为空"),
	ill_imsg_empty(44004,"文本消息内容为空"),
	ill_media_limit_size(45001,"多媒体文件大小超过限制"),
	ill_msg_contnt_limit_size(45002,"消息内容超过限制"),
	ill_tit_limit_size(45003,"标题字段超过限制"),
	ill_desc_limit_size(45004,"描述字段超过限制"),
	url_limit_size(45005,"链接字段超过限制"),
	img_url_limit_size(45006,"图片链接字段超过限制"),
	ill_voice_time_limit(45007,"语音播放时间超过限制"),
	ill_im_limit_size(45008,"图文消息超过限制"),
	ill_i_limit_times(45009,"接口调用超过限制"),
	ill_menu_num(45010,"创建菜单个数超过限制"),
	replay_too_late(45015,"回复时间超过限制"),
	ill_mod_sys_gp(45016,"系统分组，不允许修改"),
	ill_gp_name_limit(45017,"分组名字过长"),
	ill_gp_num_limit(45018,"分组数量超过上限"),
	ill_no_mediab(46001,"不存在媒体数据"),
	ill_menu_ver_notexisted(46002,"不存在的菜单版本"),
	ill_menu_notexisted(46003,"不存在的菜单数据"),
	ill_nfd_user(46004,"不存在的用户"),
	ill_json_xml_error(47001,"解析JSON/XML内容错误"),
	ill_api_no_auth(48001,"api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限"),
	ill_api_no_auth_u(50001,"用户未授权该api"),
	ill_param_err(61451,"参数错误(invalid parameter)"),
	ill_custom_acc_err(61452,"无效客服账号(invalid kf_account)"),
	ill_custom_acc_existed(61453,"客服帐号已存在(kf_account exsited)"),
	ill_custom_name_too_long(61454,"客服帐号名长度超过限制(仅允许10个英文字符，不包括@及@后的公众号的微信号)(invalid kf_acount length)"),
	ill_custom_name_ill(61455,"客服帐号名包含非法字符(仅允许英文+数字)(illegal character in kf_account)"),
	ill_custom_limit_num(61456,"客服帐号个数超过限制(10个客服账号)(kf_account count exceeded)"),
	ill_header_img_type(61457,"无效头像文件类型(invalid file type)"),
	sys_err(61450,"系统错误(system error)"),
	date_format_err(61500,"日期格式错误"),
	data_rang_err(61501,"日期范围错误"),
	post_param_err(9001001,"POST数据参数不合法"),
	service_err(9001002,"远端服务不可用"),
	ticket_err(9001003,"Ticket不合法"),
	area_user_fail(9001004,"获取摇周边用户信息失败"),
	obtain_commercial_info_err(9001005,"获取商户信息失败"),
	openid_get_fail(9001006,"获取OpenID失败"),
	up_file_miss(9001007,"上传文件缺失"),
	up_file_type_err(9001008,"上传素材的文件类型不合法"),
	up_fle_size_ill(9001009,"上传素材的文件尺寸不合法"),
	up_fail(9001010,"上传失败"),
	acc_err(9001020,"帐号不合法"),
	device_alive_low(9001021,"已有设备激活率低于50%，不能新增设备"),
	device_nul_ill(9001022,"设备申请数不合法，必须为大于0的数字"),
	has_device_in(9001023,"已存在审核中的设备ID申请"),
	q_device_limit(9001024,"一次查询设备ID数量不能超过50"),
	deviceid_ill(9001025,"设备ID不合法"),
	pageid_ill(9001026,"页面ID不合法"),
	page_param_ill(9001027,"页面参数不合法"),
	del_page_num_limit(9001028,"一次删除页面ID数量不能超过10"),
	page_in_device_err(9001029,"页面已应用在设备中，请先解除应用关系再删除"),
	q_page_num_limit(9001030,"一次查询页面ID数量不能超过50"),
	date_rang_err(9001031,"时间区间不合法"),
	bind_page_device_perr(9001032,"保存设备与页面的绑定关系参数错误"),
	store_id_ill(9001033,"门店ID不合法"),
	device_desc_toolong(9001034,"设备备注信息过长"),
	device_perr(9001035,"设备申请参数不合法"),
	q_begin_err(9001036,"查询起始值begin不合法"),
	others(-999,"位置状态码");
	
	private int code;
	private String desc;
	
	private Code(int code, String desc){
		this.code = code;
		this.desc = desc;
	}
	public String getDesc(){
		return this.desc;
	}
	public int getCode(){
		return this.code;
	}
	public static Code valueOf(int code){
		List<Code> now = Arrays.asList(Code.values()).stream().filter(t->{
			if(code == t.getCode()){
				return true;
			}else{
				return false;
			}
		}).collect(Collectors.toList());
		
		return now.size()>0?now.get(0):others;
	}
	
	public static Code instance(String code){
		if(Strings.isNullOrEmpty(code)){
			return  others;
		}
		if(RegExp.match(RegExp.REGEXP_INTEGER, code)){
			return valueOf(Integer.parseInt(code.trim()));
		}else{
			return others;
		}
	}
}
