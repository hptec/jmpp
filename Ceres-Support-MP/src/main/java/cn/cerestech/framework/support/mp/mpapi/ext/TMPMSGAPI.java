package cn.cerestech.framework.support.mp.mpapi.ext;

import java.util.List;

import cn.cerestech.framework.support.mp.entity.base.MpTemplate;
import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.TplMsgAPI;
import cn.cerestech.framework.support.mp.mpapi.cache.Executor;
import cn.cerestech.framework.support.mp.msg.client.initiative.templatemsg.MpTemplateMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月5日
 */
public class TMPMSGAPI extends TplMsgAPI{
	private Executor exec;

	public TMPMSGAPI(Executor exec) {
		super(exec.getAppid(), exec.getAppsecret());
		this.exec = exec;
	}
	
	/**
	 * 设置行行业，并返回是否成功
	 * 
	 * @param access_token
	 * @param primary_profession
	 * @param second_profession
	 * @return
	 */
	public Status<String> setProfession(int primary_profession, int second_profession) {
		return this.exec.execute(token->{
			return this.setProfession(token.getToken(), primary_profession, second_profession);
		});
	}

	/**
	 * 获取当前公众号的行业信息
	 * 
	 * @param access_token
	 * @return { "primary_industry":{"first_class":"运输与仓储","second_class":"快递"},
	 *         	 "secondary_industry":{"first_class":"IT科技","second_class":"互联网|电子商务"} 
	 *         }
	 */
	public Status<String> getProfession() {
		return this.exec.execute(token->{
			return this.getProfession(token.getToken());
		});
	}
	/**
	 * 获取行业模板
	 * @param access_token
	 * @param tempid_short
	 * @return
	 * 	如果获取成功，模板id 体现在Status 的 object 中
	 */
	public Status<String> getTemplate(String tempid_short){
		return this.exec.execute(token->{
			return this.getTemplate(token.getToken(),tempid_short);
		});
	}
	
	/**
	 * 获取模板列表
	 * @param access_token
	 * @return
	 */
	public Status<List<MpTemplate>> getTemplates(){
		return this.exec.execute(token->{
			return this.getTemplates(token.getToken());
		});
	}
	
	/**
	 * 删除模板
	 * @param access_token
	 * @param template_id
	 * @return
	 */
	public Status<String> delTemplate(String template_id){
		return this.exec.execute(token->{
			return this.delTemplate(token.getToken(),template_id);
		});
	}
	
	/**
	 * 发送模板消息
	 * @param access_token
	 * @param msg
	 * @return
	 * 成功则返回消息msgId ， 否则返回错误信息
	 */
	public Status<String> send(MpTemplateMsg msg){
		return this.exec.execute(token->{
			return this.send(token.getToken(),msg);
		});
	}

}
