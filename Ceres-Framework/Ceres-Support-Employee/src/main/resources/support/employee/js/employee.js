define([ 'http', 'modal', 'cache', 'platform', 'app', 'login', 'angular', '/api/employee/me' ], function(http, modal, cache, platform, app, login, angular, me) {

	return {
		__currentUser : me,
		login : function(loginEntity, funcCallback) {
			if (loginEntity == undefined) {

			} else {
				login.login({
					data : loginEntity,
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
				})
			}
		},
		getCurrent : function() {

			if (this.__currentUser == undefined) {
				// 检测是否有持久化缓存
				var c = angular.element.cookie("COOKIE_REMEMBER_TOKEN_" + platform.get().key);
				if (c != undefined) {
					// 有记住用户名
					var mine = this.getMine();
					if (mine != undefined) {
						this.__currentUser = mine;
						return mine;
					}
				}
				if (http.onLoginRequired) {
					cui.log("onLoginRequired");
					http.onLoginRequired();
				}
				return undefined;
			}

			return this.__currentUser;
		}
	}
});