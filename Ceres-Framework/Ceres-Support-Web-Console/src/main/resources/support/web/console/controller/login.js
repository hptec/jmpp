define([], function() {
	var app = require('console/js/app');

	// dynamic load services here or add into dependencies of state config
	// require('../services/usersService');

	app.controller('usersCtrl', [ '$scope', function($scope) {
		// shortcut to get angular injected service.
		var userServices = app.get('usersService');

		$scope.userList = usersService.list();
	} ]);

});