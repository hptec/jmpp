package cn.cerestech.middleware.email.service;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.Dates;
import cn.cerestech.framework.core.EmailUtils;
import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.middleware.email.entity.EmailRecord;
import cn.cerestech.middleware.email.enums.Email;
import cn.cerestech.middleware.email.enums.EmailState;
import cn.cerestech.middleware.email.enums.EmailType;
import cn.cerestech.middleware.email.enums.ErrorCodes;
import cn.cerestech.middleware.email.sender.EmailServer;

/**
 * 邮件发送
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年11月22日下午8:29:25
 */
@Service
public class EmailService extends BaseService{
	
	@Autowired
	EmailConfigService config;
	
	public Result<EmailRecord> plan(EmailRecord email){
		if(email == null){
			return Result.error(ErrorCodes.EMAIL_NFD);
		}

		if(!EmailUtils.isEmail(email.getSend_to())){
			return Result.error(ErrorCodes.EMAIL_ACC_ERR);
		}
		
		if(Strings.isNullOrEmpty(email.getContent())){
			return Result.error(ErrorCodes.EMAIL_NFD).setMessage("内容为空");
		}
		
		if(email.getSend_time() == null){
			email.setSend_time(Dates.add(new Date(), Calendar.MINUTE, 5));
		}
		
		email.setType(EmailType.PLAN.key());
		email.setState(EmailState.WAIT.key());
		
		process(email);
		return Result.success().setObject(email);
	}
	/**
	 * 及时发送，模板邮件
	 * @param toEmail
	 * @param plannedTime
	 * @param templateId
	 * @param args
	 * @return
	 */
	public Result<EmailRecord> send(String toEmail, Date plannedTime, String templateId, String... args){
		if(Strings.isNullOrEmpty(templateId)){
			return Result.error().setMessage("邮件内容模板未找到");
		}
		String template = Email.template(templateId, args);
		if(Strings.isNullOrEmpty(template)){
			return Result.error().setMessage("邮件内容模板未找到");
		}
		EmailRecord email = new EmailRecord();
		email.setSend_to(toEmail);
		email.setContent(template);
		email.setKey(templateId);
		email.setSend_time(plannedTime);
		if(plannedTime == null){
			email.setType(EmailType.TIMELY.key());
		}else{
			email.setType(EmailType.PLAN.key());
			return plan(email);
		}
		
		return send(email);
	}
	
	/**
	 * 邮件发送
	 * @param email
	 * @return
	 */
	public Result<EmailRecord> send(EmailRecord email){
		if(Strings.isNullOrEmpty(config.acc()) || Strings.isNullOrEmpty(config.pwd())){
			return Result.error(ErrorCodes.EMAIL_SERVICE_DISABLE);
		}
		if(email == null){
			return Result.error(ErrorCodes.EMAIL_NFD);
		}
		
		if(!EmailUtils.isEmail(email.getSend_to())){
			return Result.error(ErrorCodes.EMAIL_ACC_ERR);
		}
		
		if(Strings.isNullOrEmpty(email.getContent())){
			return Result.error(ErrorCodes.EMAIL_NFD).setMessage("内容为空");
		}
		
		EmailServer server = EmailServer.of(config.acc(), config.pwd()).setServer(config.smtp());
		
		Result res = server.send(email.getSend_to(), email.getSubject(), email.getContent());
		
		email.setState(EmailState.FAIL.key());
		email.setDesc(res.getMessage());
		email.setSend_time(new Date());
		if(EmailType.keyOf(email.getType()) == null){
			email.setType(EmailType.TIMELY.key());
			email.setSend_time(new Date());
		}
		
		Result<EmailRecord> resBack = Result.success();
		if(!res.isSuccess()){
			email.setState(EmailState.FAIL.key());
			email.setDesc(res.getMessage());
			resBack = Result.error(ErrorCodes.EMAIL_SEND_ERR).setMessage(res.getMessage());
		}else{
			email.setState(EmailState.SUCCESS.key());
		}
		process(email);
		
		return resBack.setObject(email);
	}
	
	public EmailRecord process(EmailRecord email){
		if(email!=null){
			if(email.getId() == null){
				mysqlService.insert(email);
			}else{
				mysqlService.update(email);
			}
		}
		return email;
	}
	
	
}
