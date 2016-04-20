define([ 'app', 'platform', 'employee', 'http', 'cache', 'menu' ], function(app, platform, employee, http, cache, menu) {

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
		$scope.menus = menu.getMine(function(result) {
			$scope.menus = result;
			$scope.$apply();
			$("#side-menu").metisMenu();
		});
	} ]);

	return retObj;
});