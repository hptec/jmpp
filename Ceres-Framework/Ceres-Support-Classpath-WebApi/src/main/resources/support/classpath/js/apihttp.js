define([ 'require', 'jquery', 'module' ], function(require, $, module) {
	console.log("apihttp");
	console.log(module.config());
	return {
		load : function(param) {
			$.ajax({
				url : param.url,
				type : "POST",
				async : true,
				dataType : "text",
				data : data,
				context : param.context == undefined ? param : param.context,
				error : function(xhr, errorText, srcObject) {
					$c.log("error:" + errorText, srcObject);
					alert("错误:" + srcObject);
					throw errorText;
				},
				timeout : function() {
					alert(timeout);
				},
				success : function(content, statusText, xhr) {
					if (xhr.status == "404" || statusText == "error") {
						alert("当前页面未找到");
						return;
					} else if (content == "login") {
						// 如果服务器要求客户端重登录，则要清除本地的remember字符串，因为字符串已经过期，便于显示登录页面
						$c.fn.cache("_currentUser", {});
						location = $c.loginUrl;
					} else if (content.startWith("error:")) {
						alert(content);
						return;
					} else {
						var dataObj = JSON.parse(content);
						// 回调成功消息
						$c.log("请求页面 [" + param.url + "] 返回结果:", dataObj);

						if (param.success != null) {
							param.success(dataObj, this);
						}
					}
				},
				complete : function(xhr, textStatus) {
					if (param.complete != null) {
						param.complete(xhr, textStatus);
					}
					/*
					 * 暂时取消进度条 hide_loading_bar();
					 */
				}

			});
		}
	};
});