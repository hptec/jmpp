define([ 'app', 'platform', 'employee', 'menu', 'pages', 'login' ], function(app, platform, employee, menu, pages, login) {

	app.controller('workbenchCtrl', [ '$scope', '$location', '$state', function($scope, $location, $state) {
		$scope.platform = platform.get();
		$scope.currentUser = employee.getCurrentUser();
		$scope.jump = function(url) {
			$state.go(url);
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