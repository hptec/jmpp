package cn.cerestech.framework.support.login.provider;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.parser.DefaultPropertiesTemplateParser;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.utils.KV;
import cn.cerestech.framework.core.utils.Random;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.entity.LoginField;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.enums.LoginErrorCodes;
import cn.cerestech.framework.support.starter.operator.RequestOperator;
import cn.cerestech.framework.support.starter.operator.SessionOperator;
import cn.cerestech.middleware.location.mobile.Mobile;
import cn.cerestech.middleware.location.operator.IpOperator;
import cn.cerestech.middleware.sms.entity.SmsRecord;
import cn.cerestech.middleware.sms.service.SmsService;

/**
 * 默认提交参数值 phone/code
 * 
 * @author harryhe
 *
 * @param <T>
 */
public abstract class SmsLoginProvider<T extends Loginable>
		implements LoginProvider<T>, SessionOperator, RequestOperator, IpOperator {

	public static final String LOGIN_SESSION_SMS_MOBILE = "LOGIN_SESSION_SMS_MOBILE";// 发送验证短信后保存在Session中电话号码的值
	public static final String LOGIN_SESSION_SMS_CODE = "LOGIN_SESSION_SMS_CODE";// 发送验证短信后保存在Session中代码的值

	public static final String LOGIN_PHONE = "phone";// 登录电话
	public static final String LOGIN_CODE = "code";// 登录验证码

	private Logger log = LogManager.getLogger();

	/**
	 * 当校验完成需要创建用户的时候，调用此方法进行创建，
	 * 
	 * @return 创建记录的ID，必须保证数据库中有记录。如果返回null,则默认从数据库查找，数据库中没有的话，会报错
	 */
	abstract public Long onRegisterRequired(Login login);

	@Autowired
	LoginDao<T> loginDao;

	@Override
	public Result<Long> validate() {

		Login fromLogin = getLogin();
		if (fromLogin == null || fromLogin.isEmpty()) {
			return Result.error(LoginErrorCodes.LOGIN_FAILED);
		}

		// 验证短信验证码
		String sys_phone = getSession(LOGIN_SESSION_SMS_MOBILE);
		String sys_code = getSession(LOGIN_SESSION_SMS_CODE);

		if (!fromLogin.getId().equals(sys_phone) || !fromLogin.getPwd().equals(sys_code)) {
			return Result.error(LoginErrorCodes.SMS_CODE_ERROR);
		}
		// 检查是否创建用户
		Long id = onRegisterRequired(fromLogin);
		if (id == null) {
			// 从数据库获取用户对象
			T t = loginDao.findUniqueByLoginIdIgnoreCase(fromLogin.getId());
			if (t == null) {
				return Result.error(LoginErrorCodes.LOGIN_FAILED);
			}
			id = t.getId();
		}

		return Result.success(id);
	}

	/**
	 * 获取用户提交的登录信息（手机号和验证码)
	 * 
	 * @return
	 */
	public Login getLogin() {
		String phone = getRequest(LOGIN_PHONE);
		String code = getRequest(LOGIN_CODE);

		return Login.from(phone, code);
	};

	public Result<SmsRecord> sendSmsCode(Mobile to) {
		if (!to.legal()) {
			return Result.error(cn.cerestech.middleware.location.enums.ErrorCodes.PHONE_ILLEGAL);
		}

		String code = Random.number(4);
		String content = new DefaultPropertiesTemplateParser("login_or_reg").parse(KV.on().put("code", code));
		Result<SmsRecord> res = getSmsService().send(to, content, getIp());
		log.trace("发送短信 号码：" + to.fullNumber() + " 验证码：" + code);
		putSession(SmsLoginProvider.LOGIN_SESSION_SMS_CODE, code);
		putSession(SmsLoginProvider.LOGIN_SESSION_SMS_MOBILE, to.number());

		return res;
	}

	abstract public String getSmsCodeTemplate();

	abstract public SmsService getSmsService();

	@Override
	public List<LoginField> getLoginFields() {
		List<LoginField> fields = Lists.newArrayList();
		fields.add(new LoginField("tel", LOGIN_PHONE, "登录电话号码"));
		fields.add(new LoginField("tel", LOGIN_CODE, "验证码"));
		return fields;
	}

}
