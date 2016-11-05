package cn.cerestech.framework.support.mp.enums;
/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年4月22日
 */
/*
 * 1、简体中文 "zh_CN" 2、繁体中文TW "zh_TW" 3、繁体中文HK "zh_HK" 4、英文 "en" 5、印尼 "id" 
 * 6、马来 "ms" 7、西班牙 "es" 8、韩国 "ko" 
 * 9、意大利 "it" 10、日本 "ja" 11、波兰 "pl" 12、葡萄牙 "pt" 
 * 13、俄国 "ru" 14、泰文 "th" 
 * 15、越南 "vi" 16、阿拉伯语 "ar" 17、北印度 "hi" 18、希伯来 "he" 19、土耳其 "tr" 
 * 20、德语 "de" 21、法语 "fr"
 */
public enum Language {
	ZH_CN("zh_CN","简体中文"),
	ZH_TW("zh_TW","繁体中文TW"),
	ZH_HK("zh_HK","繁体中文HK"),
	EN("en","英文"),
	ID("id","印尼"),
	MS("ms","马来"),
	ES("es","韩国"),
	IT("it","意大利"),
	JP("jp","日本"),
	PL("pl","波兰"),
	PT("pt","葡萄牙"),
	RU("ru","俄国"),
	TH("th","泰文"),
	VI("vi","越南"),
	AR("ar","阿拉伯语"),
	HI("hi","北印度"),
	HE("he","希伯来"),
	TR("tr","土耳其"),
	DE("de","德语"),
	FR("fr","法语")
	;
	private String key, desc;
	private Language(String key, String desc){
		this.key = key;
		this.desc = desc;
	}
	public String key(){
		return key;
	}
	public String desc(){
		return desc;
	}
	
}
