package cn.cerestech.middleware.helpcenter.simple.web;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.annotation.RequiredResource;
import cn.cerestech.console.web.WebConsoleSupport;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.middleware.helpcenter.simple.entity.HelpCenterCategory;
import cn.cerestech.middleware.helpcenter.simple.entity.HelpCenterQA;
import cn.cerestech.middleware.helpcenter.simple.enums.ErrorCodes;
import cn.cerestech.middleware.helpcenter.simple.service.HelpCenterService;

@RequestMapping("$$ceres_sys/console/helpcenter")
@Controller
@RequiredResource(js = {
		"console/base/res/cp?id=console/res/middleware/helpcenter/simple/ceres-pages-middleware-helpcenter.js" })
public class HelpCenterConsoleCtrl extends WebConsoleSupport {

	@Autowired
	HelpCenterService helpCenterService;

	@RequestMapping("category/firstlevel")
	@LoginRequired
	public @ResponseBody void categoryFirstLevel() {

		List<HelpCenterCategory> retList = helpCenterService.queryCategoryFirstLevel();
		zipOut(Jsons.toJson(retList));
	}

	@RequestMapping("category/search")
	@LoginRequired
	public @ResponseBody void categorySearch(@RequestParam(value = "parent_id", required = false) Long pid) {

		List<HelpCenterCategory> retList = helpCenterService.queryCategoryFlattedStructure().stream().filter(cate -> {
			if (pid == null) {
				return Boolean.TRUE;
			} else {
				return pid.equals(cate.getParent_id());
			}
		}).collect(Collectors.toList());

		KV kv = KV.on();
		kv.put("data", retList);
		kv.put("parent", mysqlService.queryBy(HelpCenterCategory.class));

		zipOut(kv.toJson());
	}

	@RequestMapping("category/get")
	@LoginRequired
	public @ResponseBody void categoryGet(@RequestParam(value = "id", required = false) Long id) {
		KV kv = KV.on();
		if (id != null) {
			kv.put("category", mysqlService.queryById(HelpCenterCategory.class, id));
		}
		kv.put("allcate", helpCenterService.queryCategoryFirstLevel());
		kv.put("yes_no", EnumCollector.forClass(YesNo.class).toList());

		zipOut(Jsons.toJson(kv));
	}

	@RequestMapping("category/confirm")
	@LoginRequired
	public @ResponseBody void categoryConfirm(HelpCenterCategory category) {
		if (Strings.isNullOrEmpty(category.getName())) {
			zipOut(Result.error(ErrorCodes.CATEGORY_NAME_NOT_EXIST).toJson());
			return;
		}

		if (category.getParent_id() == null) {
			category.setParent_id(-1L);
		}

		if (Strings.isNullOrEmpty(category.getVisible())) {
			category.setVisible(YesNo.NO.key());
		}

		if (category.getCreate_time() == null) {
			category.setCreate_time(new Date());
		}

		if (category.getId() == null) {
			mysqlService.insert(category);
		} else {
			mysqlService.update(category);
		}

		zipOut(Result.success(category).toJson());
	}

	@RequestMapping("category/remove")
	@LoginRequired
	public @ResponseBody void categoryRemove(@RequestParam(value = "id", required = false) Long id) {
		zipOut(helpCenterService.removeCategory(id).toJson());
	}

	@RequestMapping("question/search")
	@LoginRequired
	public @ResponseBody void questionSearch(@RequestParam(value = "category_id", required = false) Long category_id,
			@RequestParam(value = "visible", required = false) String visibleStr) {

		YesNo visible = null;
		if (!Strings.isNullOrEmpty(visibleStr)) {
			visible = EnumCollector.forClass(YesNo.class).keyOf(visibleStr);
		}

		List<HelpCenterQA> retList = helpCenterService.searchQuestion(HelpCenterQA.class, category_id, visible, null);

		KV kv = KV.on();
		kv.put("data", retList);
		kv.put("categorys", mysqlService.queryBy(HelpCenterCategory.class));

		zipOut(kv.toJson());
	}

	@RequestMapping("question/get")
	@LoginRequired
	public @ResponseBody void questionGet(@RequestParam(value = "id", required = false) Long id) {
		KV kv = KV.on();
		if (id != null) {
			kv.put("question", mysqlService.queryById(HelpCenterQA.class, id));
		} else {
			HelpCenterQA question = new HelpCenterQA();
			question.setVisible(YesNo.YES.key());
			kv.put("question", question);
		}
		kv.put("categorys", helpCenterService.queryCategoryFlattedStructure());
		kv.put("yes_no", EnumCollector.forClass(YesNo.class).toList());

		zipOut(Jsons.toJson(kv));
	}

	@RequestMapping("question/confirm")
	@LoginRequired
	public @ResponseBody void questionConfirm(HelpCenterQA question) {
		if (Strings.isNullOrEmpty(question.getQuestion())) {
			zipOut(Result.error(ErrorCodes.QUESTION_TITLE_REQUIRED).toJson());
			return;
		}

		if (question.getCategory_id() == null) {
			zipOut(Result.error(ErrorCodes.QUESTION_CATEGORY_REQUIRED).toJson());
			return;
		}

		if (Strings.isNullOrEmpty(question.getVisible())) {
			question.setVisible(YesNo.NO.key());
		}

		if (question.getCreate_time() == null) {
			question.setCreate_time(new Date());
		}
		

		if (question.getId() == null) {
			mysqlService.insert(question);
		} else {
			mysqlService.update(question);
		}

		zipOut(Result.success(question).toJson());
	}

	@RequestMapping("question/remove")
	@LoginRequired
	public @ResponseBody void questionRemove(@RequestParam(value = "id", required = false) Long id) {
		mysqlService.delete(HelpCenterQA.class, id);
		zipOut(Result.success().toJson());
	}
}
