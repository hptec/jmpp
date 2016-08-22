package cn.cerestech.framework.support.starter.console.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.cerestech.framework.core.service.Result;
import cn.cerestech.framework.support.employee.entity.Employee;
import cn.cerestech.framework.support.employee.enums.EmployeeErrorCodes;
import cn.cerestech.framework.support.employee.service.EmployeeService;
import cn.cerestech.framework.support.starter.console.dao.SysMenuDao;
import cn.cerestech.framework.support.starter.console.dao.SysPrivilegeDao;
import cn.cerestech.framework.support.starter.console.entity.SysMenu;
import cn.cerestech.framework.support.starter.console.entity.SysPrivilege;
import cn.cerestech.framework.support.starter.console.entity.SysRole;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;

@Service
public class PrivilegeService {

	Logger log = LogManager.getLogger();

	@Autowired
	PlatformProvider platformProvider;

	@Autowired
	SysPrivilegeDao privilegeDao;

	@Autowired
	SysMenuDao menuDao;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	RoleService roleService;

	/**
	 * 得到登录用户的角色
	 * 
	 * @param id
	 * @return
	 */
	public List<SysRole> getRolesByEmployee(Long id) {
		List<SysRole> roleList = Lists.newArrayList();
		if (id == null) {
			log.warn("getRolesByEmploye 的 employee id 不能为空");
			return roleList;
		}

		Employee e = employeeService.get(id);
		if (e == null) {
			log.warn("getRolesByEmploye 的 employee 必须存在");
			return roleList;
		}

		SysPrivilege pri = null;
		// 普通会员，获取普通会员权限
		pri = privilegeDao.findUniqueByPlatformAndEmployeeId(platformProvider.get().getKey(), id);

		if (pri != null) {
			roleList.addAll(pri.getRoles());
		}
		return roleList;
	}

	public List<SysMenu> getMenusByEmployee(Long id) {
		Set<String> menuSet = Sets.newHashSet();
		getRolesByEmployee(id).forEach(role -> {
			menuSet.addAll(role.getMenus());
		});

		List<SysMenu> menus = menuDao.findByPlatformAndUuidIn(platformProvider.get().getKey(), menuSet);

		// 过滤掉不非顶级菜单，这样树状层次结构才完整。
		menus = menus.stream().filter(m -> m.getParent() == null).collect(Collectors.toList());

		return menus;
	}

	public SysPrivilege getByEmployee(Long id) {
		if (id == null) {
			log.warn("getByEmploye 的 employee id 不能为空");
			return null;
		}

		SysPrivilege privilege = privilegeDao.findUniqueByPlatformAndEmployeeId(platformProvider.get().getKey(), id);
		return privilege;
	}

	/**
	 * 为员工分配角色
	 * 
	 * @param employeeId
	 * @param roleId
	 * @return
	 */
	public Result<SysPrivilege> setEmployeeRoles(Long employeeId, List<Long> roleId) {
		SysPrivilege privilege = getByEmployee(employeeId);
		if (privilege == null) {
			privilege = new SysPrivilege();

			Employee e = employeeService.get(employeeId);
			if (e == null) {
				return Result.error(EmployeeErrorCodes.EMPLOYEE_NOT_EXIST);
			}
			privilege.setEmployee(e);
		}

		privilege.setPlatform(platformProvider.get().getKey());

		List<SysRole> roles = roleService.getRolesById(roleId);
		privilege.setRoles(roles);
		privilegeDao.save(privilege);
		return Result.success(privilege);
	}
}
