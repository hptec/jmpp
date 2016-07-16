package cn.cerestech.middleware.codetable.api;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Strings;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.login.annotation.LoginRequired;
import cn.cerestech.framework.support.login.operator.UserSessionOperator;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.framework.support.web.web.WebApi;
import cn.cerestech.middleware.codetable.entity.Category;
import cn.cerestech.middleware.codetable.entity.Code;
import cn.cerestech.middleware.codetable.enums.ErrorCodes;
import cn.cerestech.middleware.codetable.service.CodeTableService;

@RestController
@RequestMapping("api/codetable")
public class CodeTableWebApi extends WebApi implements UserSessionOperator {

	private Logger log = LogManager.getLogger();
	@Autowired
	CodeTableService codeTableService;

	/**
	 * 获得当前可用的字典表项目列表
	 * 
	 * @param hashTag
	 */
	@RequestMapping("/category/list")
	@LoginRequired
	public void categoryList(@RequestParam("ownerType") String ownerType) {
		if (Strings.isNullOrEmpty(ownerType)) {
			zipOut(Result.error(ErrorCodes.NOT_FOUND));
			return;
		}

		List<Category> cateList = codeTableService.list(new Owner(ownerType, getUserId()));
		cateList.forEach(c -> {
			List<Code> codes = c.getCodes() == null ? Lists.newArrayList() : c.getCodes();
			for (Code code : codes) {
				code.setCategory(null);
			}
		});
		zipOut(Result.success(cateList));

	}

}
