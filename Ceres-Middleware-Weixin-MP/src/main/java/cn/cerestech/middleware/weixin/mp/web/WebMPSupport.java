package cn.cerestech.middleware.weixin.mp.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.cerestech.framework.web.support.WebSupport;
import cn.cerestech.middleware.weixin.entity.MpUser;
import cn.cerestech.middleware.weixin.mapper.MpUserMapper;
import cn.cerestech.middleware.weixin.mp.service.WeiXinMpService;
import cn.cerestech.middleware.weixin.service.WeiXinService;

/**
 * 获取微信数据
 * 
 * @author bird
 *
 */
public abstract class WebMPSupport extends WebSupport {
	public static final String OPENID = "cur_session_openId";
	public static final String OPENID_COOKIE = "cur_user_oid";
	public static final String ACCESSTOKEN = "cur_session_access_token";
	public static final String REFRESH_TOKEN = "refresh_token";

	@Autowired
	MpUserMapper mpuserMapper;
	@Autowired
	WeiXinService weixinService;

	@Autowired
	WeiXinMpService weixinMpService;

	protected MpUser getCurMpUser() {
		String oid = (String) session(OPENID);
		if (StringUtils.isNotBlank(oid)) {
			return mpuserMapper.queryMpUserByOpenId(oid);
		}
		return null;
	}

	public synchronized void synchMpuser(String openid) {
		weixinMpService.synchronizedMpuser(openid);
	}

	/**
	 * 初始化参数，绑定微信的相关的参数
	 */
	public void init() {
		getCurMpUser();
	}

	protected String mpError(String msg) {
		return mpError(msg, "抱歉，出错了");
	}

	protected String mpError(String msg, String title) {
		getRequest().setAttribute("error_message", msg);
		return "error_info";
	}
}
