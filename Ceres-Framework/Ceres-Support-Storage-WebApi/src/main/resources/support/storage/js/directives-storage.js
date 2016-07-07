define([ '$', 'app', 'cache', 'http' ], function($, app, cache, http) {

	app.directive('cuiStorage', function($rootScope, $timeout) {
		// 得到文件名
		var getFilename = function(file) {
			return file.replace(/.*(\/|\\)/, "");
		}

		// 得到文件扩展名
		var getExtension = function(filename) {
			var point = filename.lastIndexOf(".");
			return point == -1 ? "" : filename.substr(point);
		}

		return {
			restrict : "A",
			scope : {
				cuiStorage : "&"
			},
			controller : [ "$scope", function($scope) {

			} ],
			link : function(scope, element, attrs, controller) {
				var tagName = element[0].tagName;
				if (scope.cuiStorage() == undefined) {
					console.log("[cui-storage] 未发现存储文件");
					return;
				}

				var obj = scope.cuiStorage();

				// 拼装资源文件的标识key
				var filename = obj.localUri;

				// 检查是否指定filter
				if (attrs.cuiFilter != undefined && attrs.cuiFilter != "") {
					var ext = getExtension(filename);
					var filenameWithoutExt = filename.substring(0, filename.length - ext.length);
					filename = filenameWithoutExt + "@" + attrs.cuiFilter + ext;
				}

				var preKey = "SUPPORT_STORAGE_IMAGE_DATA_";
				if (tagName == "IMG") {
					// 检测本本地缓存
					var key = preKey + filename;
					var obj = cache.get(key);
					if (obj == undefined) {
						// 缓存不存在，到服务器申请
						var url = "/api/storage/query";
						if (filename.substring(0, 1) != "/") {
							url += "/"
						}
						http.load({
							url : url + filename,
							data : {
								toImageData : true
							},
							success : function(ret) {
								// 存入本地,默认存储30天
								cache.set(key, ret, new Date(new Date().getTime() + 30 * 24 * 60 * 60 * 1000));

								element.attr("src", ret);
							}
						})
					} else {
						element.attr("src", obj);
					}

					// 从服务器获取ImageData格式的内容

					// return;
					// var prefixUri = "/api/storage/query";
					// if (filename.substring(0, 1) != "/") {
					// prefixUri += "/"
					// }
					// element.attr("src", prefixUri + filename);
					// element.bind("load", function(e) {
					// var imgData = getImageData(e.target);
					// })
				} else {
					console.log("发现未识别标记");
				}
			}
		}
	})
});