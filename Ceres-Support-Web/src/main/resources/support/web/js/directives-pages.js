define([ 'app', 'platform', 'pages' ], function(app, platform, pages) {

	// 用于对页面跳转的支持
	app.directive('cuiPages', function($rootScope, $timeout) {
		return {
			restrict : "E",
			controller : [ "$scope", "$state", function($scope, $state) {
				$scope.go = function(url) {
					$state.go(url);
				}
			} ],
			link : function(scope, element, attrs, controller) {
				if (platform.category() != "app") {
					// 非APP方式使用此种
					var pgs = pages.pages();
					for (i in pgs) {
						var pg = pgs[i];
						element.append("<a style='display:none;' >" + pg.name + "</a>");
						element.bind("click", function(e) {
							scope.go(e.target.text);
						});
					}
				}
			},

		}
	});

});