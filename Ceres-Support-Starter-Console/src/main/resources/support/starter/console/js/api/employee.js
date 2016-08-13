define([ 'http', 'modal', 'cache', 'platform', 'app', 'login', 'angular' ], function(http, modal, cache, platform, app, login, angular) {

	return {
		__currentUser : undefined,
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
		getCurrentUser : function() {

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
					console.log("onLoginRequired");
					http.onLoginRequired();
				}
				return undefined;
			}

			return this.__currentUser;
		},
		getMine : function() {
			if (this.__currentUser == undefined) {
				var id = angular.element.cookie("COOKIE_REMEMBER_ID_" + platform.get().key);
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