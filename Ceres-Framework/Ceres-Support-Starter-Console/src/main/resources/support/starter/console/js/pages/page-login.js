define([ 'module', 'app', 'platform', 'http', 'modal', 'employee', 'pages', 'login', 'geetest' ], function(module, app, platform, http, modal, employee, pages, login, geetest) {

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
						var page = "workbench.customer";
						if (module.config().mainpage != undefined) {
							page = module.config().mainpage;
						}

						$state.go(page);
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