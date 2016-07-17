package cn.cerestech.middleware.codetable.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.middleware.codetable.dao.CategoryDao;
import cn.cerestech.middleware.codetable.entity.Category;
import cn.cerestech.middleware.codetable.entity.Code;
import cn.cerestech.middleware.codetable.enums.ErrorCodes;
import cn.cerestech.middleware.codetable.provider.CodeTableProvider;

@Service
public class CodeTableService {

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	CodeTableProvider provider;

	public Category get(DescribableEnum category, DescribableEnum[] defaultValues) {
		return get(category, Arrays.asList(defaultValues == null ? new DescribableEnum[0] : defaultValues));
	}

	/**
	 * 读取一个字典表数据分类,如果没有数据则使用默认值的数据。
	 * 
	 * @param owner
	 * @param category
	 * @return
	 */
	public Category get(DescribableEnum category, Collection<DescribableEnum> defaultValues) {
		Owner owner = provider.resolveOwner();
		if (owner == null || category == null) {
			return null;
		}

		Category cate = categoryDao.findUniqueByOwnerAndCategory(owner, category.key());

		if (cate == null) {
			// 找不到，默认插入初始化的值
			cate = new Category();
			cate.setOwner(owner);
			cate.setCategory(category.key());
			cate.setDesc(category.desc());
			if (defaultValues != null) {
				List<Code> codesList = Lists.newArrayList();

				for (DescribableEnum de : defaultValues) {
					Code code = new Code();
					code.setValue(de.key());
					code.setDesc(de.desc());
					code.setCategory(cate);
					codesList.add(code);
				}
				cate.setCodes(codesList);
				cate.reCount();
			}
			categoryDao.save(cate);
		} else if ((cate.getCodes() == null || cate.getCodes().isEmpty())
				&& (defaultValues != null && !defaultValues.isEmpty())) {
			// 赋予默认值
			List<Code> codesList = Lists.newArrayList();
			for (DescribableEnum de : defaultValues) {
				Code code = new Code();
				code.setValue(de.key());
				code.setDesc(de.desc());
				code.setCategory(cate);
				codesList.add(code);
			}
			cate.setCodes(codesList);
			cate.reCount();
			categoryDao.save(cate);
		}

		return cate;

	}

	public Category get(DescribableEnum category) {
		return get(category, Lists.newArrayList());
	}

	/**
	 * 列出指定owner拥有的可配置字典项目列表。可指定默认值，如果没有项目则使用默认值创建，如果项目不齐则使用默认值补充。
	 * 
	 * @param owner
	 * @param defaultValues
	 * @return
	 */
	public List<Category> list() {
		Owner owner = provider.resolveOwner();
		if (owner == null) {
			return Lists.newArrayList();
		}

		List<Category> cateList = categoryDao.findByOwner(owner);

		// 补充完善
		Collection<DescribableEnum> defaultValues = provider.getDefaultCategoryList();
		List<Category> cateToSave = Lists.newArrayList();
		if (defaultValues != null && !defaultValues.isEmpty()) {
			Map<String, Category> cateMap = Maps.newHashMap();
			cateList.forEach(cate -> {
				cateMap.put(cate.getCategory(), cate);
			});

			for (DescribableEnum de : defaultValues) {
				if (!cateMap.containsKey(de.key())) {
					Category c = new Category();
					c.setCategory(de.key());
					c.setDesc(de.desc());
					c.setOwner(owner);
					c.setCodes(Lists.newArrayList());
					// 增加字段选项
					Collection<DescribableEnum> codesDe = provider.getDefaultCodeByCategory(de);
					for (DescribableEnum cde : codesDe) {
						Code code = new Code();
						code.setCategory(c);
						code.setDesc(cde.desc());
						code.setSortIndex(999);
						code.setValue(cde.key());
						c.getCodes().add(code);
					}

					// 添加到集合中
					c.reCount();
					cateToSave.add(c);
				}
			}
		}

		// 检查是否需要重新查询和保存
		if (cateToSave.isEmpty()) {
			return cateList;
		} else {
			categoryDao.save(cateToSave);
			return categoryDao.findByOwner(owner);
		}
	}

	/**
	 * 添加或更新code
	 * 
	 * @param code
	 * @return
	 */
	public Result<Code> saveCode(Code code) {
		if (code == null) {
			return Result.error(ErrorCodes.NOT_FOUND);
		}
		Category category = code.getCategory();
		if (category == null) {
			return Result.error(ErrorCodes.NOT_FOUND);
		}
		category = categoryDao.findOne(category.getId());
		if (category == null) {
			return Result.error(ErrorCodes.NOT_FOUND);
		}
		if (code.getId() != null) {
			// 更新模式
			Code codeInDb = category.getCodes().stream().filter(c -> c.getId().equals(code.getId())).findFirst()
					.orElse(null);
			if (codeInDb != null) {
				codeInDb.setValue(code.getValue());
				codeInDb.setDesc(code.getDesc());
				categoryDao.save(category);
				return Result.success(codeInDb);
			} else {
				return Result.success();
			}
		} else {
			// 新增模式
			Code codeInDb = new Code();
			codeInDb.setCategory(category);
			codeInDb.setDesc(code.getDesc());
			codeInDb.setValue(code.getValue());
			codeInDb.setSortIndex(999);
			if (category.getCodes() == null) {
				category.setCodes(Lists.newArrayList());
			}
			category.getCodes().add(codeInDb);
			categoryDao.save(category);
			return Result.success(codeInDb);
		}
	}
}
