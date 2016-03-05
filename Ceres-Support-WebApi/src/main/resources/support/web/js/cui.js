define([ '/api/requirejs/module_def.js' ], function(module_def) {
	return {
		/**
		 * 启动项目
		 * 
		 * @returns
		 */
		bootstrap : function(cfg) {
			if (cfg.platformCategory == undefined || cfg.platformCategory == "") {
				throw new Error("platformCategory is requried!");
			}

			var defaultConfig = {
				application : '',
				appid : '',
				appsecret : '',
				baseUrl : '/',
				waitSeconds : 50,
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
			require.config({
				config : {
					'http' : {
						'platformCategory' : cfg.platformCategory,
						'onLoginRequired' : (cfg.onLoginRequired == undefined ? function() {
							alert("请求登录");
						} : cfg.onLoginRequired),
						'onHttpTimeout' : (cfg.onHttpTimeout == undefined ? function() {
							alert("请求超时");
						} : cfg.onHttpTimeout),
						'onHttpError' : (cfg.onHttpError == undefined ? function(xhr, errorText, srcObject) {
							console.log("http error:" + errorText, srcObject);
							alert("错误:" + srcObject);
							throw errorText;
						} : cfg.onHttpError),
						'onHttpNotFound' : (cfg.onHttpNotFound == undefined ? function(content, statusText, xhr) {
							alert(statusText);
							console.log(statusText, xhr);
							throw statusText;
						} : cfg.onHttpNotFound)
					}
				}
			});

//			require([ 'app' ], function(angular, starter) {
				var depUris = [ 'angular' ];

				if (cfg.configUri != undefined && cfg.configUri != "") {
					console.log("发现启动配置文件路径", cfg.configUri);
					depUris.push(cfg.configUri);
				}
				require(depUris, function(angular, starter) {
//					angular.element(document).ready(function() {
//						angular.bootstrap(document, [ 'app' ]);
//						angular.element(document).find('html').addClass('ng-app');
//					});
				});
//			});

		},
	};
});