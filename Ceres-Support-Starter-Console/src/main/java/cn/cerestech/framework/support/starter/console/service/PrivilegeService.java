package cn.cerestech.framework.support.starter.console.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

import cn.cerestech.framework.support.employee.entity.Employee;
import cn.cerestech.framework.support.employee.service.EmployeeService;
import cn.cerestech.framework.support.starter.console.dao.SysMenuDao;
import cn.cerestech.framework.support.starter.console.entity.SysMenu;
import cn.cerestech.framework.support.starter.provider.PlatformProvider;

@Service
public class PrivilegeService {

	Logger log = LogManager.getLogger();

	@Autowired
	PlatformProvider platformProvider;

	@Autowired
	SysMenuDao menuDao;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	RoleService roleService;

	public List<SysMenu> getMenusByEmployee(Long id) {
		Set<String> menuSet = Sets.newHashSet();

		if (id == null) {
			log.warn("getByEmploye 的 employee id 不能为空");
			return null;
		}

		Employee e = employeeService.get(id);

		roleService.getRolesByKey(e.getRoles()).forEach(r -> {
			menuSet.addAll(r.getMenus());
		});

		List<SysMenu> menus = menuDao.findByPlatformAndUuidIn(platformProvider.get().getKey(), menuSet);

		// 过滤掉不非顶级菜单，这样树状层次结构才完整。
		menus = menus.stream().filter(m -> m.getParent() == null).collect(Collectors.toList());

		return menus;
	}
}
