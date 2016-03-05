var cfg = {
	'cdn_path' : '//cdn.bootcss.com',
	module_version : {
		'angular.js' : '1.5.0-rc.0',
		'angular-ui-router' : '1.0.0-alpha0',
		'bootstrap' : '3.3.6',
		'device.js' : '0.2.7',
		'jquery' : '2.2.0',
		'require-css' : '0.1.8',
	//
	},

	cdn : function(module_name, file_name) {
		var str = this['cdn_path'] + '/' + module_name + '/' + this.module_version[module_name] + "/" + file_name;
		return str;
	},
	classpath : function(file_name) {
		var str = '/api/classpath/query/' + file_name;
		return str;
	}

}

require.config({
	baseUrl : '/',
	paths : {
		'classpath' : '/api/classpath/query',
		'console' : '/api/classpath/query/support/web/console',
		//
		'ceres-loader' : cfg.classpath("support/web/console/ceres-loader"),

		//
		'angular' : cfg.cdn('angular.js', 'angular'),
		'angular-local' : cfg.cdn('angular.js', 'i18n/angular-locale_zh-cn'),
		'angular-ui-router' : cfg.cdn('angular-ui-router', 'angular-ui-router.min'),
		'angular-async-loader' : cfg.classpath('support/web/console/js/angular-async-loader/angular-async-loader.min'),
		'bootstrap' : cfg.cdn('bootstrap', 'js/bootstrap.min'),
		'device' : cfg.cdn('device.js', 'device.min'),
		'jquery' : cfg.cdn('jquery', 'jquery.min'),
		'require-css' : cfg.cdn('require-css', "css"),
		'app-routes' : cfg.classpath('support/web/console/js/app-routes'),
		'apihttp' : cfg.classpath('support/classpath/js/apihttp'),
	//
	},
	shim : {
		'angular' : {
			exports : 'angular'
		},
		'angular-ui-router' : {
			deps : [ 'angular', 'angular-local' ]
		},
		'angular-local' : {
			deps : [ 'angular' ]
		},
		'bootstrap' : {
			deps : [ 'jquery', 'css!' + cfg.cdn('bootstrap', 'css/bootstrap.min') ]
		},
		'ceres-loader' : {
			deps : [ 'bootstrap', 'css!' + cfg.classpath('support/web/console/theme/homer/styles/style') ]
		},
		'jquery' : {
			exports : '$'
		},
	//
	},
	map : {
		'*' : {
			'css' : 'require-css'
		}
	},
	config : {
		'apihttp' : {
			'appid' : 'a95d18f28a7a4608b372ac575a5fbaa6',
			'appsecret' : '3b93bfb606684064ba25fa22371af89e'
		}
	}
//
});

require([ 'angular', 'app-routes' ], function(angular) {
	require.config({
		config : {
			'apihttp' : {
				'appid' : '1',
				'appsecret' : '2'
			}
		}
	});

	require(['ceres-loader']);

	angular.element(document).ready(function() {
		angular.bootstrap(document, [ 'app' ]);
		angular.element(document).find('html').addClass('ng-app');
	});
});