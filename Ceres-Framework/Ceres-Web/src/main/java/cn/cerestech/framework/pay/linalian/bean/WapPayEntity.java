package cn.cerestech.framework.pay.linalian.bean;

import org.apache.commons.lang3.StringUtils;

public class WapPayEntity {
	
	//版本号 	
	private String version="1.1";//是 	变(6) 	版本号:1.1 
	//商户编号
	private String oid_partner;//是 	定(18) 	商户编号是商户在连连支付支付平台上开 设的商户号码,为 18 位数字,如201304121000001004
	//平台来源标示
	private String platform;// 否 该参数可实现多个商户号之间用户数据共 享,该标识填写主商户号即可。
	//商户系统中用户的唯一号码
	private String user_id;//是 该用户在商户系统中的唯一编号,要求是该编号在商户系统中唯一标识该用户
	private String app_request;// 是 1-Android 2-ios 3-WAP
	private String sign_type;//是 RSA 或者 MD5
	private String sign;//是 RSA 加密签名,见安全签名机制
	private String bg_color;//否  支付页面背景颜色,000000~ffffff 默认值为 ff5001
	private String font_color; //否 支付页面字体颜色
	private String syschnotify_flag="0";//否 0-点击通知 1-主动通知 默认为 0
	//-业务参数-----------------------------------------------
	private String busi_partner; // 是  虚拟商品销售:101001 实物商品销售:109001
	private String no_order;//是 商户系统唯一订单号
	private String dt_order;// 是  格 式 : YYYYMMDDH24MISS字,14 位数 精确到秒
	private String name_goods;//是 商品名字
	private String info_order ;//否 
	private String money_order;// 是  该笔订单的资金总额,单位为 RMB-元。 大于 0 的数字,精确到小数点后两位。 如:49.65
	private String notify_url;// 是 连连支付支付平台在用户支付成功后通知 商户服务端的地址
	private String url_return;// 否 支付结束后显示的合作商户系统页面地址
	private String no_agree;//否 已经记录快捷银行卡的用户,商户在调用的时候可以与 pay_type 一块配合使用
	private String valid_order;// 否 分钟为单位,默认为 10080 分钟(7 天), 从创建时间开始,过了此订单有效时间此笔 订单就会被设置为失败状态不能再重新进 行支付
	private String id_type = "0";//否 默认为 0 0:身份证或企业经营证件,1: 户口簿,2: 护照,3.军官证,4.士兵证,5. 港澳居民来往 内地通行证,6. 台湾同胞来往内地通行 证,7. 临时身份证,8. 外国人居留证,9. 警 官证, X.其他证件,10.组织结构代码
	private String id_no;//否 证件号码 非必须
	private String acct_name;//否  银行账号姓名
	private String flag_modify = "0";//否 非必须 0-可以修改,默认为 0 1-不允许修改 与 id_type,id_no,acct_name 配合使用, 如果该用户在商户系统已经实名认证过了, 则在绑定银行卡的输入信息不能修改,否则 可以修改
	private String shareing_data;//否 分帐商户号 1^分账业务类型 1^分帐金额 1^分账说明 1|分帐商户号 2^分账业务类 型 2^分帐金额 2^分账说明 2|分帐商户号 3^分账业务类型 3^分帐金额 3^分账说明 3注:1、 分账商户号:为注册登记在连连银通系统的商户编号(18 位数字) 2、 分账业务类型:可与主收款业务类型 busi_partner 一致 3、 分帐金额:元为单位,保留两位数字 4、 分账说明:不能超过 100 个汉字,不能有^和|符号分账方只支持除主收款方外的 3 个分账方
	private String risk_item;// 是  风险控制参数 此字段填写风控参数,采用 json 串的模式 传入,字段名和字段内容彼此对应好,例如: {"frms_ware_category":"1002","user_in fo_mercht_userno":"123456","user_inf o_dt_register":"20141015165530"}
	private String card_no;// 否 银行卡号 银行卡号前置,卡号可以在商户的页面输入
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOid_partner() {
		return oid_partner;
	}

	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getApp_request() {
		return app_request;
	}

	public void setApp_request(String app_request) {
		this.app_request = app_request;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getBg_color() {
		return bg_color;
	}

	public void setBg_color(String bg_color) {
		this.bg_color = bg_color;
	}

	public String getFont_color() {
		return font_color;
	}

	public void setFont_color(String font_color) {
		this.font_color = font_color;
	}

	public String getSyschnotify_flag() {
		return syschnotify_flag;
	}

	public void setSyschnotify_flag(String syschnotify_flag) {
		this.syschnotify_flag = syschnotify_flag;
	}

	public String getBusi_partner() {
		return busi_partner;
	}

	public void setBusi_partner(String busi_partner) {
		this.busi_partner = busi_partner;
	}

	public String getNo_order() {
		return no_order;
	}

	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}

	public String getDt_order() {
		return dt_order;
	}

	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}

	public String getName_goods() {
		return name_goods;
	}

	public void setName_goods(String name_goods) {
		this.name_goods = name_goods;
	}

	public String getInfo_order() {
		return info_order;
	}

	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}

	public String getMoney_order() {
		return money_order;
	}

	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getUrl_return() {
		return url_return;
	}

	public void setUrl_return(String url_return) {
		this.url_return = url_return;
	}

	public String getNo_agree() {
		return no_agree;
	}

	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}

	public String getValid_order() {
		return valid_order;
	}

	public void setValid_order(String valid_order) {
		this.valid_order = valid_order;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public String getAcct_name() {
		return acct_name;
	}

	public void setAcct_name(String acct_name) {
		this.acct_name = acct_name;
	}

	public String getFlag_modify() {
		return flag_modify;
	}

	public void setFlag_modify(String flag_modify) {
		this.flag_modify = flag_modify;
	}

	public String getShareing_data() {
		return shareing_data;
	}

	public void setShareing_data(String shareing_data) {
		this.shareing_data = shareing_data;
	}

	public String getRisk_item() {
		return risk_item;
	}

	public void setRisk_item(String risk_item) {
		this.risk_item = risk_item;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public boolean validate(){
		boolean bol = StringUtils.isNotBlank(version)
					&& StringUtils.isNotBlank(oid_partner)
					&& StringUtils.isNotBlank(user_id)
					&& StringUtils.isNotBlank(app_request)
					&& StringUtils.isNotBlank(sign_type)
					&& StringUtils.isNotBlank(busi_partner)
					&& StringUtils.isNotBlank(no_order)
					&& StringUtils.isNotBlank(dt_order)
					&& StringUtils.isNotBlank(name_goods)
					&& StringUtils.isNotBlank(money_order)
					&& StringUtils.isNotBlank(notify_url)
					&& StringUtils.isNotBlank(risk_item);
		return bol;
	}
}
