define([ 'jquery', 'module', 'jquery-cookie' ], function($, module) {
	var moduleConfig = module.config();

	var retObj = {
		onLoginRequired : moduleConfig.onLoginRequired,
		onHttpTimeout : moduleConfig.onHttpTimeout,
		onHttpError : moduleConfig.onHttpError,
		onHttpNotFound : moduleConfig.onHttpNotFound,
		// 支持onSuccess,onTimeout,onError,onComplete,onNotFound
		load : function(context) {

			console.log("Http模块: 请求" + context.url, context);

			// 系统请求,用户加载系统相关信息(非用户行为请求，不用校验会话信息)
			var url = context.url;
			if (context == undefined || context.url == undefined) {
				throw new Error("Url is requried!", context);
			}

			$.cookie("ceres_platform", moduleConfig.platform, {
				expired : 7,
				path : '/'
			});
			$.cookie("ceres_platform_authcode", moduleConfig.authcode, {
				expired : 7,
				path : '/'
			});

			context.data.ceres_platform = moduleConfig.platform;
			context.data.ceres_platform_authcode = moduleConfig.authcode;

			$.ajax({
				url : context.url,
				type : "POST",
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
							this.success(dataObj, this.context == undefined ? this : this.context);
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
	return retObj;
});