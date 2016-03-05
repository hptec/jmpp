define([ 'jquery', 'http', 'css!api/classpath/query/support/web/console/theme/homer/styles/style.css', 'bootstrap', 'icheck' ], function($, http) {

	return {
		start : function() {
			console.log("后台启动器加载");
//			location.href = "./dashboard";
			$(".splash").hide();

			http.load({
				url : '/sayhi',
				success : function(obj) {
					console.log("HTTP返回", obj);
				}
			})
		}
	}
});