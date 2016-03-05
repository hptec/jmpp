package cn.cerestech.framework.pay.linalian.bean;

/**
 * 风险控制参数
 * @author bird
 *
 */
public class RiskItem {
	//*商品类目
	private String frms_ware_category;//是 见附录《商品类目》
	//商户用户唯一标识 	
	private String user_info_mercht_userno;//是
	//商户用户登录名
	private String user_info_mercht_userlogin; 
	//用户邮箱
	private String user_info_mail; 
	//*绑定手机号 	
	private String user_info_bind_phone;
	//商户用户分类 	
	private String user_info_mercht_usertype;
	//*注册时间 	
	private String user_info_dt_register; //注册时间
	//注册 IP 	
	private String user_info_register_ip;
	/**
	 * @param frms_ware_category 商品类目
	 * @param user_info_mercht_userno 商户用户唯一标识 	
	 * @param user_info_dt_register 注册时间
	 */
	public RiskItem(String frms_ware_category, String user_info_mercht_userno, String user_info_dt_register){
		this.frms_ware_category = frms_ware_category;
		this.user_info_mercht_userno = user_info_mercht_userno;
		this.user_info_dt_register = user_info_dt_register;
	}
	public RiskItem(){}

	public String getFrms_ware_category() {
		return frms_ware_category;
	}

	public void setFrms_ware_category(String frms_ware_category) {
		this.frms_ware_category = frms_ware_category;
	}

	public String getUser_info_mercht_userno() {
		return user_info_mercht_userno;
	}

	public void setUser_info_mercht_userno(String user_info_mercht_userno) {
		this.user_info_mercht_userno = user_info_mercht_userno;
	}

	public String getUser_info_mercht_userlogin() {
		return user_info_mercht_userlogin;
	}

	public void setUser_info_mercht_userlogin(String user_info_mercht_userlogin) {
		this.user_info_mercht_userlogin = user_info_mercht_userlogin;
	}

	public String getUser_info_mail() {
		return user_info_mail;
	}

	public void setUser_info_mail(String user_info_mail) {
		this.user_info_mail = user_info_mail;
	}

	public String getUser_info_bind_phone() {
		return user_info_bind_phone;
	}

	public void setUser_info_bind_phone(String user_info_bind_phone) {
		this.user_info_bind_phone = user_info_bind_phone;
	}

	public String getUser_info_mercht_usertype() {
		return user_info_mercht_usertype;
	}

	public void setUser_info_mercht_usertype(String user_info_mercht_usertype) {
		this.user_info_mercht_usertype = user_info_mercht_usertype;
	}

	public String getUser_info_dt_register() {
		return user_info_dt_register;
	}

	public void setUser_info_dt_register(String user_info_dt_register) {
		this.user_info_dt_register = user_info_dt_register;
	}

	public String getUser_info_register_ip() {
		return user_info_register_ip;
	}

	public void setUser_info_register_ip(String user_info_register_ip) {
		this.user_info_register_ip = user_info_register_ip;
	}
}
