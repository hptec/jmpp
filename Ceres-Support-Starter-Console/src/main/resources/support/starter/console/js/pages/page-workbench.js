define([ 'app', 'platform', 'employee', 'pages', 'login', 'angular', 'http', 'modal'], function(app, platform, employee, pages, login, angular, http, modal) {

	app.controller('workbenchCtrl', [ '$scope', '$location', '$state', '$uibModal', function($scope, $location, $state, $modal) {
		//'$scope', '$location', '$stateParams', '$uibModal'
		$scope.platform = platform.get();
		$scope.currentUser = employee.getCurrent();
		
		$scope.logout = function() {
			login.logout(function() {
				pages.open("login");
			});
		}
		
		$scope.modifymypwd = function() {
			var modalInstance = $modal.open({
				templateUrl : "/api/classpath/query/support/employee/console/tpl/modify_my_password.html",
				size : 'xs',
				scope : $scope,
				controller : function($scope, $uibModalInstance) {
					$scope.__modifyMyPassword = {
						oldPwd:"",
						newPwd:"",
						confirmPwd:""
					};
					$scope.busying = false;
					$scope.cancel = function() {
						$scope.busying = false;
						$uibModalInstance.dismiss('cancel');
					}
					$scope.modify = function() {
						$scope.busying = true;
						var data = cui.extend(true, {}, $scope.__modifyMyPassword);
						http.load({
							url : '/api/employee/updateMyPassword',
							data : data,
							success : function(ret) {
								if (ret.isSuccess) {
									$scope.cancel();
									modal.success("修改密码成功");
								} else {
									modal.error(ret.message);
								}
							},
							complete:function(){
								$scope.busying = false;
								$scope.$applyAsync();
							}
						});

					}
				}
			});
		}
		
		
		
	} ]);

});