package cn.cerestech.framework.pay.bean;

import java.math.BigDecimal;
import java.util.Date;

import cn.cerestech.framework.pay.enums.PayChannel;

public class PayNotifyBean {
	private String local_trade_no;
	private String third_trade_no;
	private BigDecimal money;
	private Date pay_time;
	private String attach;
	private PayChannel channel;
	
	public PayNotifyBean(String local_trade_no, String third_trade_no, BigDecimal money, Date pay_time, String attach,
			PayChannel channel) {
		super();
		this.local_trade_no = local_trade_no;
		this.third_trade_no = third_trade_no;
		this.money = money;
		this.pay_time = pay_time;
		this.attach = attach;
		this.channel = channel;
	}

	public PayNotifyBean(){
	}
	
	public String getLocal_trade_no() {
		return local_trade_no;
	}
	public void setLocal_trade_no(String local_trade_no) {
		this.local_trade_no = local_trade_no;
	}
	public String getThird_trade_no() {
		return third_trade_no;
	}
	public void setThird_trade_no(String third_trade_no) {
		this.third_trade_no = third_trade_no;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Date getPay_time() {
		return pay_time;
	}
	public void setPay_time(Date pay_time) {
		this.pay_time = pay_time;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public PayChannel getChannel() {
		return channel;
	}
	public void setChannel(PayChannel channel) {
		this.channel = channel;
	}
	
}
