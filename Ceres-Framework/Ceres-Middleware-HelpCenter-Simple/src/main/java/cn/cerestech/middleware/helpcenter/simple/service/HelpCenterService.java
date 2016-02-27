package cn.cerestech.middleware.helpcenter.simple.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.persistence.entity.BaseEntity;
import cn.cerestech.middleware.helpcenter.simple.entity.HelpCenterCategory;
import cn.cerestech.middleware.helpcenter.simple.entity.HelpCenterQA;
import cn.cerestech.middleware.helpcenter.simple.entity.HelpCenterQACriteria;
import cn.cerestech.middleware.helpcenter.simple.enums.ErrorCodes;

@Service
public class HelpCenterService extends BaseService {

	/**
	 * 删除分类，有下级分类或者挂载了文章的不可删除
	 * 
	 * @param id
	 */
	public Result<HelpCenterCategory> removeCategory(Long id) {
		// 查找是否有下级分类
		Integer countSub = mysqlService.count(HelpCenterCategory.class, " parent_id=" + id);
		if (countSub > 0) {
			return Result.error(ErrorCodes.CATEGORY_HAS_CHILDREN_CANNOT_REMOVE);
		}

		// 是否有挂载文章
		Integer countQA = mysqlService.count(HelpCenterQA.class, " category_id=" + id);
		if (countQA > 0) {
			return Result.error(ErrorCodes.CATEGORY_HAS_QUESTION_CANNOT_REMOVE);
		}

		mysqlService.delete(HelpCenterCategory.class, id);
		return Result.success();
	}

	/**
	 * 查询帮组分类下的所以问题
	 * 
	 * @param clazz
	 * @param category_id
	 * @param visible
	 * @param maxRecords
	 * @return
	 */
	public <T extends HelpCenterQA> List<T> searchQuestion(Class<T> clazz, Long category_id, YesNo visible,
			Integer maxRecords) {
		maxRecords = maxRecords == null ? BaseEntity.MAX_QUERY_RECOREDS : maxRecords;

		StringBuffer where = new StringBuffer(" 1=1 ");
		List<HelpCenterCategory> list = Lists.newArrayList();
		if (category_id != null) {
			list = mysqlService.queryBy(HelpCenterCategory.class,
					" (id = " + category_id + " OR parent_id = " + category_id + ") AND visible='Y'");
		}
		if (list.size() > 0) {
			where.append(" AND `category_id` IN (");
			list.forEach(t -> {
				where.append(t.id + ",");
			});
			where.deleteCharAt(where.length() - 1);
			where.append(")");
		}
		if (visible != null) {
			where.append(" AND visible='" + visible.key() + "'");
		}
		where.append(" ORDER BY category_id,sort");

		where.append(" LIMIT " + maxRecords);

		List<T> retList = mysqlService.queryBy(clazz, where.toString());
		return retList;
	}
	
	public <T extends HelpCenterQA> HelpCenterQACriteria<T> searchQA(Class<T> clazz,HelpCenterQACriteria<T> criteria) {

		StringBuffer where = new StringBuffer(" 1=1 ");
		List<HelpCenterCategory> list = Lists.newArrayList();
		if (criteria.getCategory_id() != null) {
			list = mysqlService.queryBy(HelpCenterCategory.class,
					" (id = " + criteria.getCategory_id() + " OR parent_id = " + criteria.getCategory_id() + ") AND visible='Y'");
		}
		if (list.size() > 0) {
			where.append(" AND `category_id` IN (");
			list.forEach(t -> {
				where.append(t.id + ",");
			});
			where.deleteCharAt(where.length() - 1);
			where.append(")");
		}
		if (criteria.getVisible() != null) {
			where.append(" AND visible='" + criteria.getVisible().key() + "'");
		}
		if(criteria.getKeyword() != null){
			where.append(" AND question LIKE '%"+criteria.getKeyword()+"%'");
		}
		
		//查询条件总页数
		criteria.setCount(mysqlService.count(clazz, where.toString()));
		
		where.append(" ORDER BY category_id,sort");

		// 组装分页
		if (criteria.getPage() != null) {
			where.append(" LIMIT " + criteria.getOffset()+ "," + criteria.getPageSize());
		}

		List<T> retList = mysqlService.queryBy(clazz, where.toString());
		criteria.addData(retList);
		return criteria;
	}

	/**
	 * 查询第一级的分类
	 * 
	 * @return
	 */
	public List<HelpCenterCategory> queryCategoryFirstLevel() {
		return mysqlService.queryBy(HelpCenterCategory.class, " parent_id=-1 ORDER BY `sort`");
	}

	public List<HelpCenterCategory> queryCategoryFlattedStructure() {
		List<HelpCenterCategory> retList = Lists.newArrayList();
		List<HelpCenterCategory> originalList = queryCategoryStructted();
		for (HelpCenterCategory fisrtLevel : originalList) {
			retList.add(fisrtLevel);
			List<HelpCenterCategory> children = fisrtLevel.getChildren();
			for (HelpCenterCategory secondLevel : children) {
				retList.add(secondLevel);
			}
			fisrtLevel.getChildren().clear();
		}
		return retList;
	}

	/**
	 * 查询结果已经按照一二级目录及顺序归并好
	 * 
	 * @return
	 */
	public List<HelpCenterCategory> queryCategoryStructted() {
		List<HelpCenterCategory> retList = Lists.newArrayList();

		List<HelpCenterCategory> originalList = mysqlService.queryBy(HelpCenterCategory.class, "1=1 ORDER BY sort");

		Map<Long, HelpCenterCategory> rootMap = Maps.newHashMap();

		// 第一次循环构建root
		for (HelpCenterCategory cate : originalList) {
			if (cate.getParent_id() == null || cate.getParent_id() == -1L) {
				// 一级
				rootMap.put(cate.getId(), cate);
				retList.add(cate);
			}
		}

		// 第二次循环补充子节点

		for (HelpCenterCategory cate : originalList) {
			if (cate.getParent_id() != null && cate.getParent_id() != -1L) {
				HelpCenterCategory parentCate = rootMap.get(cate.getParent_id());
				if (parentCate != null) {
					parentCate.addChild(cate);
				}
			}
		}

		return retList;
	}
}
