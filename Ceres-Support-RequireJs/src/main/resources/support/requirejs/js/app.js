define([ 'angular', 'angular-async-loader', 'angular-ui-router' ], function(angular, asyncLoader) {

	var app = angular.module('app', [ 'ui.router' ]);

	angular.element(document).ready(function() {
		angular.bootstrap(document, [ 'app' ]);
		angular.element(document).find('html').addClass('ng-app');
	});
	
	
	// initialze app module for async loader
	asyncLoader.configure(app);
	return app;
});