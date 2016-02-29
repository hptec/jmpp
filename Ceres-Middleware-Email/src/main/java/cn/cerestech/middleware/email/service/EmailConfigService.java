package cn.cerestech.middleware.email.service;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.configuration.service.ConfigService;
import cn.cerestech.middleware.email.enums.EmailConfigKey;

/**
 * 邮件发送配置服务器
 * @author <a href="mailto:royrxc@gmail.com">Roy</a>
 * @since  2015年11月22日下午9:01:20
 */
@Service
public class EmailConfigService extends ConfigService{
	
	/**
	 * 发送服务邮箱账号
	 * @return
	 */
	public String acc(){
		return query(EmailConfigKey.EMAIL_SERVER_ACC).stringValue();
	}
	
	/**
	 * 发送服务邮箱密码
	 * @return
	 */
	public String pwd(){
		return query(EmailConfigKey.EMAIL_SERVER_PWD).stringValue();
	}
	
	/**
	 * 发件smtp 服务器地址
	 * @return
	 */
	public String smtp(){
		return query(EmailConfigKey.EMAIL_SMTP_SERVER).stringValue();
	}
	
	/**
	 * 邮件发送服务配置是否完整
	 * @return
	 */
	public boolean enable(){
		if(Strings.isNullOrEmpty(acc()) || Strings.isNullOrEmpty(pwd())){
			return false;
		}
		return true;
	}
	
}
