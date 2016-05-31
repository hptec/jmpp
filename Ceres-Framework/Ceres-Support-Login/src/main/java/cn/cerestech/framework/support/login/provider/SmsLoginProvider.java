package cn.cerestech.framework.support.login.provider;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.dao.LoginDao;
import cn.cerestech.framework.support.login.entity.Login;
import cn.cerestech.framework.support.login.entity.Loginable;
import cn.cerestech.framework.support.login.enums.ErrorCodes;
import cn.cerestech.framework.support.web.operator.SessionOperator;

public abstract class SmsLoginProvider<T extends Loginable> implements LoginProvider<T>, SessionOperator {

	public static final String LOGIN_SESSION_SMS_MOBILE = "LOGIN_SESSION_SMS_MOBILE";// 发送验证短信后保存在Session中电话号码的值
	public static final String LOGIN_SESSION_SMS_CODE = "LOGIN_SESSION_SMS_CODE";// 发送验证短信后保存在Session中代码的值

	private Login fromLogin = null;

	/**
	 * 当校验完成需要创建用户的时候，调用此方法进行创建，
	 * 
	 * @return 创建记录的ID，必须保证数据库中有记录。如果返回null,则默认从数据库查找，数据库中没有的话，会报错
	 */
	abstract public Long onRegisterRequired();

	@Override
	public Result<Long> validate() {

		LoginDao<T> dao = getDao();
		if (dao == null || fromLogin == null || fromLogin.isEmpty()) {
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		// 验证短信验证码
		String sys_phone = getSession("login_or_reg_phone");
		String sys_code = getSession("login_or_reg");

		if (fromLogin != null && fromLogin.isEmpty()) {
			return Result.error(ErrorCodes.LOGIN_FAILED);
		}

		if (!fromLogin.getId().equals(sys_phone) || !fromLogin.getPwd().equals(sys_code)) {
			return Result.error(ErrorCodes.SMS_CODE_ERROR);
		}
		// 检查是否创建用户
		Long id = onRegisterRequired();
		if (id == null) {
			// 从数据库获取用户对象
			T t = dao.findUniqueByLoginId(fromLogin.getId());
			if (t == null) {
				return Result.error(ErrorCodes.LOGIN_FAILED);
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
	abstract public Login getLogin();

}
