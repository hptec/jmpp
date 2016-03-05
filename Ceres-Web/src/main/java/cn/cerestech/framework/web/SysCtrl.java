package cn.cerestech.framework.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.web.support.WebSupport;

@Controller
@RequestMapping(value = "/infra")
public class SysCtrl extends WebSupport {

	/**
	 * 返回窗口小组件的文本内容
	 * 
	 * @param values
	 * @return
	 */
	@RequestMapping(value = "widget")
	public @ResponseBody String widget(@RequestParam(value = "n", required = true, defaultValue = "") String values, @RequestParam(value = "pageId", required = false, defaultValue = "") String pageId) {
		Map<String, Object> retMap = Maps.newHashMap();
		retMap.put("pageId", pageId);
		return Jsons.toJson(retMap);
	}
}
