define([ '$', 'app' ], function($, app) {

	app.directive('cuiStorage', function($rootScope, $timeout) {
		// 把image 转换为 canvas对象
		function getImageData(image) {
			// 创建canvas DOM元素，并设置其宽高和图片一样
			var canvas = document.createElement("canvas");
			canvas.width = image.width;
			canvas.height = image.height;
			// 坐标(0,0) 表示从此处开始绘制，相当于偏移。
			canvas.getContext("2d").drawImage(image, 0, 0);
			return canvas;
		}

		return {
			restrict : "A",
			controller : [ "$scope", function($scope) {

			} ],
			link : function(scope, element, attrs, controller) {
				var tagName = element[0].tagName;
				var obj = JSON.parse(attrs.cuiStorage);
				if (tagName == "IMG") {
					element.attr("src", "/api/storage/query" + obj.localUri);
					element.bind("load", function(e) {
						var imgData = getImageData(e.target);
					})
				} else {
					console.log("发现未识别标记");
				}
			}
		}
	})
});