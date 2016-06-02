define([ 'http', 'modal', '$' ], function(http, modal, $) {
	return {
		login : function(option) {
			if (option == undefined) {
				modal.toast("传入的登录参数为空");
				return;
			}

			var req = {};
			req = $.extends(req, option);
			req = $.extends(req, {
				url : "/api/login/doLogin",
				server : true
			});

			http.load(req);
		},
		logout : function() {

		}
	};
});