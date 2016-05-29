package cn.cerestech.middleware.sms.console.queryforms;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import cn.cerestech.framework.support.persistence.search.AbstractCriteria;
import cn.cerestech.middleware.sms.entity.SmsRecord;
/**
 * 短信检索平台
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2016年1月3日上午9:57:32
 * @param <T>
 */
public class SmsCriteria<T extends SmsRecord> extends AbstractCriteria<T>{
	private String phone;
	private String ip;
	private Date from;
	private Date to;
	private String provider;
	private Date cur_day;
	private List<String> state = Lists.newArrayList();
	private String business_type;
	
	public SmsCriteria<T> setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	public SmsCriteria<T> setIp(String ip) {
		this.ip = ip;
		return this;
	}
	public SmsCriteria<T> setFrom(Date from) {
		this.from = from;
		return this;
	}
	public SmsCriteria<T> setTo(Date to) {
		this.to = to;
		return this;
	}
	public SmsCriteria<T> setProvider(String provider) {
		this.provider = provider;
		return this;
	}
	public SmsCriteria<T> setCur_day(Date cur_day) {
		this.cur_day = cur_day;
		return this;
	}
	public SmsCriteria<T> stateIn(String ... status){
		if(status != null && status.length > 0){
			for (int i = 0; i < status.length; i++) {
				state.add(status[i]);
			}
		}
		return this;
	}
	public SmsCriteria<T> setState(List<String> state) {
		this.state = state;
		return this;
	}
	public SmsCriteria<T> setBusiness_type(String business_type) {
		this.business_type = business_type;
		return this;
	}
	public String getPhone() {
		return phone;
	}
	public String getIp() {
		return ip;
	}
	public Date getFrom() {
		return from;
	}
	public Date getTo() {
		return to;
	}
	public String getProvider() {
		return provider;
	}
	public Date getCur_day() {
		return cur_day;
	}
	public List<String> getState() {
		return state;
	}
	public String getBusiness_type() {
		return business_type;
	}
}
