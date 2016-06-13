define([ 'app', 'platform', 'http', 'modal', 'employee', 'pages', 'login' ], function(app, platform, http, modal, employee, pages, login) {

	app.controller('commonLoginCtrl', [ '$scope', '$location', '$state', function($scope, $location, $state) {

		$scope.platform = platform.get();
		$scope.loginEntity = {};
		$scope.loginFields = [];

		login.getDefinition({
			success : function(ret) {
				$scope.loginFields = ret;
				$scope.$apply();
			}
		});

		$scope.doLogin = function() {
			employee.login($scope.loginEntity, function(result) {
				if (result.isSuccess) {
					$state.go("workbench.calendar");
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