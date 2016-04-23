define([ 'app', 'platform', 'http', 'modal', 'employee', 'pages' ], function(app, platform, http, modal, employee, pages) {

	app.controller('commonLoginCtrl', [ '$scope', '$location', function($scope, $location) {

		$scope.platform = platform.get();
		$scope.loginEntity = {};

		$scope.doLogin = function() {

			employee.login($scope.loginEntity.loginId, $scope.loginEntity.loginPwd, $scope.loginEntity.remember, function(result) {
				if (result.isSuccess) {
					pages.open("/workbench");
					$scope.$apply();
				} else {
					modal.alert({
						title : result.message
					});
				}
			});

		}
	} ]);

});