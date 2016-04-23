define([ 'http', 'modal', 'cache', 'platform', 'app' ], function(http, modal, cache, platform, app) {

	return {
		__currentUser : undefined,
		login : function(loginId, loginPwd, remember, funcCallback) {
			if (loginId == undefined || loginId == "" || loginPwd == undefined || loginPwd == "") {

			} else {
				http.load({
					url : '/api/employee/doLogin',
					data : {
						loginId : loginId,
						loginPwd : loginPwd,
						remember : remember == undefined ? false : remember
					},
					context : this,
					success : function(result, context) {
						if (result.isSuccess) {
							context.__currentUser = result.object;
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

			if (this.__currentUser == undefined) {
				// 检测是否有持久化缓存
				var c = $.cookie("COOKIE_REMEMBER_TOKEN_" + platform.category());
				if (c != undefined) {
					// 有记住用户名
					var mine = this.getMine();
					if (mine != undefined) {
						this.__currentUser = mine;
						return mine;
					}
				}
				if (http.onLoginRequired) {
					console.log("onLoginRequired");
					http.onLoginRequired();
				}
				return undefined;
			}

			return this.__currentUser;
		},
		getMine : function() {
			if (this.__currentUser == undefined) {
				var id = $.cookie("COOKIE_REMEMBER_ID_" + platform.category());
				http.load({
					url : '/api/employee/get',
					data : {
						id : id
					},
					async : false,
					context : this,
					success : function(obj, context) {
						context.__currentUser = obj;
					}
				});
			}
			return this.__currentUser;
		}
	}
});