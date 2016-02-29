define([ '/api/requirejs/module_def.js' ], function(module_def) {
	return {
		/**
		 * 启动项目
		 * 
		 * @returns
		 */
		bootstrap : function(cfg) {
			if (cfg.applicationCategory == undefined || cfg.applicationCategory == "") {
				throw new Error("applicationCategory is requried!");
			}

			var defaultConfig = {
				application : '',
				appid : '',
				appsecret : '',
				baseUrl : '/',
				waitSeconds : 5,
				map : {
					'*' : {
						'css' : 'require-css'
					}
				}
			}

			console.log("默认配置", defaultConfig);
			console.log("输入配置", cfg);
			console.log("系统级模块定义", module_def);
			require.config(defaultConfig);
			require.config(module_def);
			require.config(cfg);

			var depUris = [ 'angular', 'app' ];

			if (cfg.configUri != undefined && cfg.configUri != "") {
				console.log("发现启动配置文件路径", cfg.configUri);
				depUris.push(cfg.configUri);
			}
			require(depUris, function(angular, app, starter) {
				angular.element(document).ready(function() {
					angular.bootstrap(document, [ 'app' ]);
					angular.element(document).find('html').addClass('ng-app');
				});
			});
		},
	};
});