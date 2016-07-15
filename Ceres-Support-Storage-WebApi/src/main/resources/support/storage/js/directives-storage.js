define([ 'app', 'cache', 'platform', 'module', 'http' ], function(app, cache, platform, module, http) {
	var moduleConfig = module.config();
	// 得到文件扩展名
	var getExtension = function(filename) {
		var point = filename.lastIndexOf(".");
		return point == -1 ? "" : filename.substr(point);
	}


	var download = function(element, fetchUrl) {
		var tagName = element[0].tagName;
		var preKey = "SUPPORT_STORAGE_IMAGE_DATA_";
		if (tagName == "IMG" && fetchUrl != undefined) {
			cache.file(http.toUrl(fetchUrl), function(ret, state) {
				element.attr("src", ret);
			});
		} else {
			console.log("发现未识别标记");
		}
	}

	// 服务器存储对象格式
	app.directive('cuiStorage', function($rootScope, $timeout) {
		return {
			restrict : "A",
			scope : {
				cuiStorage : "="
			},
			controller : [ "$scope", function($scope) {

			} ],
			link : function(scope, element, attrs, controller) {
				scope.$watch(function() {
					return JSON.stringify(scope.cuiStorage);
				}, function(o, n) {
					var tagName = element[0].tagName;

					if (scope.cuiStorage == undefined) {
						console.log("[cui-storage] 未发现存储文件");
						return;
					}
					element.attr("src", "");

					var obj = scope.cuiStorage;

					// 拼装资源文件的标识key
					var fetchUrl = undefined;
					if (typeof (obj) == "object" && obj.localUri != undefined) {
						// 对象方式
						var fetchUrl = obj.localUri;

						// 检查是否指定filter
						if (attrs.cuiFilter != undefined && attrs.cuiFilter != "") {
							var ext = getExtension(fetchUrl);
							var filenameWithoutExt = fetchUrl.substring(0, fetchUrl.length - ext.length);
							fetchUrl = filenameWithoutExt + "@" + attrs.cuiFilter + ext;
						}

						// 内部url
						var url = "/api/storage/query";
						if (fetchUrl.substring(0, 1) != "/") {
							url += "/"
						}
						fetchUrl = url + fetchUrl;

					}

					download(element, fetchUrl);
				});
			}
		}
	});
	// 指定url格式
	app.directive('cuiStorageUrl', function($rootScope, $timeout) {

		return {
			restrict : "A",
			scope : {},
			controller : [ "$scope", function($scope) {

			} ],
			link : function(scope, element, attrs, controller) {

				if (attrs.cuiStorageUrl == undefined) {
					console.log("[cui-storage] 未发现存储文件");
					return;
				}

				var obj = attrs.cuiStorageUrl;

				// 拼装资源文件的标识key
				var fetchUrl = undefined;
				if (typeof (obj) == "string") {
					// 指定路径，忽略cuiFilter
					if (attrs.cuiFilter != undefined && attrs.cuiFilter != "") {
						console.log("[WARN] Cui-Storage, 直接指定路径 cui-filter指定参数无效");
					}
					// 判断是内部url还是外部url
					if (obj.indexOf("://") != -1) {
						// 外部url
						fetchUrl = obj;
					} else {
						// 内部url
						var url = "/api/storage/query";
						if (obj.substring(0, 1) != "/") {
							url += "/"
						}
						fetchUrl = url + obj;
					}
				} else if (typeof (obj) == "object" && obj.localUri != undefined) {
					// 对象方式
					var fetchUrl = obj.localUri;

					// 检查是否指定filter
					if (attrs.cuiFilter != undefined && attrs.cuiFilter != "") {
						var ext = getExtension(fetchUrl);
						var filenameWithoutExt = fetchUrl.substring(0, fetchUrl.length - ext.length);
						fetchUrl = filenameWithoutExt + "@" + attrs.cuiFilter + ext;
					}

					// 内部url
					var url = "/api/storage/query";
					if (fetchUrl.substring(0, 1) != "/") {
						url += "/"
					}
					fetchUrl = url + fetchUrl;

				}

				download(element, fetchUrl);
			}
		}
	})
});