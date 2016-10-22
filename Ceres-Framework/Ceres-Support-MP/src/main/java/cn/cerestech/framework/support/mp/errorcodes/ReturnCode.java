package cn.cerestech.framework.support.mp.errorcodes;

public enum ReturnCode {
	BUSY(-1, "系统繁忙，此时请稍候再试"), //
	SUCCESS(0, "请求成功"), //
	TOKEN_INVALID(40001, "AppSecret错误，或者access_token无效"), //
	TOKEN_ILLGAL(40002, "不合法的凭证类型"), //
	OPENID_INVALID(40003, "不合法的OpenID"), //
	MEDIA_TYPE_INVALID(40004, "不合法的媒体文件类型"), //
	FILE_TYPE_INVALID(40005, "不合法的文件类型"), //
	FILE_SIZE_INVALID(40006, "不合法的文件大小"), //
	MEDIA_ID_INVALID(40007, "不合法的媒体文件id"), //
	MSG_TYPE_INVALID(40008, "不合法的消息类型"), //
	IMG_SIZE_INVALID(40009, "不合法的图片文件大小"), //
	VOICE_SIZE_INVALID(40010, "不合法的语音文件大小"), //
	VEDIO_SIZE_INVALID(40011, "不合法的视频文件大小"), //
	THUMBNAIL_SIZE_INVALID(40012, "不合法的缩略图文件大小"), //
	APPID_INVALID(40013, "不合法的AppID"), //
	ACCESS_TOKEN_INVALID(40014, "不合法的access_token"), //
	MENU_TYPE_INVALID(40015, "不合法的菜单类型"), //
	BUTTON_COUNT_INVALID(40016, "不合法的按钮个数"), //
	BUTTON_NUMBER_INVALID(40017, "不合法的按钮个数"), // 文档描述是重复的，不知道为什么，这里就是区别一下不同种类的错误代码。
	BUTTONNAME_LENGTH_INVALID(40018, "不合法的按钮名字长度"), //
	BUTTONKEY_LENGTH_INVALID(40019, "不合法的按钮KEY长度"), //
	BUTTONURL_LENGTH_INVALID(40020, "不合法的按钮URL长度"), //
	MENU_VERSION_INVALID(40021, "不合法的菜单版本号"), //
	SUBMENU_DEPTH_INVALID(40022, "不合法的子菜单级数"), //
	SUBMENU_COUNT_INVALID(40023, "不合法的子菜单按钮个数"), //
	SUBMENU_TYPE_INVALID(40024, "不合法的子菜单按钮类型"), //
	SUBMENU_NAME_LENGTH_INVALID(40025, "不合法的子菜单按钮名字长度"), //
	SUBMENU_KEY_LENGTH_INVALID(40026, "不合法的子菜单按钮KEY长度"), //
	SUBMENU_URL_LENGTH_INVALID(40027, "不合法的子菜单按钮URL长度"), //
	MENU_USER_INVALID(40028, "不合法的自定义菜单使用用户"), //
	OAUTHCODE_INVALID(40029, "不合法的oauth_code"), //
	REFRESHTOKEN_INVALID(40030, "不合法的refresh_token"), //
	OPENID_LIST_INVALID(40031, "不合法的openid列表"), //
	OPENID_LIST_LENGHT_INVALID(40032, "不合法的openid列表长度"), //
	CHARACTER_INVALID(40033, "不合法的请求字符，不能包含\\uxxxx格式的字符"), //
	PARAMETER_INVALID(40035, "不合法的参数"), //
	REQUEST_FORMAT_INVALID(40038, "不合法的请求格式"), //
	URL_LENGTH_INVALID(40039, "不合法的URL长度"), //
	GROUP_ID_INVALID(40050, "不合法的分组id"), //
	GROUP_NAME_INVALID(40051, "分组名字不合法"), //
	GROUP_NAME_ILLGAL(40117, "分组名字不合法"), //
	MEDIAID_SIZE_INVALID(40118, "media_id大小不合法"), //
	BUTTON_TYPE_ERROR(40119, "button类型错误"), //
	BUTTON_TYPE_ILLGAL(40120, "button类型错误"), //
	MEDIAID_TYPE_INVALID(40121, "不合法的media_id类型"), //
	MP_INVALID(40122, "微信号不合法"), //
	IMG_NOT_SUPPORT(40123, "不支持的图片格式"), //
	ACCESSTOKEN_NOT_FOUND(41001, "缺少access_token参数"), //
	APPID_NOT_FOUND(41002, "缺少appid参数"), //
	REFRESHTOKEN_NOT_FOUND(41003, "缺少refresh_token参数"), //
	SECRET_NOT_FOUND(41004, "缺少secret参数"), //
	MEDIA_DATA_NOT_FOUND(41005, "缺少多媒体文件数据"), //
	MEDIA_ID_NOT_FOUND(41006, "缺少media_id参数"), //
	SUBMENU_DATA_NOT_FOUND(41007, "缺少子菜单数据"), //
	OAUTH_CODE_NOT_FOUND(41008, "缺少oauth code"), //
	OPENID_NOT_FOUND(41009, "缺少openid"), //
	ACCESSTOKEN_TIMEOUT(42001, "access_token超时"), //
	REFRESHTOKEN_TIMEOUT(42002, "refresh_token超时"), //
	OAUTHCODE_TIMEOUT(42003, "oauth_code超时"), //
	USER_PASS_MODIFIED(42007, "用户修改微信密码，accesstoken和refreshtoken失效，需要重新授权"), //
	GET_REQUIRED(43001, "需要GET请求"), //
	POST_REQURIED(43002, "需要POST请求"), //
	HTTPS_REQUIRED(43003, "需要HTTPS请求"), //
	FOLLOW_REQUIRED(43004, "需要接收者关注"), //
	FRIENDSHIP_REQUIRED(43005, "需要好友关系"), //
	MEDIA_IS_EMPTY(44001, "多媒体文件为空"), //
	POST_DATA_IS_EMPTY(44002, "POST的数据包为空"), //
	RICHTEXT_IS_EMPTY(44003, "图文消息内容为空"), //
	TEXT_IS_EMPTY(44004, "文本消息内容为空"), //
	MEDIA_SIZE_OVER_LIMIT(45001, "多媒体文件大小超过限制"), //
	MSG_CONTENT_OVER_LIMIT(45002, "消息内容超过限制"), //
	TITLE_OVER_LIMIT(45003, "标题字段超过限制"), //
	DESC_OVER_LIMIT(45004, "描述字段超过限制"), //
	LINK_OVER_LIMIT(45005, "链接字段超过限制"), //
	PICURL_OVER_LIMIT(45006, "图片链接字段超过限制"), //
	VOICE_PLAY_TIME_OVER_LIMIT(45007, "语音播放时间超过限制"), //
	RICHTEXT_OVER_LIMIT(45008, "图文消息超过限制"), //
	API_INVOKE_COUNT_OVER_LIMIT(45009, "接口调用超过限制"), //
	MENU_SIZE_OVER_LIMIT(45010, "创建菜单个数超过限制"), //
	REPLY_TIME_OVER_LIMIT(45015, "回复时间超过限制"), //
	SYSTEM_GROUP_CANNOT_MODIFY(45016, "系统分组，不允许修改"), //
	GROUP_NAME_TOO_LONG(45017, "分组名字过长"), //
	GROUP_OVER_COUNT_LIMIT(45018, "分组数量超过上限"), //
	CUSTSERVICE_OVER_LIMIT(45047, "客服接口下行条数超过上限"), //
	MEDIA_NOT_EXIST(46001, "不存在媒体数据"), //
	MENU_VERSION_NOT_EXIST(46002, "不存在的菜单版本"), //
	MENU_DATA_NOT_EXIST(46003, "不存在的菜单数据"), //
	USER_NOT_EXIST(46004, "不存在的用户"), //
	XMLJSON_ERROR(47001, "解析JSON/XML内容错误"), //
	API_NOT_AUTHORIZED(48001, "API功能未授权"), //
	API_BANNED(48002, "api接口被封禁"), //
	API_NOT_AUTHORIZED_BY_USER(50001, "用户未授权该api"), //
	USER_BANNED(50002, "用户受限"), //
	INVALID_PARAMETER(61451, "参数错误"), //
	INVALID_KF_ACCOUNT(61452, "无效客服账号"), //
	KF_ACCOUNT_EXIST(61453, "客服帐号已存在"), //
	KF_NAME_TOO_LONG(61454, "客服帐号名长度超过限制"), //
	ILLGAL_CHARACTER_IN_KF_ACCOUNT(61455, "客服帐号名包含非法字符"), //
	KF_ACCOUNT_COUNT_OVER_LIMIT(61456, "客服帐号个数超过限制"), //
	AVATAR_TYPE_INVALID(61457, "无效头像文件类型"), //
	SYSTEM_ERROR(61450, "系统错误"), //
	DATE_FORMAT_ERROR(61500, "日期格式错误"), //
	NO_MENUID_MENU(65301, "不存在此menuid对应的个性化菜单"), //
	NO_USER(65302, "没有相应的用户"), //
	NO_DEFAULT_MENU(65303, "没有默认菜单，不能创建个性化菜单"), //
	MATCHRULE_EMPTY(65304, "MatchRule信息为空"), //
	CUSTMENU_LIMITED(65305, "个性化菜单数量受限"), //
	CUSTMENU_NOT_SUPPORT(65306, "不支持个性化菜单的帐号"), //
	CUSTMENU_IS_EMPTY(65307, "个性化菜单信息为空"), //
	NO_RESPONSE_TYPE_BUTTON(65308, "包含没有响应类型"), //
	CUSTMENU_SWITCH_CLOSED(65309, "个性化菜单开关处于关闭状态"), //
	NATION_REQURIED(65310, "填写了省份或城市信息，国家信息不能为空"), //
	PROVINCE_REQUIRED(65311, "填写了城市信息，省份信息不能为空"), //
	NATION_ILLGAL(65312, "不合法的国家信息"), //
	PROVINCE_ILLGAL(65313, "不合法的省份信息"), //
	CITY_ILLGAL(65314, "不合法的城市信息"), //
	TOO_MUCH_DOMAIN_JUMP(65316, "该公众号的菜单设置了过多的域名外跳"), //
	ILLGAL_URL(65317, "不合法的URL"), //
	POST_PARAMETER_ILLGAL(9001001, "POST数据参数不合法"), //
	REMOTE_SERVICE_UNAVAILABLE(9001002, "远端服务不可用"), //
	TICKET_ILLGAL(9001003, "Ticket不合法"), //
	SHAKE_AROUND_FAILED(9001004, "获取摇周边用户信息失败"), //
	MERCHANT_INFO_FAILED(9001005, "获取商户信息失败"), //
	OPENID_FETCH_FAILED(9001006, "获取OpenID失败"), //
	UPLOAD_FILE_LOST(9001007, "上传文件缺失"), //
	UPLOAD_MATERIA_TYPE_ILLGAL(9001008, "上传素材的文件类型不合法"), //
	UPLOAD_MATERIA_SIZE_ILLGAL(9001009, "上传素材的文件尺寸不合法"), //
	UPLOAD_FAILED(9001010, "上传失败"), //
	ACCOUNT_ILLGAL(9001020, "帐号不合法"), //
	DEVICE_ACITVE_LOW(9001021, "已有设备激活率低于50%，不能新增设备"), //
	DEVICE_APPLY_COUNT_ERROR(9001022, "设备申请数不合法，必须为大于0的数字"), //
	APPLING_DEVICE(9001023, "已存在审核中的设备ID申请"), //
	DEVICE_QUERY_COUNT_TOO_MUCH(9001024, "一次查询设备ID数量不能超过50"), //
	DEVICE_ID_ILLGAL(9001025, "设备ID不合法"), //
	PAGE_ID_ILLGAL(9001026, "页面ID不合法"), //
	PAGE_PARAMETER_ILLGAL(9001027, "页面参数不合法"), //
	DELETE_PAGE_COUNT_LESS_THAN_10(9001028, "一次删除页面ID数量不能超过10"), //
	DEVICE_IN_USE(9001029, "页面已应用在设备中，请先解除应用关系再删除"), //
	QUERY_PAGE_LESS_THAN_50_ONETIME(9001030, "一次查询页面ID数量不能超过50"), //
	TIMEZONE_ILLGAL(9001031, "时间区间不合法"), //
	PAGE_DEVICE_BIND_ERROR(9001032, "保存设备与页面的绑定关系参数错误"), //
	STORE_ID_ILLGAL(9001033, "门店ID不合法"), //
	DEVICE_REMARK_TOO_LONG(9001034, "设备备注信息过长"), //
	DEVICE_APPLY_PARAMETER(9001035, "设备申请参数不合法"), //
	QUERY_BEGIN_ILLGAL(9001036, "查询起始值begin不合法"),//

	;
	private Integer key;
	private String desc;

	private ReturnCode(Integer key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public Integer key() {
		return this.key;
	}

	public String desc() {
		return this.desc;
	}
}
