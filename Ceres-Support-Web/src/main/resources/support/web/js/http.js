define([ 'jquery', 'module', 'jquery-cookie' ], function($, module) {

	console.log("http模块配置", module.config());

	return {
		// 支持onSuccess,onTimeout,onError,onComplete,onNotFound
		load : function(context) {
			var moduleConfig = module.config();
			console.log("Http模块: 系统配置", moduleConfig);
			console.log("Http模块: 输入配置", context);

			// 系统请求,用户加载系统相关信息(非用户行为请求，不用校验会话信息)
			var url = context.url;
			if (context == undefined || context.url == undefined) {
				throw new Error("Url is requried!", context);
			}

			$.cookie("ceres_platform", moduleConfig.platform, {
				expired : 7,
				path : '/'
			});
			$.cookie("ceres_platform_key", moduleConfig.key, {
				expired : 7,
				path : '/'
			});
			$.cookie("ceres_platform_secret", moduleConfig.secret, {
				expired : 7,
				path : '/'
			});

			$.ajax({
				url : context.url,
				type : "POST",
				async : true,
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
					} else if (content == "login") {
						console.log("Http模块: 要求登录", content);
						if (moduleConfig.onLoginRequired != undefined) {
							moduleConfig.onLoginRequired(content);
							return;
						}
						return;
					} else if (content.startsWith("error:")) {
						console.log("Http模块: 返回错误", content);
						var func = (context.onError == undefined ? moduleConfig.onError : context.onError);
						func(xhr, statusText, content);
						return;
					} else {
						// 强制尝试错误判断
						try {
							var dataObj = JSON.parse(content);
							if (!dataObj.isSuccess && dataObj.code == "LOGIN_REQUIRED") {
								if (moduleConfig.onLoginRequired != undefined) {
									console.log("Http模块: 要求登录", dataObj);
									moduleConfig.onLoginRequired(dataObj);
									return;
								}
							} else if (!dataObj.isSuccess && dataObj.code == "PLATFORM_AUTH_INCORRECT") {
								alert("平台授权认证错误，请联系管理员!");
							}

						} catch (e) {
							var dataObj = content;
						}

						console.log("Http模块: 返回内容", dataObj);

						if (this.success != null) {
							this.success(dataObj, this);
						}
					}
				},
				complete : function(xhr, textStatus) {
					if (context.complete != null) {
						context.complete(xhr, textStatus);
					}
					/*
					 * 暂时取消进度条 hide_loading_bar();
					 */
				}

			});
		}
	}
});