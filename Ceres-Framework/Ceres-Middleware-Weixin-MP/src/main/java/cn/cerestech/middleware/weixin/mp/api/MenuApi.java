package cn.cerestech.middleware.weixin.mp.api;

import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.tools.ant.filters.StringInputStream;

import cn.cerestech.framework.core.HttpUtils;
import cn.cerestech.framework.core.Logable;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.middleware.weixin.entity.Status;
import cn.cerestech.middleware.weixin.mp.entity.Menu;

public class MenuApi implements Logable {

	private MpApi parent;

	public MenuApi(MpApi mpApi) {
		parent = mpApi;
	}

	/**
	 * 定义菜单并且返回状态码
	 * 
	 * @param accessToken
	 * @param menu
	 * @return
	 */
	public Status create(Menu menu) {
		return parent.execute(token -> {
			StringBuffer url = new StringBuffer("https://api.weixin.qq.com/cgi-bin/menu/create?");
			url.append("access_token=" + token);

			Status statusReturn = Status.ABSENT;
			if (menu == null) {
				statusReturn.setMsg("菜单不能为空");
				return statusReturn;
			}

			String menuString = Jsons.toJson(menu, false, false);
			if (log.isTraceEnabled()) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("MpApi Request: [create menu] ");
				buffer.append(url);
				buffer.append("\nDATA: " + Jsons.toPrettyJson(menu.toJson()));
				log.trace(buffer.toString());
			}
			//InputStreamEntity ent = new InputStreamEntity(new StringInputStream(menuString));
			ByteArrayEntity ent = new ByteArrayEntity(menuString.getBytes());
			String res = HttpUtils.post(url.toString(), ent);
			String str = Jsons.toPrettyJson(Jsons.from(res));
			log.trace("MpApi Response: \n" + str);
			statusReturn = Status.as(res);
			return statusReturn;
		});

	}

}
