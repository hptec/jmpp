define([ 'app', 'platform', 'icheck' ], function(app, platform) {

	app.controller('commonLoginCtrl', [ '$scope', function($scope) {

		$scope.platform = platform.get();
	} ]);

});