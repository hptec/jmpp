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
	// 只能输入数字和小数
	app.directive('cuiNumberOnly', function($rootScope, $timeout) {
		return {
			restrict : "AC",
			controller : [ "$scope", "$state", function($scope, $state) {

			} ],
			link : function(scope, element, attrs, controller) {
				element.bind("keyup", function(e) {
					var value = element.val();
					value = value.replace(/[^\d.]/g, ""); // 清除"数字"和"."以外的字符
					value = value.replace(/^\./g, ""); // 验证第一个字符是数字而不是
					value = value.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
					value = value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
					// value = value.replace(/^(\-)*(\d+)\.(\d\d).*$/,
					// '$1$2.$3'); // 只能输入两个小数
					element.val(value);
				});
			}
		}
	});

	// 只能输入货币
	app.directive('cuiCurrencyOnly', function($rootScope, $timeout) {
		return {
			restrict : "AC",
			controller : [ "$scope", "$state", function($scope, $state) {

			} ],
			link : function(scope, element, attrs, controller) {
				element.bind("keyup", function(e) {
					// TODO 保留光标位置的方法没有做
					var value = element.val();
					value = value.replace(/[^\d.]/g, ""); // 清除"数字"和"."以外的字符
					value = value.replace(/^\./g, ""); // 验证第一个字符是数字而不是
					value = value.replace(/\.{2,}/g, "."); // 只保留第一个. 清除多余的
					value = value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
					value = value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); // 只能输入两个小数
					element.val(value);
					
				});
			}
		}
	});

});