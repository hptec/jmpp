define([ 'app', 'platform', 'http', 'modal', 'employee', 'pages', 'login', 'geetest' ], function(app, platform, http, modal, employee, pages, login, geetest) {

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

		geetest.init({
			transId : "console_login",
			product : "popup",
			targetId : "#loginBtn",
			valueId : "#geetestValue",
			success : function(ret) {
				employee.login($scope.loginEntity, function(result) {
					if (result.isSuccess) {
						$state.go("workbench.customer");
						$scope.$apply();
					} else {
						modal.alert({
							title : result.message
						});
					}
				});
			}
		});

	} ]);

});