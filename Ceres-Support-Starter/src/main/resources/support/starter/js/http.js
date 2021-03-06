define([ 'module', 'cache', 'pages', 'angular' ], function(module, cache, pages, angular) {
	var moduleConfig = module.config();
	var $ = angular.element;

	var retObj = {
		onLoginRequired : moduleConfig.onLoginRequired,
		onHttpTimeout : moduleConfig.onHttpTimeout,
		onHttpError : moduleConfig.onHttpError,
		onHttpNotFound : moduleConfig.onHttpNotFound,
		// 支持onSuccess,onTimeout,onError,onComplete,onNotFound
		__InProcessingCounter : 0, // 正在发送http的计数器，用于隐藏显示浮动
		load : function(context) {

			// 系统请求,用户加载系统相关信息(非用户行为请求，不用校验会话信息)
			var url = context.url;
			if (context == undefined || context.url == undefined) {
				throw new Error("Url is requried!", context);
			}

			var prefix = "";
			if (moduleConfig.host != undefined && context.server) {
				var host = moduleConfig.host;
				if (host.port != undefined) {
					if (host.server != undefined) {
						prefix = "" + host.server + ":" + host.port;
					} else {
						prefix = "localhost" + ":" + host.port
					}
				} else if (host.server != undefined) {
					prefix = '' + host.server;
				}

				if (host.protocal != undefined) {
					prefix = host.protocal + "://" + prefix;
				}
			}

			if (context.data == undefined) {
				context.data = {};
			}
			context.data.ceres_platform_key = moduleConfig.platform.key;
			var sendRequest = {
				url : prefix + context.url,
				type : context.type || "POST",
				async : context.async == undefined ? true : context.async,
				dataType : "text",
				data : context.data,
				context : context,
				error : (context.onError == undefined ? moduleConfig.onHttpError : context.onError),
				timeout : (context.onTimeout == undefined ? moduleConfig.onHttpTimeout : context.onTimeout),
				success : function(content, statusText, xhr) {

					if (xhr.status == "404" || statusText == "error") {
						cui.log("Http模块: 找不到或返回错误", content)
						var func = (context.onNotFound == undefined ? moduleConfig.onHttpNotFound : context.onNotFound);
						func(content, statusText, xhr);
						return;
					} else {
						// 强制尝试错误判断
						try {
							var dataObj = JSON.parse(content);
							if (!dataObj.isSuccess && dataObj.code == "LOGIN_REQUIRED") {
								if (moduleConfig.onLoginRequired != undefined) {
									cui.log("Http模块: 要求登录", dataObj);
									moduleConfig.onLoginRequired(pages, dataObj);

									return;
								}
							} else if (!dataObj.isSuccess && dataObj.code == "PLATFORM_AUTH_INCORRECT") {
								// alert("平台授权认证错误，请联系管理员!");
							}

						} catch (e) {
							var dataObj = content;
						}
						if (moduleConfig.platform.category == "app") {
							cui.log("Http模块: 返回内容", JSON.stringify(dataObj));
						} else {
							cui.log("Http模块: 返回内容", dataObj);
						}

						if (this.success != null) {
							this.success(dataObj, this.context == undefined ? this : this.context);
						}
					}
				},
				complete : function(xhr, textStatus) {
					if (context.complete != null) {
						context.complete(xhr, textStatus);
					}
					if (this.__waiting != undefined) {
						cui.log("计数器:", this.__waiting.__root.__InProcessingCounter);
						this.__waiting.__root.__InProcessingCounter--;
						if (this.__waiting.__root.__InProcessingCounter <= 0) {
							this.__waiting.__root.__InProcessingCounter = 0;
							this.__waiting.__config.close(this.__waiting.__handle, this.__waiting.__config);
						}
					}

				}

			}
			var tmpUrl = context.url;
			if (sendRequest.url != context.url) {
				tmpUrl = context.url + " ==> " + sendRequest.url;
			}
			cui.log("Http模块: 请求" + tmpUrl, context);

			// 开始计数
			if (context.waiting == true || typeof (context.waiting) == "object") {
				// 默认是开启的
				var __waitingConfig = moduleConfig.waiting;
				if (typeof (context.waiting) == "object") {
					// 是对象说明覆盖设置
					__waitingConfig = $.extend(true, __waitingConfig, context.waiting);
				}

				var waitingHandle = __waitingConfig.show(__waitingConfig);
				this.__InProcessingCounter++;
				context.__waiting = {
					__handle : waitingHandle,
					__config : __waitingConfig,
					__root : this
				}

			}
			if (moduleConfig.platform.category == "app") {
				cui.log("请求内容：" + JSON.stringify(sendRequest));
				require([ "mui" ], function(mui) {
					mui.ajax(sendRequest.url, sendRequest);
				});

			} else {
				$.ajax(sendRequest);

			}
		},
		toUrl : function(uri) {
			var host = moduleConfig.host;
			if (/:\/\//.test(uri) || !host) {
				return uri;
			} else {
				var url = host && host.server || "";
				if (!/:\/\//.test(url)) {
					url = (host && host.protocal && host.protocal || "http") + "://" + url;
				}
				if (host && host.port && !new RegExp("/:" + host.port + "$").test(url)) {
					url += ":" + host.port;
				}
				if (!/\/$/.test(url)) {
					url += "/";
				}

				if (/^\//.test(uri)) {
					url += uri.substring(1);
				} else {
					url += uri;
				}
				return url;
			}
		}
	}
	return retObj;
});