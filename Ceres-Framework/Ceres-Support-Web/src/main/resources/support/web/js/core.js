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
					waitSeconds : 10,
					map : {
						'*' : {
							'css' : 'require-css',
							'style' : 'require-less',
							'async' : 'require-async'
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
							var uri = jsModule["uri"];
							if (uri instanceof Object) {
								// 对象，要判断开发模式，如果是dev模式，则从服务器映射文件
								if (cfg.devMode != undefined && cfg.devMode == "dev") {
									// 开发模式，从远端加载js
									var remote = jsModule.uri.remote;
									if (cfg.host != undefined) {
										var p = (cfg.host.protocal == undefined ? "http" : cfg.host.protocal) + "://";
										if (cfg.host.server != undefined) {
											p = p + cfg.host.server;
											if (cfg.host.port != undefined) {
												p = p + ":" + cfg.host.port;
											}
										}
										remote = p + remote;
									}
									console.log("开发者模式：使用服务器端文件 [" + remote + "]");
									shimConfig.paths[jsModule.name] = remote;
								} else {
									console.log("生产模式：使用本地文件 [" + jsModule.uri.local + "]")
									shimConfig.paths[jsModule.name] = jsModule.uri.local;
								}
							} else {
								shimConfig.paths[jsModule.name] = jsModule.uri;
							}

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
					'require-less' : {
						path : "../../../.",
						rootPath : ""
					},
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
					},
					'modal' : cfg.modal,
					'http' : {}
				};

				if (cfg['http'] != undefined) {
					configObj['http'] = cfg.http;
				}
				configObj['http'].platform = cfg.platform.category;
				configObj['http'].appid = cfg.platform.key;
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