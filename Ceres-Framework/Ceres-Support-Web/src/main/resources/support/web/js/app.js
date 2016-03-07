define([ 'angular', 'angular-async-loader', 'module', 'angular-ui-router' ], function(angular, asyncLoader, module) {

	var app = angular.module('app', module.config().requiredModule);
	// var app = angular.module('app', [ 'ui.router', // Angular flexible
	// routing
	// 'ngSanitize', // Angular-sanitize
	// 'ui.bootstrap', // AngularJS native directives for Bootstrap
	// 'angular-flot', // Flot chart
	// 'angles', // Chart.js
	// 'angular-peity', // Peity (small) charts
	// 'cgNotify', // Angular notify
	// 'angles', // Angular ChartJS
	// 'ngAnimate', // Angular animations
	// 'ui.map', // Ui Map for Google maps
	// 'ui.calendar', // UI Calendar
	// 'summernote', // Summernote plugin
	// 'ngGrid', // Angular ng Grid
	// 'ui.tree', // Angular ui Tree
	// 'bm.bsTour', // Angular bootstrap tour
	// 'datatables', // Angular datatables plugin
	// 'xeditable', // Angular-xeditable
	// 'ui.select', // AngularJS ui-select
	// 'ui.sortable', // AngularJS ui-sortable
	// 'ui.footable', // FooTable
	// 'angular-chartist', // Chartist
	// 'gridshore.c3js.chart', // C3 charts
	// 'datatables.buttons', // Datatables Buttons
	// 'angular-ladda', // Ladda - loading buttons
	// 'ui.codemirror' // Ui Codemirror
	// ]);
	app.start = function(callbackFunc) {
		angular.element(document).ready(function() {
			angular.bootstrap(document, [ 'app' ]);
			angular.element(document).find('html').addClass('ng-app');
			callbackFunc();
		});
	}

	console.log("APP初始化")
	// initialze app module for async loader
	asyncLoader.configure(app);
	return app;
});