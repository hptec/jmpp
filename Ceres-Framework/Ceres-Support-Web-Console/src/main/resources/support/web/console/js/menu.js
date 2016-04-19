define([ 'http', 'employee' ], function(http, modal, cache, platform) {

	return {
		login : function(loginId, loginPwd, remember, funcCallback) {
			if (loginId == undefined || loginId == "" || loginPwd == undefined || loginPwd == "") {

			} else {
				http.load({
					url : '/api/console/employee/doLogin',
					data : {
						loginId : loginId,
						loginPwd : loginPwd,
						remember : remember == undefined ? false : remember
					},
					success : function(result) {
						if (result.isSuccess) {
							// 成功则存入缓存
							var key = "current_user_" + platform.category();
							cache.set(key, result.object);
						}

						// 通知回调
						if (funcCallback) {
							funcCallback(result);
						}
					}
				});
			}
		},
		getCurrentUser : function() {
			var key = "current_user_" + platform.category();
			var e = cache.get(key);

			return e;
		}
	}
});