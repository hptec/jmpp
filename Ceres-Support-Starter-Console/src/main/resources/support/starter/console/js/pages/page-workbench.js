define([ 'app', 'platform', 'employee', 'menu', 'pages', 'login', 'angular' ], function(app, platform, employee, menu, pages, login, angular) {

	app.controller('workbenchCtrl', [ '$scope', '$location', '$state', function($scope, $location, $state) {
		$scope.platform = platform.get();
		$scope.currentUser = employee.getCurrent();
		$scope.jump = function(url) {
			pages.open({
				url : url
			});
		}
		$scope.menus = menu.getMine(function(result) {
			$scope.menus = result;
			$scope.$apply();
			$("#side-menu").metisMenu();
		});
		$scope.logout = function() {
			login.logout(function() {
				pages.open("login");
			});
		}
	} ]);

});