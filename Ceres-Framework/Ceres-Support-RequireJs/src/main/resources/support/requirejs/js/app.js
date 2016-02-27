define([ 'angular', 'angular-async-loader', 'angular-ui-router' ], function(angular, asyncLoader) {

	var app = angular.module('app', [ 'ui.router' ]);

	// initialze app module for async loader
	asyncLoader.configure(app);
	return app;
});