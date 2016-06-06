define([ 'app', 'platform', 'employee', 'http', 'cache', 'menu', 'pages' ], function(app, platform, employee, http, cache, menu, pages) {

	app.controller('workbenchCtrl', [ '$scope', '$location', function($scope, $location) {
		$scope.platform = platform.get();
		$scope.currentUser = employee.getCurrentUser();
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
	} ]);

});