package cn.cerestech.framework.support.pay.wxpay.gate;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.core.utils.Encrypts;
import cn.cerestech.framework.support.mp.entity.MpUser;
import cn.cerestech.framework.support.mp.service.MpConfigService;
import cn.cerestech.framework.support.mp.support.MpWebSupport;
import cn.cerestech.framework.support.pay.wxpay.publics.CommonUtil;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Controller
@RequestMapping("func")
public class FuncCtrl extends MpWebSupport{
	
	@Autowired
	MpConfigService mpConfigService;
	@RequestMapping("getEditAddressPostParam")
	public @ResponseBody void paramSign(){
		String url = getRequest("url");
		MpUser mpuser = getCurMpUser();// 获取当前的微信用户
		if (mpuser == null) {
			zipOut(Result.error().setMessage("没有找到用户").toJson());
			return;
		}
		
		String access_token = getToken().getToken();
		if(Strings.isNullOrEmpty(access_token)){
			zipOut(Result.error().setMessage("4000").toJson());
			return;
		}
		
		//获取用户
		Map<String, String> map = Maps.newHashMap();
		map.put("appid", mpConfigService.getAppid());
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
			if(!Strings.isNullOrEmpty(v)){
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
