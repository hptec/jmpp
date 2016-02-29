package cn.cerestech.middleware.email.monitor;

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.core.monitor.AbstractMonitor;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.middleware.email.entity.EmailRecord;
import cn.cerestech.middleware.email.enums.EmailState;
import cn.cerestech.middleware.email.enums.EmailType;
import cn.cerestech.middleware.email.mapper.EmailMapper;
import cn.cerestech.middleware.email.service.EmailService;

@Service
public class EmailMonitor extends AbstractMonitor<EmailRecord> {
	@Autowired
	EmailMapper emailMapper;
	@Autowired
	EmailService emailService;
	
	
	@Override
	public Supplier<List<EmailRecord>> getReloader() {
		return ()->{
			return emailMapper.search(EmailType.PLAN.key(), 180L, null, null, EmailState.WAIT.key());
		};
	}

	@Override
	public Function<EmailRecord, Boolean> getMonitor() {
		return email->{
			if(email.getSend_time() == null || !EmailState.WAIT.equals(EmailState.keyOf(email.getState()))){//为空则直接跳过该数据，表示该数据没有设置开标时间
				return Boolean.TRUE;
			}else if(Dates.after(new Date(), email.getSend_time())){
				Result res = emailService.send(email);
				if(res == null){
					return Boolean.FALSE;
				}else{
					return res.isSuccess();
				}
			}else{
				return Boolean.FALSE;
			}
		};
	}

	@Override
	public Boolean isRunable() {
		if (getLastRunTime() == null) {
			return Boolean.TRUE;
		} else {
			return System.currentTimeMillis() > (getLastRunTime().getTime() + 180 * 1000L);
		}
	}

}
