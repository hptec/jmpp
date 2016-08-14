define([ 'app', 'platform', 'employee', 'pages', "modal" ], function(app, platform, employee, pages, modal) {

	app.controller('employeeCtrl', [ '$scope', '$location', function($scope, $location) {
		$scope.editEmployee = function(employee) {
			pages.open({
				url : "workbench.employee_detail",
				data : {
					id : -1
				}
			});
		}

		$scope._param = {
			editEmployee : function(row) {
				if (row == undefined) {
					return;
				}
				if (row.id == undefined || row.id == "") {
					modal.toast({
						title : "员工信息不存在或已删除"
					});
					return;
				}
				pages.open({
					url : "workbench.employee_detail",
					data : {
						id : row.id
					}
				});
			},
			deleteEmployee : function(row) {
				// 删除为逻辑删除
			}
		}

	} ]);

});