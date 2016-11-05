package cn.cerestech.framework.support.mp.mpapi;

import java.util.List;
import java.util.Map;

import org.apache.http.entity.ByteArrayEntity;

import com.beust.jcommander.internal.Maps;
import com.google.gson.reflect.TypeToken;

import cn.cerestech.framework.core.http.Https;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.support.mp.entity.base.MpTemplate;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.msg.client.initiative.templatemsg.MpTemplateMsg;

/**
 * 模板消息
 * 
 * 行业模板(行业代码参考)
 *  ------------------------------------------------------
 *   		主行业	 	| 		副行业 		| 		代码	
 * ------------------------------- -----------------------
 * 	IT科技 					互联网/电子商务 			1 
 * 	IT科技 					IT软件与服务 				2 
 * 	IT科技 					IT硬件与设备 				3
 * 	IT科技 					电子技术 					4 
 * 	IT科技					通信与运营商 				5 
 * 	IT科技 					网络游戏 					6 
 * 	金融业 					银行 					7 
 * 	金融业 					基金|理财|信托 			8 
 * 	金融业 					保险	 					9 
 * 	餐饮 					餐饮						10
 * 	酒店旅游 					酒店 					11 
 * 	酒店旅游 					旅游 					12 
 * 	运输与仓储 				快递 					13 
 * 	运输与仓储 				物流 					14 
 * 	运输与仓储 				仓储 					15 
 * 	教育						培训 					16 
 * 	教育						院校 					17 
 * 	政府与公共事业 			学术科研 					18 
 * 	政府与公共事业 			交警 					19 
 * 	政府与公共事业 			博物馆 					20 
 * 	政府与公共事业 			公共事业|非盈利机构 		21 
 * 	医药护理 					医药医疗 					22 
 * 	医药护理 					护理美容 					23 
 * 	医药护理 					保健与卫生 				24 
 * 	交通工具 					汽车相关 					25 
 * 	交通工具 					摩托车相关 				26 
 * 	交通工具 					火车相关 					27
 * 	交通工具 					飞机相关 					28 
 * 	房地产 					建筑 					29 
 * 	房地产 					物业 					30 
 * 	消费品 					消费品 					31 
 * 	商业服务 					法律 					32 
 * 	商业服务 					会展 					33 
 * 	商业服务 					中介服务 					34 
 * 	商业服务 					认证 					35 
 * 	商业服务 					审计 					36 
 * 	文体娱乐 					传媒 					37 
 * 	文体娱乐 					体育 					38 
 * 	文体娱乐 					娱乐休闲 					39 
 * 	印刷 					印刷 					40 
 * 	其它 					其它 					41
 * --------------------------------------------------------
 * 
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月5日
 */
public class TplMsgAPI extends API {
	public TplMsgAPI(String appid, String appsecret) {
		super(appid, appsecret);
	}

	public static TplMsgAPI of(String appid, String appsecret) {
		return new TplMsgAPI(appid, appsecret);
	}

	/**
	 * 设置行行业，并返回是否成功
	 * 
	 * @param access_token
	 * @param primary_profession
	 * @param second_profession
	 * @return
	 */
	public Status<String> setProfession(String access_token, int primary_profession, int second_profession) {
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + access_token;
		Map<String, String> params = Maps.newHashMap();
		params.put("industry_id1", "" + primary_profession);
		params.put("industry_id2", "" + second_profession);
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(params).toJson().getBytes())).readString();
		return Status.as(ret);
	}

	/**
	 * 获取当前公众号的行业信息
	 * 
	 * @param access_token
	 * @return { "primary_industry":{"first_class":"运输与仓储","second_class":"快递"},
	 *         	 "secondary_industry":{"first_class":"IT科技","second_class":"互联网|电子商务"} 
	 *         }
	 */
	public Status<String> getProfession(String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" + access_token;
		String ret = Https.of().get(url).readString();
		return Status.as(ret);
	}
	/**
	 * 获取行业模板
	 * @param access_token
	 * @param tempid_short
	 * @return
	 * 	如果获取成功，模板id 体现在Status 的 object 中
	 */
	public Status<String> getTemplate(String access_token, String tempid_short){
		String url ="https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token="+access_token;
		Map<String, Object> params = Maps.newHashMap();
		params.put("template_id_short", tempid_short);
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(params).toJson().getBytes())).readString();
		/**
		 * {
	           "errcode":0,
	           "errmsg":"ok",
	           "template_id":"Doclyl5uP7Aciu-qZ7mJNPtWkbkYnWBWVja26EGbNyk"
	       }
		 */
		Status<String> s = Status.as(ret);
		if(s.isSuccess()){
			s.setObject(s.jsonValue("template_id"));
		}
		return s;
	}
	
	/**
	 * 获取模板列表
	 * @param access_token
	 * @return
	 */
	public Status<List<MpTemplate>> getTemplates(String access_token){
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token="+access_token;
		String ret = Https.of().get(url).readString();
		Status<List<MpTemplate>> status = Status.as(ret);
		if(status.isSuccess()){
			List<MpTemplate> datas = Jsons.from(status.jsonValue("template_list")).to(new TypeToken<List<MpTemplate>>() {});
			status.setObject(datas);
		}
		return status;
	}
	
	/**
	 * 删除模板
	 * @param access_token
	 * @param template_id
	 * @return
	 */
	public Status<String> delTemplate(String access_token, String template_id){
		String url = "https://api,weixin.qq.com/cgi-bin/template/del_private_template?access_token="+access_token;
		MpTemplate t = new MpTemplate();
		t.setTemplate_id(template_id);
		String ret = Https.of().post(url, new ByteArrayEntity(Jsons.from(t).toJson().getBytes())).readString();
		return Status.as(ret);
	}
	
	/**
	 * 发送模板消息
	 * @param access_token
	 * @param msg
	 * @return
	 * 成功则返回消息msgId ， 否则返回错误信息
	 */
	public Status<String> send(String access_token, MpTemplateMsg msg){
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
		String ret = Https.of().post(url, new ByteArrayEntity(msg.reply().getBytes())).readString();
		Status<String> status = Status.as(ret);
		if(status.isSuccess()){
			status.setObject(status.jsonValue("msgid"));
		}
		return status;
	}

}
