define([ 'app', 'platform', 'employee', 'pages', 'login', 'angular' ], function(app, platform, employee, pages, login, angular) {

	app.controller('workbenchCtrl', [ '$scope', '$location', '$state', function($scope, $location, $state) {
		$scope.platform = platform.get();
		$scope.currentUser = employee.getCurrent();
		
		$scope.logout = function() {
			login.logout(function() {
				pages.open("login");
			});
		}
	} ]);

});