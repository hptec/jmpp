package cn.cerestech.console.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.cerestech.console.entity.SysEmployeeRole;
import cn.cerestech.console.entity.SysMenu;
import cn.cerestech.console.entity.SysRole;
import cn.cerestech.console.entity.SysRoleMenu;
import cn.cerestech.console.errorcodes.SysRoleErrorCodes;
import cn.cerestech.framework.core.service.BaseService;
import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.persistence.annotation.Table;
import cn.cerestech.framework.persistence.entity.BaseEntity;

/**
 * 权限相关
 * 
 * @author harryhe
 *
 */
@Service
public class PrivilegeService extends BaseService {

	/**
	 * 员工在系统中分配的角色菜单
	 * 
	 * @param employeeId
	 * @return
	 */
	public List<SysMenu> menusOfEmployee(Long employeeId, Boolean withHiddenMenu) {
		List<SysMenu> root = Lists.newArrayList();
		if (employeeId != null) {
			// 找到用户分配了那些角色
			Set<Long> roleIdSet = Sets.newHashSet();
			mysqlService.queryBy(SysEmployeeRole.class, " employee_id =" + employeeId).stream()
					.mapToLong(er -> er.getRole_id()).distinct().forEach(roleId -> {
						roleIdSet.add(roleId);
					});
			// 读取角色
			List<SysRole> roles = mysqlService.queryByIdIn(SysRole.class, roleIdSet);

			// 读取角色包含的菜单(去重)
			List<SysMenu> menus = Lists.newArrayList();

			if (isSuperAdminContained(roles)) {
				// 有超级管理员的角色就全部菜单
				menus = mysqlService.queryBy(SysMenu.class, " 1=1 ORDER BY sort_index", m -> {
					if (withHiddenMenu) {
						return Boolean.TRUE;
					} else {
						return m.isMenu();
					}
				});
			} else {
				// 没有则找出角色包含的菜单
				if (!roles.isEmpty()) {
					Set<Long> menuIdSet = Sets.newHashSet();

					StringBuffer whereBuffer = new StringBuffer(" role_id in (");
					for (SysRole r : roles) {
						whereBuffer.append(r.getId() + ",");
					}
					whereBuffer.deleteCharAt(whereBuffer.length() - 1);
					whereBuffer.append(")");
					mysqlService.queryBy(SysRoleMenu.class, whereBuffer.toString()).forEach(srm -> {
						menuIdSet.add(srm.getMenu_id());
					});
					if (!menuIdSet.isEmpty()) {
						whereBuffer = new StringBuffer(" id in (");
						for (Long mid : menuIdSet) {
							whereBuffer.append(mid + ",");
						}
						whereBuffer.deleteCharAt(whereBuffer.length() - 1);
						whereBuffer.append(") ORDER BY sort_index");
						menus = mysqlService.queryBy(SysMenu.class, whereBuffer.toString(), m -> {
							if (withHiddenMenu) {
								return Boolean.TRUE;
							} else {
								return m.isMenu();
							}
						});
					}
				}
			}

			// 整理菜单为树状结构
			Map<Long, SysMenu> menuIndex = Maps.newHashMap();// 菜单索引，便于快速查询菜单
			menus.forEach(m -> menuIndex.put(m.getId(), m));

			// 整理菜单结构
			for (SysMenu m1 : menus) {
				if (m1.getParent_id().equals(-1L)) {
					// 根菜单
					root.add(m1);
				} else {
					// 添加到父菜单中
					SysMenu parentMenu = menuIndex.get(m1.getParent_id());
					if (parentMenu != null) {
						parentMenu.addSub(m1);
					}
				}
			}
		}
		return root;

	}

	private Boolean isSuperAdminContained(List<SysRole> roles) {
		if (roles != null) {
			for (SysRole sr : roles) {
				if (sr.isSuperAdmin()) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * 恢复菜单数据为出厂数据
	 * 
	 * @return
	 */
	public Result<List<SysMenu>> menuRecoverAll() {
		Table table = SysMenu.class.getAnnotation(Table.class);
		if (table != null) {
			String tablename = table.value();
			if (!Strings.isNullOrEmpty(tablename)) {
				// 删除表数据
				baseMapper.delete(tablename, " 1=1");

				List<SysMenu> retList = Lists.newArrayList();
				// 添加表数据
				List<BaseEntity> initData = BaseEntity.getInit();
				Set<Long> initIds = Sets.newHashSet();
				for (BaseEntity be : initData) {
					if (be instanceof SysMenu && !initIds.contains(be.getId())) {
						mysqlService.insert(be);
						retList.add((SysMenu) be);
						initIds.add(be.getId());//排除重复的Key
					}
					
				}
				return Result.success(retList);
			}
		}
		return Result.error().setMessage("找不到菜单表");
	}

	public List<SysRole> searchRole(String isSuperAdmin) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" 1=1");
		if (!Strings.isNullOrEmpty(isSuperAdmin)) {
			buffer.append(" AND super_admin = '" + isSuperAdmin + "'");
		}

		return mysqlService.queryBy(SysRole.class, buffer.toString());
	}

	public SysRole queryRoleFull(Long id) {
		SysRole role = mysqlService.queryById(SysRole.class, id);
		if (role != null) {
			mysqlService.queryBy(SysEmployeeRole.class, " role_id = " + id).forEach(ser -> {
				role.getEmployeeIds().add(ser.getEmployee_id());
			});
			mysqlService.queryBy(SysRoleMenu.class, " role_id = " + id).forEach(srm -> {
				role.getMenuIds().add(srm.getMenu_id());
			});
		}
		return role;
	}

	public Result<SysRole> persistRole(SysRole role) {
		if (role == null) {
			return Result.error(SysRoleErrorCodes.ROLE_IS_EMPTY);
		}
		if (role.getCreate_time() == null) {
			role.setCreate_time(new Date());
		}

		// 计算个数
		role.setEmployee_count(role.getEmployeeIds().size());
		role.setMenu_count(role.getMenuIds().size());
		if (role.getId() == null) {
			// 新建角色
			mysqlService.insert(role);
		} else {
			mysqlService.update(role);
		}

		// 同步人员和菜单
		mysqlService.delete(SysEmployeeRole.class, " role_id=" + role.getId());
		role.getEmployeeIds().forEach(eid -> {
			SysEmployeeRole ser = new SysEmployeeRole(eid, role.getId());
			mysqlService.insert(ser);
		});

		mysqlService.delete(SysRoleMenu.class, " role_id=" + role.getId());
		role.getMenuIds().forEach(mid -> {
			SysRoleMenu srm = new SysRoleMenu();
			srm.setMenu_id(mid);
			srm.setRole_id(role.getId());
			mysqlService.insert(srm);
		});

		return Result.success(role);
	}

	public Result<SysRole> removeRole(Long id) {
		SysRole role = mysqlService.queryById(SysRole.class, id);
		if (role == null) {
			return Result.error(SysRoleErrorCodes.ROLE_NOT_EXIST);
		}

		// 检测是否有员工
		if (role.getEmployee_count() > 0) {
			return Result.error(SysRoleErrorCodes.HAS_EMPLOYEE_CANNOT_DELETE);
		}
		// 检测是否有菜单
		if (role.getMenu_count() > 0) {
			return Result.error(SysRoleErrorCodes.HAS_MENU_CANNOT_DELETE);
		}

		// 删除角色
		mysqlService.delete(SysRole.class, id);
		return Result.success(role);

	}
}
