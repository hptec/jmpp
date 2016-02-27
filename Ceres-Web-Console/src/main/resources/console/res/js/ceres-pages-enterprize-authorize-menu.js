consoleApp.controller('menuListCtrl', [ '$scope', function($scope) {

	$c.load({
		url : 'console/enterprize/authorize/menu/init',
		success : function(ret) {
			for (ln in ret.menus) {
				ret.menus[ln].is_menu_name = ret.menus[ln].is_menu == "Y" ? "菜单" : "权限";
			}

			$.extend($scope, ret);

			$.extend($scope, {
				cdt : {},
				pmenus : ret.menus
			});

			$scope.$apply();
		}
	});

	$scope.doSearch = function() {
		$c.load({
			url : 'console/enterprize/authorize/menu/search',
			data : $scope.cdt,
			success : function(ret) {
				for (ln in ret) {
					ret[ln].is_menu_name = $c.enums.desc("yes_no", ret[ln].is_menu);
				}
				$scope.menus = ret;
				$scope.$apply();
			}
		});
	}
	// 恢复所有菜单为出厂设置
	$scope.recoverAll = function() {
		if (confirm("\"恢复为出厂数据\" 会导致所有改动后的菜单数据丢失，是否确认恢复?")) {
			$c.load({
				url : 'console/enterprize/authorize/menu/recoverall',
				success : function(ret) {
					if (ret.isSuccess) {
						alert("恢复成功!");
					} else {
						alert(ret.message);
					}
				}
			})
		}

	}
} ]);

consoleApp.controller('menuDetailCtrl', [ '$scope', '$routeParams', function($scope, $routeParams) {
	$c.load({
		url : 'console/enterprize/authorize/menu',
		data : {
			id : $routeParams.id
		},
		success : function(ret) {
			$.extend($scope, ret);

			$scope.$apply()
		}
	});

	$scope.doUpdate = function() {
		$c.load({
			url : 'console/enterprize/authorize/menu/update',
			data : $scope.menu,
			success : function(ret) {
				if (ret.isSuccess) {
					alert("保存成功");
				} else {
					alert(ret.message);
				}
			}
		});
	};

	$scope.recover = function(id) {
		$c.load({
			url : 'console/enterprize/authorize/menu/recover',
			data : {
				id : id
			},
			success : function(ret) {
				if (ret.isSuccess) {
					alert("恢复成功");
				} else {
					alert(ret.message);
				}
			}
		});
	}

} ]);

consoleApp.controller('roleSearchCtrl', [ '$scope', function($scope) {

	$scope.remove = function(role) {
		if (confirm("角色删除后将不可恢复，是否继续?")) {
			$c.load({
				url : 'console/enterprize/authorize/role/remove',
				data : {
					id : role.id
				},
				success : function(ret) {
					if (ret.isSuccess) {
						$scope.$$ceres_target.trigger("search");
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}
} ]);
consoleApp.controller('roleDetailCtrl', [ '$scope', '$routeParams', function($scope, $routeParams) {
	$c.load({
		url : 'console/enterprize/authorize/role',
		data : {
			id : $routeParams.id
		},
		success : function(ret) {
			$.extend($scope, ret);
			$scope.$apply();
			$('input[type="checkbox"]').iCheck({
				checkboxClass : 'icheckbox_minimal-blue',
				radioClass : 'iradio_minimal-blue',
			});
			$(".select2").select2({
				tags : true,
				placeholder : "选择拥有这个角色的成员名单",
				allowClear : true
			});
		}
	});

	$scope.doUpdate = function() {
		var json = JSON.stringify($scope.role);
		if (json == undefined || json == "") {
			alert("请填写角色内容");
			return;
		}
		if ($scope.role.name == undefined || $scope.role.name == "") {
			alert("请输入名称");
			return;
		}
		if ($scope.role.super_admin == undefined || $scope.role.super_admin == "") {
			alert("请选择类型");
			return;
		}

		$c.load({
			url : 'console/enterprize/authorize/role/update',
			data : {
				json : JSON.stringify($scope.role)
			},
			success : function(ret) {
				if (ret.isSuccess) {
					alert("保存成功");
				} else {
					alert(ret.message);
				}
			}
		});
	};
} ]);