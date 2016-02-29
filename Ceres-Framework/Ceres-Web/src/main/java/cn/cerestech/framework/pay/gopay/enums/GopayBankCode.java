package cn.cerestech.framework.pay.gopay.enums;

/**
 * 银行代码
 * @author <a href="mailto:royrxc@gmail.com">bird</a>
 *
 */
public enum GopayBankCode {
	CCB("CCB","中国建设银行"),
	CMB("CMB","招商银行"),
	ICBC("ICBC","中国工商银行"),
	BOC("BOC","中国银行"),
	ABC("ABC","中国农业银行"),
	BOCOM("BOCOM","交通银行"),
	CMBC("CMBC","中国民生银行"),
	HXBC("HXBC","华夏银行"),
	CIB("CIB","兴业银行"),
	SPDB("SPDB","上海浦东发展银行"),
	GDB("GDB","广东发展银行"),
	CITIC("CITIC","中心银行"),
	CEB("CEB","光大银行"),
	PSBC("PSBC","中国邮政银行"),
	BOBJ("BOBJ","北京银行"),
	TCCB("TCCB","天津银行"),
	BOS("BOS","上海银行"),
	PAB("PAB","平安银行"),
	NBCB("NBCB","宁波银行"),
	NJCB("NJCB","南京银行");
	
	private String key, desc;
	
	private GopayBankCode(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	
	public String key(){
		return this.key;
	}
	
	public String desc(){
		return this.desc;
	}
	
	public GopayBankCode keyOf(String key){
		for (GopayBankCode item : GopayBankCode.values()) {
			if(item.key().equalsIgnoreCase(key)){
				return item;
			}
		}
		return null;
	}
}
