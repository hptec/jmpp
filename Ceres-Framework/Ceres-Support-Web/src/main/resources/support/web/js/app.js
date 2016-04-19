define([ 'angular', 'module', 'angular-ui-router', 'angular-require' ], function(angular, module) {
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

	function configState($stateProvider, $urlRouterProvider, $compileProvider, $locationProvider, $requireProvider) {

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

			var opt = {
				url : pg.uri,
				templateUrl : pg.tpl,
				data : pg.data
			}

			if (pg.deps != undefined) {
				var js = new Array();
				var css = new Array();
				var resolve = {};
				for ( var j in pg.deps) {
					var dep = pg.deps[j];
					if (dep.substr(0, 4) == "css!") {
						css.push(dep);
					} else {
						js.push(dep);
					}
				}
				if (js.length > 0) {
					resolve.deps = $requireProvider.requireJS(js);
				}
				if (css.length > 0) {
					resolve.css = $requireProvider.requireCSS(css);
				}
				opt.resolve = resolve;
			}
			console.log(opt);
			$stateProvider.state(pg.uri, opt);

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
	return app;
});