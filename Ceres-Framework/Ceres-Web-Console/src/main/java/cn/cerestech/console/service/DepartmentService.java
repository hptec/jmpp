package cn.cerestech.console.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.cerestech.console.entity.SysDepartment;
import cn.cerestech.console.enums.Console;
import cn.cerestech.console.errorcodes.DepartmentErrorCodes;
import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.framework.core.service.Result;

@Service
public class DepartmentService extends BaseService {

	@Autowired
	ConsoleConfigService configService;

	/**
	 * 得到结构化的部门
	 * 
	 * @return
	 */
	public List<SysDepartment> structuredDepartments() {
		Map<Long, SysDepartment> depMapping = Maps.newHashMap();
		List<SysDepartment> root = Lists.newArrayList();
		List<SysDepartment> all = mysqlService.queryBy(SysDepartment.class, " 1=1 ORDER BY sort");
		all.forEach(dep -> {
			// 在最开始全部放到映射，避免顺序问题找不到
			depMapping.put(dep.getId(), dep);
		});

		// 建立好层次关系
		all.forEach(dep -> {
			if (dep.getParent_id() == null || dep.getParent_id() == -1L) {
				// 根部门
				root.add(dep);
			} else {
				// 子部门
				SysDepartment parent = depMapping.get(dep.getParent_id());
				if (parent != null) {
					parent.addChild(dep);
				}
			}
		});

		// 建立Depth
		root.forEach(d1 -> {
			d1.setDepth(1);
			d1.getChildren().forEach(d2 -> {
				d2.setDepth(2);
				d2.getChildren().forEach(d3 -> {
					d3.setDepth(3);
					d3.getChildren().forEach(d4 -> {
						d4.setDepth(4);
						d4.getChildren().forEach(d5 -> {
							d5.setDepth(5);
						});
					});
				});
			});
		});

		return root;
	}

	/**
	 * 得到结构化后按照平面展开的部门结构(便于管理台进行编辑)
	 * 
	 * @return
	 */
	public List<SysDepartment> structuredFlatDepartments() {
		List<SysDepartment> root = Lists.newArrayList();
		// 第一级
		structuredDepartments().forEach(dept -> {
			root.add(dept);
			// 第二级
			dept.getChildren().forEach(dept2 -> {
				root.add(dept2);
				// 第三级
				dept2.getChildren().forEach(dept3 -> {
					root.add(dept3);
					// 第四级
					dept3.getChildren().forEach(dept4 -> {
						root.add(dept4);
						// 第五级
						dept4.getChildren().forEach(dept5 -> {
							root.add(dept5);
						});
					});
				});
			});
		});
		return root;
	}

	public Result<SysDepartment> move(Long id, Console.Department.Move move) {
		SysDepartment dept = mysqlService.queryById(SysDepartment.class, id);
		if (dept != null && move != null) {
			switch (move) {
			case UP:
				// 同级别向上移动
				List<SysDepartment> brothers = mysqlService.queryBy(SysDepartment.class,
						" parent_id=" + dept.parent_id + " ORDER by `sort`");
				if (brothers != null && !brothers.isEmpty()) {
					// 保持顺序不变，重置数字
					Integer from = 0, to = brothers.size();
					for (Integer i = from; i < to; i++) {
						SysDepartment cur = brothers.get(i);
						cur.setSort(i.longValue());
					}

					// 找到当前部门，检查是否能上移
					for (Integer i = from; i < to; i++) {
						SysDepartment cur = brothers.get(i);
						if (cur.getId() == id) {
							// 找到对象
							if (i == 0) {
								// 第一位不用上移
								break;
							} else {
								cur.setSort(cur.getSort() - 1);// 顺序上移一位
								SysDepartment previousSibling = brothers.get(i - 1);
								previousSibling.setSort(i.longValue());
							}

						}
					}

					// 保存所有顺序
					brothers.forEach(d -> {
						mysqlService.update(d);
					});

				}
				return Result.success(dept);
			case DOWN:
				// 同级别向下移动
				brothers = mysqlService.queryBy(SysDepartment.class,
						" parent_id=" + dept.parent_id + " ORDER by `sort`");
				if (brothers != null && !brothers.isEmpty()) {
					// 保持顺序不变，重置数字
					Integer from = 0, to = brothers.size();
					for (Integer i = from; i < to; i++) {
						SysDepartment cur = brothers.get(i);
						cur.setSort(i.longValue());
					}

					// 找到当前部门，检查是否能下移
					for (Integer i = from; i < to; i++) {
						SysDepartment cur = brothers.get(i);
						if (cur.getId() == id) {
							// 找到对象
							if (i == to - 1) {
								// 最后一位不用下移
								break;
							} else {
								cur.setSort(cur.getSort() + 1);// 顺序下移一位
								SysDepartment previousSibling = brothers.get(i + 1);
								previousSibling.setSort(i.longValue());
							}

						}
					}

					// 保存所有顺序
					brothers.forEach(d -> {
						mysqlService.update(d);
					});

				}
				return Result.success(dept);
			case LEFT:
				// 升级
				if (dept.getParent_id() != -1) {
					SysDepartment parentDept = mysqlService.queryById(SysDepartment.class, dept.getParent_id());
					dept.setParent_id(parentDept.getParent_id());
					mysqlService.update(dept);
					resort(dept.getParent_id());
				}
				// 如果已经是顶级，则直接返回
				return Result.success(dept);
			case RIGHT:
				// // 升级
				// if (dept.getParent_id() != -1) {
				// Department parentDept =
				// mysqlService.queryById(Department.class,
				// dept.getParent_id());
				// dept.setParent_id(parentDept.getParent_id());
				// mysqlService.update(dept);
				// resort(dept.getParent_id());
				// }
				// // 如果已经是顶级，则直接返回
				// return Result.success(dept);

				break;
			}
		}
		return Result.error(DepartmentErrorCodes.DEPARTMENT_NOT_EXIST);
	}

	/**
	 * 将这个部门下的子部门进行重新排序并更新顺序
	 * 
	 * @param id
	 * @return
	 */
	public Result<List<SysDepartment>> resort(Long id) {
		List<SysDepartment> list = mysqlService.queryBy(SysDepartment.class, " parent_id=" + id + " ORDER by `sort`");
		// 保持顺序不变，重置数字
		Integer from = 0, to = list.size();
		for (Integer i = from; i < to; i++) {
			SysDepartment cur = list.get(i);
			cur.setSort(i.longValue());
			mysqlService.update(cur);
		}
		return Result.success(list);
	}

	public Result<SysDepartment> remove(Long id) {
		if (id == null) {
			return Result.error(DepartmentErrorCodes.DEPARTMENT_NOT_EXIST);
		}
		SysDepartment dept = mysqlService.queryById(SysDepartment.class, id);
		if (dept != null) {
			// 检测是否有下级部门
			List<SysDepartment> children = mysqlService.queryBy(SysDepartment.class, " parent_id=" + id);
			if (children.size() > 0) {
				return Result.error(DepartmentErrorCodes.HAS_CHILDREN_CANNOT_REMOVE);
			} else {
				mysqlService.delete(SysDepartment.class, id);
				return Result.success(dept);
			}
		}
		return Result.error(DepartmentErrorCodes.DEPARTMENT_NOT_EXIST);
	}
}
