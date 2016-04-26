define([], function() {

	return {
		/**
		 * 启动项目
		 * 
		 * @returns
		 */
		bootstrap : function(cfg) {
			if (cfg.platform == undefined || cfg.platform.category == undefined || cfg.platform.category == "") {
				throw new Error("platform category is requried!");
			}

			if (cfg.configUrl == undefined) {
				cfg.configUrl = "/api/web/systemconfigs.js";
			}

			require([ cfg.configUrl + '?platform=' + cfg.platform.category ], function(sysConfig) {
				var defaultConfig = {
					application : '',
					appid : '',
					appsecret : '',
					baseUrl : cfg.baseUrl == undefined ? '' : cfg.baseUrl,
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

				var angularModule = new Array();
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
						// 判读那些模块需要预先加载
						if (jsModule.angularModule != undefined) {
							angularModule.push(jsModule.name);
						}

					}
				}

				console.log("RequireJs配置", shimConfig);
				require.config(shimConfig);

				console.log("输入配置", cfg);

				require.config(cfg);

				var configObj = {
					'app' : {
						jsModules : sysConfig.jsModules,
						pages : sysConfig.pages,
						html5mode : cfg.starter.html5mode
					},
					'pages' : {
						pages : sysConfig.pages,
						platform : cfg.platform.category
					},
					'platform' : {
						'platform' : cfg.platform.category,
						'appid' : cfg.platform.key,
						'appsecret' : cfg.platform.secret
					}
				};
				if (cfg['http'] != undefined) {
					configObj['http'] = cfg.http;
					configObj['http'].platform = cfg.platform.category;
				}
				configObj['http'].host = cfg.host;

				require.config({
					config : configObj
				});

				var boot = cfg.starter;
				if (boot != undefined && boot.name != undefined && boot.name != "") {
					console.log("启动模块 [" + boot.name + "]", boot);
					angularModule.push(boot.name);
					require(angularModule, function() {
						// 启动
					});
				}
			});

		}

	};
});