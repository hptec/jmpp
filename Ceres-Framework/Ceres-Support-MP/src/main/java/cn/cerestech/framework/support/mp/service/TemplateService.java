package cn.cerestech.framework.support.mp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;
import cn.cerestech.framework.support.mp.msg.client.initiative.templatemsg.MpTemplateMsg;

@Service
public class TemplateService{
	
	@Autowired
	MpConfigService mpConfig;
	/**
	 * 发送模板消息，方法名说的是要有个发送方法，其他的没有定义。根据情况修改。
	 * 
	 * @param key
	 * @param param
	 * @return
	 */
	public Status<String> send(String to_mpuser_openid, String template_id, ParamDesc...pds) {
		MpTemplateMsg msg = new MpTemplateMsg();
		msg.setTouser(to_mpuser_openid);
		msg.setTemplate_id(template_id);
		if(pds != null && pds.length > 0){
			for (ParamDesc pd : pds) {
				msg.addParam(pd.getField(), new MpTemplateMsg.ParamDesc(pd.getValue(),pd.getColor()));
			}
		}
		return MemoryStrategy.of(mpConfig.getAppid(), mpConfig.getAppsecret()).TMPMSGAPI().send(msg);
	}
	
	public class ParamDesc{
		private String field;
		private String value;
		private String color;
		public String getField() {
			return field;
		}
		public void setField(String field) {
			this.field = field;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getColor() {
			return color;
		}
		public void setColor(String color) {
			this.color = color;
		}
		
	}
}
