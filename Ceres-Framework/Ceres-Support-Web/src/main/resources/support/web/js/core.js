define([ '/api/requirejs/module_def.js' ], function(module_def) {
	return {
		/**
		 * 启动项目
		 * 
		 * @returns
		 */
		bootstrap : function(cfg) {
			if (cfg.platform == undefined || cfg.platform == "") {
				throw new Error("platform is requried!");
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
						'platform' : cfg.platform,
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
					},
					'app' : {
						'requiredModule' : module_def.requiredModule,
						'boot' : module_def.bootConfig[cfg.platform]
					}
				}
			});

			var boot = module_def.bootConfig[cfg.platform];
			if (boot != undefined && boot.bootstrapJs != undefined && boot.bootstrapJs != "") {
				console.log("启动模块 [" + boot.platform + "][" + boot.bootstrapJs + "]", boot);
				require([ boot.bootstrapJs ], function() {
					// 启动
				});
			}

		},
	};
});