define(
		[ 'jquery', 'css!api/classpath/query/support/web/console/theme/homer/styles/style.css', 'bootstrap', 'icheck', 'ceres-classpath/support/web/console/theme/homer/scripts/directives/directives' ],
		function($) {

			$(".splash").hide();
			return {
				start : function() {
					console.log("后台启动器加载");

					// var app = angular.module('app', [ 'ui.router' ]);
					//
					// // initialze app module for async loader
					// asyncLoader.configure(app);
					//
					// module.exports = app;
				}
			}
		});