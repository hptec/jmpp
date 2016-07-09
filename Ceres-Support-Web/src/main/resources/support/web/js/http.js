define([ 'module', '$', 'cache', 'pages' ], function(module, $, cache, pages) {
	var moduleConfig = module.config();

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

			// 如果不需要更新platform，那么authcode从这里获取
			if (moduleConfig.authcode == undefined) {
				var p = cache.get(module.config().appid + "_platform")
				moduleConfig.authcode = p ? p.platformAuthCode : undefined;
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
			context.data.ceres_platform = moduleConfig.platform;
			context.data.ceres_platform_authcode = moduleConfig.authcode;
			var sendRequest = {
				url : prefix + context.url,
				type : context.type||"POST",
				async : context.async == undefined ? true : context.async,
				dataType : "text",
				data : context.data,
				context : context,
				error : (context.onError == undefined ? moduleConfig.onHttpError : context.onError),
				timeout : (context.onTimeout == undefined ? moduleConfig.onHttpTimeout : context.onTimeout),
				success : function(content, statusText, xhr) {

					if (xhr.status == "404" || statusText == "error") {
						console.log("Http模块: 找不到或返回错误", content)
						var func = (context.onNotFound == undefined ? moduleConfig.onHttpNotFound : context.onNotFound);
						func(content, statusText, xhr);
						return;
					} else {
						// 强制尝试错误判断
						try {
							var dataObj = JSON.parse(content);
							if (!dataObj.isSuccess && dataObj.code == "LOGIN_REQUIRED") {
								if (moduleConfig.onLoginRequired != undefined) {
									console.log("Http模块: 要求登录", dataObj);
									moduleConfig.onLoginRequired(pages, dataObj);

									return;
								}
							} else if (!dataObj.isSuccess && dataObj.code == "PLATFORM_AUTH_INCORRECT") {
								// alert("平台授权认证错误，请联系管理员!");
							}

						} catch (e) {
							var dataObj = content;
						}

						//console.log("Http模块: 返回内容", dataObj);

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
						console.log("计数器:", this.__waiting.__root.__InProcessingCounter);
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
			console.log("Http模块: 请求" + tmpUrl, context);

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
			console.log("请求内容：" + JSON.stringify(sendRequest));

			if (moduleConfig.platform == "app") {
				var mui = require("mui");
				// sendRequest.headers = {
				// 'COOKIE' : "ceres_platform=" + moduleConfig.platform +
				// ";ceres_platform_authcode=" + moduleConfig.authcode + ";"
				// }
				mui.ajax(sendRequest.url, sendRequest);
			} else {
				$.cookie("ceres_platform", moduleConfig.platform, {
					expired : 7,
					path : '/'
				});
				$.cookie("ceres_platform_authcode", moduleConfig.authcode, {
					expired : 7,
					path : '/'
				});
				$.ajax(sendRequest);
			}
		},
		toUrl: function(uri){
			if(/:\/\//.test(uri)){
				return uri;
			}else{
				var host = moduleConfig.host;
				var url = host.server||"";
				if(!/:\/\//.test(url)){
					url = (host.protocal&&host.protocal||"http") + "://" + url;
				}
				if(host.port && !new RegExp("/:"+host.port + "$").test(url)){
					url += ":"+host.port;
				}
				if(!/\/$/.test(url)){
					url += "/";
				}
				
				if(/^\//.test(uri)){
					url += uri.substring(1);
				}else{
					url += uri;
				}
				return url;
			}
		}
	}
	return retObj;
});