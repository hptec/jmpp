package cn.cerestech.middleware.article.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import cn.cerestech.console.annotation.LoginRequired;
import cn.cerestech.console.annotation.RequiredResource;
import cn.cerestech.console.web.WebConsoleSupport;
import cn.cerestech.framework.core.KV;
import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.json.Jsons;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.middleware.article.criteria.ArticleCriteria;
import cn.cerestech.middleware.article.entity.Article;
import cn.cerestech.middleware.article.enums.ErrorCodes;
import cn.cerestech.middleware.article.service.ArticleService;

@RequestMapping("$$ceres_sys/console/middleware/article")
@Controller
@RequiredResource(js = { "console/base/res/cp?id=console/res/middleware/article/ceres-pages-middleware-article.js" })
public class MiddlewareArticleConsoleCtrl extends WebConsoleSupport {

	@Autowired
	ArticleService articleService;

	@RequestMapping("search")
	@LoginRequired
	public @ResponseBody void search(@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "visible", required = false) String visibleStr,
			@RequestParam(value = "fromto", required = false) String fromto) {

		ArticleCriteria<Article> criteria = new ArticleCriteria<Article>();

		YesNo visible = null;
		if (!Strings.isNullOrEmpty(visibleStr)) {
			visible = EnumCollector.forClass(YesNo.class).keyOf(visibleStr);
			criteria.setVisible(visible);
		}
		Date fromDate = null;
		Date toDate = null;
		if (!Strings.isNullOrEmpty(fromto)) {
			List<String> dateStrList = Splitter.on(" - ").trimResults().splitToList(fromto);
			if (dateStrList.size() > 0) {
				try {
					fromDate = FORMAT_DATE.parse(dateStrList.get(0));
					criteria.setDateFrom(fromDate);
				} catch (ParseException e) {
				}
			}
			if (dateStrList.size() > 1) {
				try {
					toDate = FORMAT_DATE.parse(dateStrList.get(1));
					criteria.setDateTo(toDate);
				} catch (ParseException e) {
				}
			}
		}

		criteria.setCategory(category);
		criteria.setMaxRecords(BaseEntity.MAX_QUERY_RECOREDS);
		criteria = articleService.search(Article.class, criteria);

		zipOut(Jsons.toJson(criteria.getData()));
	}

	@RequestMapping("get")
	@LoginRequired
	public @ResponseBody void get(@RequestParam(value = "id", required = false) Long id) {
		KV retMap = KV.on();

		if (id != null) {
			retMap.put("article", mysqlService.queryById(Article.class, id));
		}

		retMap.put("yesno", EnumCollector.forClass(YesNo.class).toList());
		EnumCollector ec = EnumCollector.forClass(EnumCollector.queryClassByCategory(ArticleService.CATEGORY_KEYWORD));
		retMap.put("categorys", ec.toList());

		zipOut(Jsons.toJson(retMap));
	}

	@RequestMapping("update")
	@LoginRequired
	public @ResponseBody void get(Article article) {
		if (article == null) {
			zipOut(Result.error(ErrorCodes.ARTICLE_NOT_EXIST));
			return;
		}

		if (Strings.isNullOrEmpty(article.getTitle())) {
			zipOut(Result.error(ErrorCodes.TITLE_REQUIRED));
			return;
		}

		if (article.getTitle().length() >= 200) {
			zipOut(Result.error(ErrorCodes.TITLE_LENGTH_LESS_THAN_200));
			return;
		}

		if (Strings.isNullOrEmpty(article.getCategory())) {
			zipOut(Result.error(ErrorCodes.CATEGORY_REQUIRED));
			return;
		}

		article.setContent(Strings.nullToEmpty(article.getContent().replace("'", "")));// 去除'

		if (article.getId() == null) {
			article.setCreate_time(new Date());
			article.setCreator_id(userId());
			mysqlService.insert(article);
		} else {
			mysqlService.update(article);
		}

		zipOut(Result.success(article).toJson());
	}

	@RequestMapping("remove")
	public @ResponseBody void remove(@RequestParam(value = "id", required = false) Long id) {
		mysqlService.delete(Article.class, id);
		zipOut(Result.success().toJson());
	}
}
