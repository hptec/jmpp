define([ 'app', 'pages', 'modal', 'http' ], function(app, pages, modal, http) {

	app.controller('employeeDetailCtrl', [ '$scope', '$location', '$stateParams', function($scope, $location, $stateParams) {

		$scope.returnToList = function() {
			pages.open({
				url : "workbench.employee"
			})
		}
		$scope.doSave = function() {
			$scope.error = {
				has : false
			};
			if ($scope.data == undefined) {
				return;
			}
			if ($scope.data.login.id == undefined || $scope.data.login.id == "") {
				cui.extend($scope.error, {
					loginId : true,
					has : true
				});
			}
			if ($scope.data.name == undefined || $scope.data.name == "") {
				cui.extend($scope.error, {
					name : true,
					has : true
				});
			}

			if ($scope.error.has == true) {
				return;
			}

			// 执行保存
			http.load({
				url : "/api/employee/updateSub",
				data : {
					jsonStr : JSON.stringify($scope.data)
				},
				success : function(ret) {
					if (ret.isSuccess) {
						alert("保存成功");
						$scope.returnToList();
					} else {
						modal.toast({
							title : ret.message
						});
					}
				}
			});
		}

		// 初始化过程
		if ($stateParams.id == undefined || $stateParams.id == "") {
			// 错误的链接进入，返回列表
			$scope.returnToList();
			return;
		} else if ($stateParams.id == -1) {
			$scope.mode = "new";
			$scope.data = {
				login : {
					id : "",
					frozen : "N"
				}
			};
		} else {
			$scope.mode = "edit"
			cui.extend($scope._servelog_param, {
				"hotelCustomer_id" : $stateParams.id
			});
			http.load({
				url : "/api/employee/get",
				data : {
					id : $stateParams.id
				},
				success : function(ret) {
					$scope.data = ret;
					// 找不到或者数据错误
					if ($scope.data == undefined) {
						modal.toast({
							title : "找不到对应员工信息"
						});
						$scope.returnToList();
						return;
					}
					$scope.$apply();
				}
			});
		}
	} ]);

});