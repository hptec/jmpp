package cn.cerestech.middleware.codetable.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.cerestech.framework.core.enums.DescribableEnum;
import cn.cerestech.framework.support.persistence.Owner;
import cn.cerestech.middleware.codetable.dao.CategoryDao;
import cn.cerestech.middleware.codetable.entity.Category;
import cn.cerestech.middleware.codetable.entity.Code;

@Service
public class CodeTableService {

	@Autowired
	CategoryDao categoryDao;

	public Category get(Owner owner, DescribableEnum category, DescribableEnum[] defaultValues) {
		return get(owner, category, Arrays.asList(defaultValues == null ? new DescribableEnum[0] : defaultValues));
	}

	/**
	 * 读取一个字典表数据分类,如果没有数据则使用默认值的数据。
	 * 
	 * @param owner
	 * @param category
	 * @return
	 */
	public Category get(Owner owner, DescribableEnum category, Collection<DescribableEnum> defaultValues) {
		if (owner == null || category == null) {
			return null;
		}

		Category cate = categoryDao.findUniqueByOwnerTypeAndOwnerIdAndCategory(owner.getType(), owner.getId(),
				category.key());

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

	public Category get(Owner owner, DescribableEnum category) {
		return get(owner, category, Lists.newArrayList());
	}
}
