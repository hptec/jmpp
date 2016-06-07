// 加载各种配置和启动前文件
define([ 'platform' ], function(platform) {
	console.log("平台信息", platform.get());

	require([ 'http', 'app' ], function(http, app) {

		var afterStart = function() {

			console.log("后台启动器加载");
			// location.href = "./console/common/login";

			$(".splash").hide();
		}
		app.controller("appcmd", [ "$scope", "$location", "$state", function($scope, $location, $state) {
			var pages = require('pages');

			$scope.pages = pages.pages();
			$scope.jump = function(url) {
				$state.go(url);
			}
		} ]);
		app.start(afterStart);

	});

});
