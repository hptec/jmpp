(function(window, undefined) {

	var cui = (function() {
		// 构建jQuery对象
		var cui = {
			__config : {
				defaultUri : {
					requirejs : '/api/classpath/query/support/web/bower_components/requirejs/require.js',
					systemconfig : '/api/web/systemconfigs.js'
				},
				baseUrl : '',
				waitSeconds : 10,
				map : {
					'*' : {
						'css' : 'require-css',
						'style' : 'require-less',
						'async' : 'require-async'
					}
				}
			},
			config : function(opt) {
				this.__config = this.extend(true, this.__config, opt);
			},
			ready : function(reqArgs, callFunc) {
				if (this.__config.platform == undefined || this.__config.platform.category == undefined) {
					// 强制指定运行平台
					throw new Error("[ERROR] platform未指定");
				}
				// 配置启动器
				cui.config({
					starter : {
						args : cui.isFunction(reqArgs) ? undefined : reqArgs,
						func : cui.isFunction(reqArgs) ? reqArgs : callFunc
					}
				})

				// 检测reqiure文件是否加载
				this.loadScript(this.__config.defaultUri.requirejs, function() {
					console.log("[INFO] RequireJs成功加载");
					require([ cui.__config.defaultUri.systemconfig + '?platform=' + cui.__config.platform.category ], function(sysConfig) {

						console.log("[INFO] 默认配置成功加载", cui.__config);
						require.config(cui.__config);

						console.log("[INFO] 系统配置成功加载", sysConfig);
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

						var configObj = {
							'require-less' : {
								path : "../../../.",
								rootPath : ""
							},
							'app' : {
								jsModules : sysConfig.jsModules,
								pages : sysConfig.pages,
								html5mode : cui.__config.html5mode
							},
							'pages' : {
								pages : sysConfig.pages
							},
							'platform' : {
								'platform' : cui.__config.platform.category,
								'appid' : cui.__config.platform.key,
								'appsecret' : cui.__config.platform.secret
							},
							'modal' : cui.__config.modal,
							'http' : {}
						};

						if (cui.__config['http'] != undefined) {
							configObj['http'] = cui.__config.http;
						}
						configObj['http'].platform = cui.__config.platform.category;
						configObj['http'].appid = cui.__config.platform.key;
						configObj['http'].host = cui.__config.host;

						require.config({
							config : configObj
						});

						var boot = cui.__config.starter;
						if (boot != undefined) {
							console.log("启动应用程序", boot);
							if (cui.isArray(boot.args)) {
								for (argsIndex in boot.args) {
									angularModule.push(boot.args[argsIndex]);
								}
							}

							require(angularModule, boot.func == undefined ? function() {
								console.log("[WARN] 未指定启动函数");
							} : boot.func);
						}
					});

				});

			},
			loadScript : function(src, func) {
				var found = false;
				var scripts = document.getElementsByTagName('script');
				if (scripts) {
					for (i = 0; i < scripts.length; i += 1) {
						var sc = scripts[i];
						if (sc.getAttribute("src") == src) {
							// 已经加载
							func();
							return;
						}
					}
				}
				// 未找到，加载标记
				var node = document.createElement('script');
				node.type = 'text/javascript';
				node.charset = 'utf-8';
				node.async = true;
				node.src = src;
				node.addEventListener('load', func);
				document.head.appendChild(node);
			},
			extend : function() {
				var options, name, src, copy, copyIsArray, clone, target = arguments[0] || {}, i = 1, length = arguments.length, deep = false;

				// Handle a deep copy situation
				if (typeof target === "boolean") {
					deep = target;

					// Skip the boolean and the target
					target = arguments[i] || {};
					i++;
				}

				// Handle case when target is a string or something (possible in
				// deep copy)
				if (typeof target !== "object" && !this.isFunction(target)) {
					target = {};
				}

				if (i === length) {
					target = this;
					i--;
				}

				for (; i < length; i++) {

					// Only deal with non-null/undefined values
					if ((options = arguments[i]) != null) {

						// Extend the base object
						for (name in options) {
							src = target[name];
							copy = options[name];

							// Prevent never-ending loop
							if (target === copy) {
								continue;
							}

							// Recurse if we're merging plain objects or arrays
							if (deep && copy && (this.isPlainObject(copy) || (copyIsArray = this.isArray(copy)))) {

								if (copyIsArray) {
									copyIsArray = false;
									clone = src && this.isArray(src) ? src : [];

								} else {
									clone = src && this.isPlainObject(src) ? src : {};
								}

								// Never move original objects, clone them
								target[name] = this.extend(deep, clone, copy);

								// Don't bring in undefined values
							} else if (copy !== undefined) {
								target[name] = copy;
							}
						}
					}
				}

				// Return the modified object
				return target;
			},
			isPlainObject : function(obj) {
				var key;

				// Not plain objects:
				// - Any object or value whose internal [[Class]] property is
				// not "[object Object]"
				// - DOM nodes
				// - window
				if (this.type(obj) !== "object" || obj.nodeType || this.isWindow(obj)) {
					return false;
				}

				// Not own constructor property must be Object
				if (obj.constructor && !hasOwn.call(obj, "constructor") && !hasOwn.call(obj.constructor.prototype || {}, "isPrototypeOf")) {
					return false;
				}

				// Own properties are enumerated firstly, so to speed up,
				// if last one is own, then all properties are own
				for (key in obj) {
				}

				return key === undefined || hasOwn.call(obj, key);
			},
			type : function(obj) {
				if (obj == null) {
					return obj + "";
				}

				// Support: Android<4.0, iOS<6 (functionish RegExp)
				return typeof obj === "object" || typeof obj === "function" ? class2type[toString.call(obj)] || "object" : typeof obj;
			},
			isWindow : function(obj) {
				return obj != null && obj === obj.window;
			},
			isArray : Array.isArray,
			isFunction : function(obj) {
				return this.type(obj) === "function";
			},
			each : function(obj, callback) {
				var length, i = 0;

				if (isArrayLike(obj)) {
					length = obj.length;
					for (; i < length; i++) {
						if (callback.call(obj[i], i, obj[i]) === false) {
							break;
						}
					}
				} else {
					for (i in obj) {
						if (callback.call(obj[i], i, obj[i]) === false) {
							break;
						}
					}
				}

				return obj;
			},

		}
		function isArrayLike(obj) {

			// Support: iOS 8.2 (not reproducible in simulator)
			// `in` check used to prevent JIT error (gh-2145)
			// hasOwn isn't used here due to false negatives
			// regarding Nodelist length in IE
			var length = !!obj && "length" in obj && obj.length, type = cui.type(obj);

			if (type === "function" || cui.isWindow(obj)) {
				return false;
			}

			return type === "array" || length === 0 || typeof length === "number" && length > 0 && (length - 1) in obj;
		}
		var class2type = {};
		var toString = class2type.toString;

		var hasOwn = class2type.hasOwnProperty;

		cui.each("Boolean Number String Function Array Date RegExp Object Error Symbol".split(" "), function(i, name) {
			class2type["[object " + name + "]"] = name.toLowerCase();
		});

		return cui;

	})();

	window.cui = window.$C = window.$c = cui;

})(window);