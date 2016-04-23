define([ 'app', 'platform', 'employee', 'http', 'cache', 'menu', 'pages' ], function(app, platform, employee, http, cache, menu, pages) {

	var retObj = {
		__mineMenu : undefined,
		getMineMenus : function(data) {
			if (this.__mineMenu == undefined) {
				http.load({
					url : '/api/menu/mine',
					success : function(result) {
						console.log(result);
					}
				});
			}
		}

	}

	app.controller('workbenchCtrl', [ '$scope', '$location', function($scope, $location) {
		$scope.platform = platform.get();
		$scope.currentUser = employee.getCurrentUser();
		$scope.jump = function(url) {
			pages.open({
				url : url
			});
		}
		// $scope.menus = menu.getMine(function(result) {
		// $scope.menus = result;
		// $scope.$apply();
		// $("#side-menu").metisMenu();
		// });
	} ]);

	return retObj;
});