package cn.cerestech.framework.support.mp.service;

import cn.cerestech.framework.support.mp.entity.base.Status;
import cn.cerestech.framework.support.mp.mpapi.cache.strategy.MemoryStrategy;
import cn.cerestech.framework.support.mp.msg.client.initiative.servicemsg.MpServiceTextMsg;
import cn.cerestech.framework.support.mp.msg.client.initiative.templatemsg.MpTemplateMsg;

/**
 *
 * @author <a mailto="royrxc@gmail.com">Roy</a>
 * @since 2016年11月5日
 */
public class Testor {
	public static void main(String[] args) {
		String appid = "wx9d02e7a1789151b2";
		String appsecret = "6b96902b4fff22b974dc136277462509";
		String toUser = "otwEVt-T63dncpcxHkGsnIwTZePE";
		
		MpTemplateMsg msg = new MpTemplateMsg();
		msg.setTouser(toUser);
		msg.setTemplate_id("TMkGRxNi2TahN_Ew91ouf9NOrpcZPj42ifA55ILBT-w");
		msg.addParam("name", new MpTemplateMsg.ParamDesc("唐敏","#ed146b"));
		
		Status s = MemoryStrategy.of(appid, appsecret).TMPMSGAPI().send(msg);//getTemplates();//getProfession();//setProfession(1, 2);
		System.out.println(s.asString());
	}
}
