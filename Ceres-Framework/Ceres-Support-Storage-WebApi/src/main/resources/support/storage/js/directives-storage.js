define([ '$', 'app' ], function($, app) {

	app.directive('cuiStorage', function($rootScope, $timeout) {
		return {
			restrict : "A", // 指令是一个元素 (并非属性)
			scope : { // 设置指令对于的scope
				name : "@", // name 值传递 （字符串，单向绑定）
				amount : "=", // amount 引用传递（双向绑定）
				save : "&" // 保存操作
			},
			transclude : true, // 不复制原始HTML内容
			controller : [ "$scope", function($scope) {

			} ],
			link : function(scope, element, attrs, controller) {
				var tagName = element[0].tagName;
				var obj = JSON.parse(attrs.cuiStorage);
				if (tagName == "IMG") {
					element.attr("src", "/api/storage/query" + obj.localUri);
				} else {
					console.log("发现未识别标记");
				}
			}
		}
	})
});