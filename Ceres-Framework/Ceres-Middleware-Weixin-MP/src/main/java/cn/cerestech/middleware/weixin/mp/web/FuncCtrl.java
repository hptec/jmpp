package cn.cerestech.middleware.weixin.mp.web;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.Encrypts;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.pay.wxpay.publics.CommonUtil;
import cn.cerestech.middleware.weixin.entity.MpUser;

//@Controller
@RequestMapping("func")
public class FuncCtrl extends WebMPSupport{
//	@Autowired
//	MpConfigService mpConfig;
	
	
	@RequestMapping("getEditAddressPostParam")
	public @ResponseBody void paramSign(){
		String url = requestParam("url");
		MpUser mpuser = getCurMpUser();// 获取当前的微信用户
		if (mpuser == null) {
			zipOut(Result.error().setMessage("没有找到用户").toJson());
			return;
		}
		
		String access_token = (String)session(ACCESSTOKEN);
		if(StringUtils.isBlank(access_token)){
			zipOut(Result.error().setMessage("4000").toJson());
			return;
		}
		
		//获取用户
		Map<String, String> map = Maps.newHashMap();
//		map.put("appid", mpConfig.appid());
		map.put("url", url);
		map.put("timestamp", CommonUtil.timeStamp());
		map.put("noncestr", CommonUtil.noncestr(16));
		map.put("accesstoken", access_token);
		String sign = sign(map);
		map.put("sign", sign);
		zipOut(Result.success().setObject(map).toJson());
	}
	
	public static String sign(Map<String, String> params){
		Set<String> sets = Sets.newHashSet();
		params.forEach((k,v)->{
			if(StringUtils.isNotBlank(v)){
				sets.add(k.toLowerCase()+"="+v);
			}
		});
		String[] arr = new String[sets.size()];//sets.toArray();
		sets.toArray(arr);
		
		Arrays.sort(arr);
		StringBuffer sb = new StringBuffer();
		Arrays.asList(arr).forEach(itm->{
			sb.append(itm).append("&");
		});
		return Encrypts.sha1(sb.substring(0, sb.length()-1).toString());
	}
}
