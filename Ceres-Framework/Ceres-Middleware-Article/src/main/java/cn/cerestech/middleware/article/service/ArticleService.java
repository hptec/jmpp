package cn.cerestech.middleware.article.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.middleware.article.criteria.ArticleCriteria;
import cn.cerestech.middleware.article.entity.Article;

@Service
public class ArticleService extends BaseService {
	public static final String CATEGORY_KEYWORD = "ARTICLE";

	public <T extends Article> ArticleCriteria<T> search(Class<T> clazz, ArticleCriteria<T> criteria) {
		StringBuffer where = new StringBuffer(" 1=1 ");

		if (criteria.getCreator_id() != null) {
			where.append(" AND creator_id=" + criteria.getCreator_id());
		}

		if (!Strings.isNullOrEmpty(criteria.getCategory())) {
			where.append(" AND category='" + criteria.getCategory() + "'");
		}

		if (!Strings.isNullOrEmpty(criteria.getVisible())) {
			where.append(" AND `visible` = '" + criteria.getVisible() + "'");
		}

		if (criteria.getDateFrom() != null) {
			where.append(
					" AND create_time >= timestamp('" + FORMAT_DATE.format(criteria.getDateFrom()) + " 00:00:00')");
		}

		if (criteria.getDateTo() != null) {
			where.append(" AND create_time <= timestamp('" + FORMAT_DATE.format(criteria.getDateTo()) + " 23:59:49')");
		}
		
		StringBuffer count_sql = where;
		criteria.setCount(mysqlService.count(clazz, count_sql.toString()));
		
		
		// 排序
		if (!Strings.isNullOrEmpty(criteria.getOrderBy())) {
			where.append(" ORDER BY ");
			where.append(criteria.getOrderBy());
		}

		// 组装分页
		if (criteria.getPage() != null) {
			where.append(" LIMIT " + criteria.getOffset()+ "," + criteria.getPageSize());
		}

		List<T> result = mysqlService.queryBy(clazz, where.toString());
		
		criteria.addData(result);
		
		return criteria;
	}
}
