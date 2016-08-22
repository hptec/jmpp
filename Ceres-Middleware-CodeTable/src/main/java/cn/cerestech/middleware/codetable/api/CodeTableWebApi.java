package cn.cerestech.middleware.codetable.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.starter.web.WebSupport;
import cn.cerestech.middleware.codetable.entity.Category;
import cn.cerestech.middleware.codetable.entity.Code;
import cn.cerestech.middleware.codetable.service.CodeTableService;

@RestController
@RequestMapping("api/codetable")
public class CodeTableWebApi extends WebSupport implements UserSessionOperator {

	@Autowired
	CodeTableService codeTableService;

	/**
	 * 获得当前的字典表数据
	 * 
	 * @param hashTag
	 */
	@RequestMapping("/data.js")
	@LoginRequired
	public void data() {
		List<Category> cateList = codeTableService.list();

		zipOutRequireJson(cateList);
	}

	/**
	 * 保存code
	 */
	@RequestMapping("/code/update")
	@LoginRequired
	public void codeUpdate(@RequestParam("jsonStr") String jsonStr) {
		Code code = Jsons.from(jsonStr).to(Code.class);
		Result<Code> ret = codeTableService.saveCode(code);
		if (ret.isSuccess()) {
			ret.getObject().setCategory(null);
		}
		zipOut(ret);

	}

	/**
	 * 删除code
	 */
	@RequestMapping("/code/remove")
	@LoginRequired
	public void codeRemove(@RequestParam("categoryId") Long cateogryId, @RequestParam("codeId") Long codeId) {
		codeTableService.removeCode(cateogryId, codeId);
		zipOut(Result.success());
	}

}
