package cn.cerestech.framework.support.mp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import cn.cerestech.framework.support.mp.annoation.MpUserRequire;
import cn.cerestech.framework.support.mp.entity.MpUser;
import cn.cerestech.framework.support.mp.entity.base.MpUserGov;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.enums.AuthorizeScope;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;
import cn.cerestech.framework.support.mp.operator.MpOperator;
import cn.cerestech.framework.support.mp.service.MpConfigService;
import cn.cerestech.framework.support.mp.service.MpuserService;
import cn.cerestech.framework.support.starter.web.WebSupport;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月6日
 */
@Component
public class MpInterceptor extends WebSupport implements HandlerInterceptor,MpOperator{
	
	@Autowired
	MpConfigService mpconfig;
	@Autowired
	MpuserService mpuserService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object requestMethod) throws Exception {
//		putSession(SESSION_MPUSER_ID_KEY, 1541L);
//		putSession(SESSION_MPUSER_OPENID, "otwEVt-T63dncpcxHkGsnIwTZePE");
		if(requestMethod instanceof HandlerMethod){
			HandlerMethod method = (HandlerMethod)requestMethod; 
			MpUserRequire clsRequire = method.getBeanType().getAnnotation(MpUserRequire.class);
			MpUserRequire mthRequire = method.getMethodAnnotation(MpUserRequire.class);
			boolean needCheck = false;
			AuthorizeScope scope = AuthorizeScope.SNSAPI_BASE;
			
			if(mthRequire != null){
				needCheck = mthRequire.require();
				if(mthRequire.force()){
					clear();
				}
				scope = mthRequire.scope();
			}else if(clsRequire != null){
				needCheck = clsRequire.require();
				if(clsRequire.force()){
					clear();
				}
				scope = clsRequire.scope();
			}
			if(needCheck){//需要检查是否是由微信浏览器2.0 认证过来的连接
				ResponseBody isDataMethod = method.getMethodAnnotation(ResponseBody.class);
				//检查缓存中是否存在
				String openid = getSessionOpenid();//从session中获取
				if(Strings.isNullOrEmpty(openid)){
					
					//开始刷新数据
					String code = getRequest("code");
					if(Strings.isNullOrEmpty(code)){//参数失败，重新跳转
						if(isDataMethod != null){//json 数据接口
							zipOut("NOT_FROM_MP");
							return false;
						}else{//页面
							redirectMpOauth(scope);
							return false;
						}
					}
					//code 参数存在获取用户
					Status<MpUserGov> status  = MemoryStrategy.of(mpconfig.getAppid(), mpconfig.getAppsecret())
						.OAUTH().snsapiUserInfo(code);
					if(status.isSuccess()){
						MpUser mpuser = status.getObject().toMpUser(mpconfig.getAppid());
						mpuserService.updateOrNew(mpuser);
						setSessionState(mpuser);
						return true;
					}else{
						if(isDataMethod != null){//json 数据接口
							zipOut("NOT_FROM_MP");
							return false;
						}else{//页面
							redirectMpOauth(scope);
							return false;
						}
					}
				}else{//open id 存在，不需要刷新数据
					return true;
				}
			}else{
				return true;
			}
		}
		return true;
	}
	
	private void redirectMpOauth(AuthorizeScope scope){
		String url = MemoryStrategy.of(mpconfig.getAppid(), mpconfig.getAppsecret())
				.OAUTH().authURL(mpconfig.getHost()+getRequestUriWithParams(), scope, "STATE");
		redirect(url);
	}

	
	
	
	
	
	
	
	
	
	
}
