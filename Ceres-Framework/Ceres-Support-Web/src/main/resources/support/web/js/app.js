define([ 'angular', 'angular-async-loader', 'module', 'angular-ui-router' ], function(angular, asyncLoader, module) {
	var angularModule = new Array();
	var config = module.config();

	// 加载模块
	for (i in config.jsModules) {
		var m = config.jsModules[i];
		if (m.angularModule != undefined) {
			angularModule.push(m.angularModule);
		}
	}
	console.log("angularModule:", angularModule);
	var app = angular.module('app', angularModule);

	function configState($stateProvider, $urlRouterProvider, $compileProvider, $locationProvider) {

		// Optimize load start with remove binding information inside the DOM
		// element
		$compileProvider.debugInfoEnabled(true);
		// app.config([ '$locationProvider' ], function($locationProvider) {
		$locationProvider.html5Mode(true);// 启用html5模式
		// });
		// Set default state
		// $urlRouterProvider.otherwise("/dashboard");
		$urlRouterProvider.otherwise("/common/login");
		$stateProvider

		// Dashboard - Main page
		.state('dashboard', {
			url : "/dashboard",
			templateUrl : "/api/classpath/query/support/web/console/theme/homer/views/dashboard.html",
			data : {
				pageTitle : 'Dashboard'
			}
		})

		// Common views
		.state('common', {
			abstract : true,
			url : "/common",
			templateUrl : "/api/classpath/query/support/web/console/theme/homer/views/common/content_empty.html",
			data : {
				pageTitle : 'Common'
			}
		}).state('common.login', {
			url : "/login",
			templateUrl : "/api/classpath/query/support/web/console/theme/homer/views/common_app/login.html",
			data : {
				pageTitle : 'Login page',
				specialClass : 'blank'
			}
		}).state('common.register', {
			url : "/register",
			templateUrl : "views/common_app/register.html",
			data : {
				pageTitle : 'Register page',
				specialClass : 'blank'
			}
		}).state('common.error_one', {
			url : "/error_one",
			templateUrl : "views/common_app/error_one.html",
			data : {
				pageTitle : 'Error 404',
				specialClass : 'blank'
			}
		}).state('common.error_two', {
			url : "/error_two",
			templateUrl : "views/common_app/error_two.html",
			data : {
				pageTitle : 'Error 505',
				specialClass : 'blank'
			}
		}).state('common.lock', {
			url : "/lock",
			templateUrl : "views/common_app/lock.html",
			data : {
				pageTitle : 'Lock page',
				specialClass : 'blank'
			}
		}).state('common.password_recovery', {
			url : "/password_recovery",
			templateUrl : "views/common_app/password_recovery.html",
			data : {
				pageTitle : 'Password recovery',
				specialClass : 'blank'
			}
		})

	}

	app.config(configState);

	app.start = function(callbackFunc) {
		angular.element(document).ready(function() {
			angular.bootstrap(document, [ 'app' ]);
			angular.element(document).find('html').addClass('ng-app');
			if (callbackFunc) {
				callbackFunc();
			}
		});
	}

	console.log("APP初始化")
	// initialze app module for async loader
	asyncLoader.configure(app);
	return app;
});