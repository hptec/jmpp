// 加载各种配置和启动前文件
var files = [ //
'http',//
'app',//
'bootstrap',//
'icheck',//
'ceres-console/js/app-routes',//
'ceres-console/theme/homer/scripts/directives/directives',//
'css!api/classpath/query/support/web/console/theme/homer/styles/style.css',//
];
define(files, function(http, app) {

	app.start(function() {
		console.log("后台启动器加载");
		// location.href = "./console/common/login";

		$(".splash").hide();

		http.load({
			url : '/api/sayhi',
			success : function(obj) {
				console.log("HTTP返回", obj);
			}
		})
	});

});