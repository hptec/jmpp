package cn.cerestech.framework.pay.jubao.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.pay.bean.PayNotifyBean;
import cn.cerestech.framework.pay.enums.PayChannel;
import cn.cerestech.framework.pay.jubao.bean.JubaoPay;
import cn.cerestech.framework.pay.jubao.utils.RSA;
import cn.cerestech.framework.pay.service.PayServices;

@Service
public final class JubaoService {
	@Autowired(required=false)
	PayServices payService;
	
	@Autowired
	JbPayConfigService jbConfig;
	/**
	 * 
	 * @param request
	 * @param response
	 * @param isFront: 是否是前端返回页面的处理
	 */
	public void payResult(HttpServletRequest request, HttpServletResponse response, boolean isFront){
		String message = request.getParameter("message");
		String signature = request.getParameter("signature");
		RSA.intialize(jbConfig.cfgFile(), jbConfig.certDir());
		// 解密，校验签名，并处理业务逻辑处理
		JubaoPay jubaopay = new JubaoPay();
		boolean result = jubaopay.decrypt(message, signature);
		String local_trade_no = jubaopay.getEncrypt("payid");
		String third_trade_no = jubaopay.getEncrypt("orderNo");
		String money = jubaopay.getEncrypt("amount");//用户支付给第三方
		String platform_money = jubaopay.getEncrypt("realReceive");//平台实际到账
		String attach = jubaopay.getEncrypt("remark");
		String partnerid = jubaopay.getEncrypt("partnerid");
		String state = jubaopay.getEncrypt("state");//2:支付成功
		String trade_time = jubaopay.getEncrypt("modifyTime");//yyyyMMddHHmmss
		String payerName = jubaopay.getEncrypt("payerName");
		
		state  = Strings.nullToEmpty(state).trim();
		// 输出正确的响应
		try {
			if(result){
				if(NumberUtils.isDigits(state) && 2 == Integer.parseInt(state) ){//支付成功
					System.out.println("支付回调成功："+local_trade_no+"  money:"+money);
					PayNotifyBean bean = new PayNotifyBean();
					bean.setAttach(attach);
					bean.setChannel(PayChannel.JUBAOPAY);
					bean.setLocal_trade_no(local_trade_no);
					bean.setMoney(NumberUtils.createBigDecimal(money));
					bean.setThird_trade_no(third_trade_no);
					bean.setPay_time(Dates.parse(trade_time, "yyyyMMddHHmmss"));//yyyyMMddHHmmss
					payService.payNotify(bean);
				}
				if(!isFront){
					response.getWriter().write("success");
					response.getWriter().flush();
				}
			} else {
				if(!isFront){
					// 签名验证失败
					response.getWriter().write("failed");
					response.getWriter().flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
