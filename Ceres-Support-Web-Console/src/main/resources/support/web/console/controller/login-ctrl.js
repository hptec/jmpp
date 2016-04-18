define([ 'app', 'platform', 'http', 'modal', 'icheck' ], function(app, platform, http, modal) {

	app.controller('commonLoginCtrl', [
			'$scope',
			'$location',
			function($scope, $location) {

				$scope.platform = platform.get();
				$scope.loginEntity = {};

				$scope.doLogin = function() {
					if ($scope.loginEntity == undefined || $scope.loginEntity.loginId == undefined || $scope.loginEntity.loginId == "" || $scope.loginEntity.loginPwd == undefined
							|| $scope.loginEntity.loginPwd == "") {

					} else {
						console.log("login entity", $scope.loginEntity);
						http.load({
							url : '/api/console/employee/doLogin',
							data : $scope.loginEntity,
							success : function(result) {
								if (result.isSuccess) {
									$location.path("/workbench");
									$scope.$apply();
								} else {
									modal.alert({
										title : result.message
									});
								}
							}
						});
					}

				}
			} ]);

});