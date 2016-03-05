define([ 'jquery', 'http', 'app', 'css!api/classpath/query/support/web/console/theme/homer/styles/style.css', 'bootstrap', 'icheck', 'ceres-console/theme/homer/scripts/directives/directives',
		'ceres-console/js/app-routes' ], function($, http, app) {

	console.log("后台启动器加载");
	// location.href = "./console/common/login";

	$(".splash").hide();

	http.load({
		url : '/sayhi',
		success : function(obj) {
			console.log("HTTP返回", obj);
		}
	})

});