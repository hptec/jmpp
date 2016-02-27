package cn.cerestech.framework.pay.jubao.wap;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.jubao.bean.PayEntity;
import cn.cerestech.framework.pay.jubao.enums.Channel;
import cn.cerestech.framework.pay.jubao.enums.DefaultTab;
import cn.cerestech.framework.pay.jubao.utils.UnifiedPay;

public class WapPay {
	public static final String url = "http://www.jubaopay.com/apiwapsyt.htm";
	
	public static Result payForm(PayEntity entity, String cfgPath, String certDir, DefaultTab tab){
		return UnifiedPay.pay(entity, cfgPath, certDir, Channel.WAP, true, tab);
	}
}
