package cn.cerestech.middleware.codetable.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beust.jcommander.internal.Lists;

import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.web.web.WebApi;
import cn.cerestech.middleware.codetable.entity.Category;
import cn.cerestech.middleware.codetable.entity.Code;
import cn.cerestech.middleware.codetable.service.CodeTableService;
import cn.cerestech.middleware.codetable.wrapper.CodeTableWrapper;

@RestController
@RequestMapping("api/codetable")
public class CodeTableWebApi extends WebApi implements UserSessionOperator {

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

		zipOutRequireJson(new CodeTableWrapper(cateList).doWrap());
	}

	/**
	 * 获得当前可用的字典表项目列表
	 * 
	 * @param hashTag
	 */
	@RequestMapping("/category/list")
	@LoginRequired
	public void categoryList() {
		List<Category> cateList = codeTableService.list();
		cateList.forEach(c -> {
			List<Code> codes = c.getCodes() == null ? Lists.newArrayList() : c.getCodes();
			for (Code code : codes) {
				code.setCategory(null);
			}
		});
		zipOut(Result.success(cateList));

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
