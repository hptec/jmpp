package cn.cerestech.framework.support.employee.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cn.cerestech.framework.core.enums.EnumCollector;
import cn.cerestech.framework.core.enums.Gender;
import cn.cerestech.framework.core.enums.YesNo;
import cn.cerestech.framework.support.employee.entity.Employee;
import cn.cerestech.framework.support.persistence.search.AbstractCriteria;

public class EmployeeCriteria extends AbstractCriteria<Employee> {

	// 所属平台
	private String platformKey;

	// 员工编号
	private Long employeeId;

	// 所属超级管理员
	private Long parentId;
	// 性别
	private String gender;
	// 是否冻结
	private String frozen;
	// 是否超级管理员
	private String isSuperAdmin;
	// 关键字匹配
	private String keyword;
	// 查询结果是否包含已经删除的数据（默认不包含)
	private Boolean includeDeleted = Boolean.FALSE;;

	@Override
	public Specification<Employee> specification() {
		return new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> sets = Lists.newArrayList();

				if (Strings.isNullOrEmpty(platformKey)) {
					throw new RuntimeException("检索必须指定Platform Key");
				} else {
					sets.add(builder.equal(root.get("platform"), platformKey));
				}

				// 上级管理员
				if (parentId != null) {
					sets.add(builder.equal(root.get("parent").get("id"), parentId));
				}

				// 性别
				if (!Strings.isNullOrEmpty(gender)) {
					Gender g = EnumCollector.forClass(Gender.class).keyOf(gender);
					if (g != null) {
						sets.add(builder.equal(root.get("gender"), g));
					}
				}

				// 是否冻结
				if (!Strings.isNullOrEmpty(frozen)) {
					YesNo f = EnumCollector.forClass(YesNo.class).keyOf(frozen);
					if (f != null) {
						sets.add(builder.equal(root.get("login").get("frozen"), f));
					}
				}

				// 是否超级管理员

				if (!Strings.isNullOrEmpty(isSuperAdmin)) {
					YesNo sa = EnumCollector.forClass(YesNo.class).keyOf(isSuperAdmin);
					if (sa != null) {
						sets.add(builder.equal(root.get("isSuperAdmin"), sa));
					}
				}

				// 是否包含已删除数据
				if (!includeDeleted) {
					sets.add(builder.isNull(root.get("deleteTime")));
				}

				// 关键字匹配
				if (!Strings.isNullOrEmpty(keyword)) {
					List<Predicate> likes = Lists.newArrayList();
					likes.add(builder.like(root.get("name"), "%" + keyword + "%"));
					likes.add(builder.like(root.get("phone").get("number"), "%" + keyword + "%"));
					likes.add(builder.like(root.get("email"), "%" + keyword + "%"));
					likes.add(builder.like(root.get("login").get("id"), "%" + keyword + "%"));
					sets.add(builder.or(toArray(likes)));
				}

				if (sets.isEmpty()) {
					return null;
				} else {
					return builder.and(toArray(sets));
				}
			}
		};
	}

	@Override
	public Sort sort() {
		Sort sort = new Sort(Direction.DESC, "createTime");
		return sort;
	}

	public String getPlatformKey() {
		return platformKey;
	}

	public void setPlatformKey(String platformKey) {
		this.platformKey = platformKey;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFrozen() {
		return frozen;
	}

	public void setFrozen(String frozen) {
		this.frozen = frozen;
	}

	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

}
