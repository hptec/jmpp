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

			var starterJsUri = module_def.starterJs[cfg.applicationCategory];
			console.log("启动器位置", starterJsUri);

			require([ 'angular', 'ceres-requirejs/js/app-routes' ], function(angular) {
				angular.element(document).ready(function() {
					// 加载对应console并 启动angularjs
					angular.bootstrap(document, [ 'app' ]);
					angular.element(document).find('html').addClass('ng-app');

					require([ starterJsUri ], function(starter) {
						console.log("启动器:", starter);
						if (starter != undefined && starter.start != undefined) {
							starter.start();
						}
					});

				});

			});

		}
	};
});