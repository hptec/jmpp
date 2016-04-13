define([], function() {

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

			require([ '/api/web/systemconfigs.js?platform=' + cfg.platform ], function(sysConfig) {
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
				require.config(defaultConfig);

				console.log("模块配置", sysConfig);
				var shimConfig = {
					"shim" : {

					},
					"paths" : sysConfig.paths == undefined ? {} : sysConfig.paths
				}

				// 根据jsModule设置requriejs的配置
				if (sysConfig.jsModules != undefined) {
					for (i in sysConfig.jsModules) {
						var jsModule = sysConfig.jsModules[i];
						if (shimConfig.shim[jsModule.name] == undefined) {
							shimConfig.shim[jsModule.name] = {};
						}

						if (jsModule["exports"] != undefined) {
							shimConfig.shim[jsModule.name]["exports"] = jsModule["exports"];
						}
						if (jsModule["deps"] != undefined) {
							shimConfig.shim[jsModule.name]["deps"] = jsModule["deps"];
						}
						if (jsModule["uri"] != undefined) {
							shimConfig.paths[jsModule.name] = jsModule.uri;
						}
					}
				}

				console.log("RequireJs配置", shimConfig);
				require.config(shimConfig);

				console.log("输入配置", cfg);

				require.config(cfg);
				require.config({
					config : {
						'http' : {
							'platform' : cfg.platform,
							'key' : cfg.platformKey,
							'secret' : cfg.platformSecret,
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
							jsModules : sysConfig.jsModules,
							pages : sysConfig.pages
						}
					}
				});

				var boot = sysConfig.starter;
				if (boot != undefined && boot.uri != undefined && boot.uri != "") {
					console.log("启动模块 [" + boot.platform + "][" + boot.uri + "]", boot);
					require([ boot.uri ], function() {
						// 启动
					});
				}
			});

		}

	};
});