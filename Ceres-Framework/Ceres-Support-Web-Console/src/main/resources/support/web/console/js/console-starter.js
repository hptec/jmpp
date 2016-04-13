// 加载各种配置和启动前文件
define([ 'jquery' ], function() {
	
	require([ '/api/console/appconfig.js', 'directives',//
	'icheck',//
	'css!api/classpath/query/support/web/console/theme/homer/styles/style.css',//
	], function(appConfig) {
		console.log("页面文件", appConfig);

		require([ 'http', 'app' ], function(http, app) {

			var afterStart = function() {

				console.log("后台启动器加载");
				// location.href = "./console/common/login";

				$(".splash").hide();

				http.load({
					url : '/api/sayhi',
					success : function(obj) {
						console.log("HTTP返回", obj);
					}
				});

			}
			app.start(afterStart);

		});

	})

});
