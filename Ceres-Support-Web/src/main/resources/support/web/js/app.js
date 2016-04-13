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

		$compileProvider.debugInfoEnabled(false);
		var html5mode = config.html5mode;
		if (html5mode != undefined && html5mode) {
			console.log("启用html5模式");
			$locationProvider.html5Mode(true);// 启用html5模式
		}

		for ( var i in config.pages) {
			var pg = config.pages[i];
			if (pg.defaultPage != undefined && pg.defaultPage) {
				$urlRouterProvider.otherwise(pg.uri);
			}

			$stateProvider.state(pg.uri, {
				url : pg.uri,
				templateUrl : pg.tpl,
				data : pg.data
			})

		}

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