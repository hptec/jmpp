define([ 'app', 'platform', 'employee' ], function(app, platform, employee) {

	var retObj = {
		__mineMenu : undefined,
		getMineMenus : function(data) {
			if (this.__mineMenu == undefined) {
				
			}
		}

	}

	app.controller('workbenchCtrl', [ '$scope', '$location', function($scope, $location) {
		$scope.platform = platform.get();
		$scope.currentUser = employee.getCurrentUser();
	} ]);

	return retObj;
});