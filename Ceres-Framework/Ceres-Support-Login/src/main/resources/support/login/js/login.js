define([ 'http', 'modal', '$' ], function(http, modal, $) {
	return {
		login : function(option) {
			if (option == undefined) {
				modal.toast("传入的登录参数为空");
				return;
			}

			var req = {};
			req = $.extend(req, option);
			req = $.extend(req, {
				url : "/api/login/doLogin",
				server : true
			});

			http.load(req);
		},
		/**
		 * 发送登录注册用的验证码
		 */
		sendSms : function(option) {
			var req = {};
			req = $.extend(req, option);
			req = $.extend(req, {
				url : "/api/login/sms",
				server : true
			});
			http.load(req);
		},
		logout : function(callback) {
			http.load({
				url : '/api/login/logout',
				server : true,
				complete : function() {
					callback && callback();
				}
			});
		},
		getDefinition : function(option) {
			var req = {};
			req = $.extend(req, option);
			req = $.extend(req, {
				url : "/api/login/definition",
				server : true
			});

			http.load(req);
		}
	};
});