consoleApp.controller('departmentCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {

	$scope.refresh = function() {
		$c.load({
			url : 'console/department/init',
			success : function(ret) {
				$.extend($scope, ret);
				$scope.$apply();
			}
		});
	}

	$scope.addDept = function(parent) {
		var name = prompt("请输入新部门的名称");
		if (name != undefined && name != "") {
			$c.load({
				url : 'console/department/update',
				data : {
					name : name,
					create_time : new Date(),
					employee_count : 0,
					parent_id : parent == undefined ? -1 : parent,
					sort : 9999
				},
				success : function(ret) {
					if (ret.isSuccess) {
						$scope.refresh();
					} else {
						alert(ret.message);
					}
				}
			});
		}
	};

	$scope.rename = function(dept) {
		var name = prompt("请输入新的部门名称", dept.name);
		if (name != undefined && name != "") {
			$c.load({
				url : 'console/department/rename',
				data : {
					id : dept.id,
					name : name
				},
				success : function(ret) {
					if (ret.isSuccess) {
						$scope.refresh();
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}

	$scope.move = function(dept, move) {
		$c.load({
			url : 'console/department/move',
			data : {
				id : dept.id,
				move : move
			},
			success : function(ret) {
				if (ret.isSuccess) {
					$scope.refresh();
				} else {
					alert(ret.message);
				}
			}
		});
	}
	$scope.remove = function(dept) {
		if (confirm("部门删除后将不可恢复，是否继续?")) {
			$c.load({
				url : 'console/department/remove',
				data : {
					id : dept.id
				},
				success : function(ret) {
					if (ret.isSuccess) {
						$scope.refresh();
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}

	$scope.refresh();
} ]);

consoleApp.controller('departmentModifyCtrl', [ '$scope', '$location', '$routeParams', function($scope, $location, $routeParams) {

} ]);