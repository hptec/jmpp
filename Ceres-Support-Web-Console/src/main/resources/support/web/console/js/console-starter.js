define([ 'jquery', 'css!api/classpath/query/support/web/console/theme/homer/styles/style.css', 'bootstrap', 'icheck' ], function($) {

	return {
		start : function() {
			console.log("后台启动器加载");
			$(".splash").hide();
			// var app = angular.module('app', [ 'ui.router' ]);
			//
			// // initialze app module for async loader
			// asyncLoader.configure(app);
			//
			// module.exports = app;
		}
	}
});