define([ '$', 'app', 'cache', 'platform', 'module', 'http'], function($, app, cache, platform, module, http) {
	var moduleConfig = module.config();
	// 得到文件名
//	var getFilename = function(file) {
//		return file.replace(/.*(\/|\\)/, "");
//	}
//
	// 得到文件扩展名
	var getExtension = function(filename) {
		var point = filename.lastIndexOf(".");
		return point == -1 ? "" : filename.substr(point);
	}

//	var loadImageData = function(url, callback) {
////		var tagName = element[0].tagName;
////		var preKey = "SUPPORT_STORAGE_IMAGE_DATA_";
////		// 检测本本地缓存
////		var key = preKey + fetchUrl;
////		var obj = cache.get(key);
////		if (obj == undefined) {
////			loadImageData(fetchUrl, function(ret) {
////
////				// 存入本地,默认存储30天
////				if (platform.category() == "app") {
////					// 只有APP才进行图片缓存
////					cache.set(key, ret, new Date(new Date().getTime() + 30 * 24 * 60 * 60 * 1000));
////				}
////
////				element.attr("src", ret);
////			});
////		} else {
////			element.attr("src", obj);
////		}
//		if(platform.category() == "app"){
//			var Files =  require('Files');
//			Files&&Files.get("http://192.168.0.106:8080"+url, callback);
//		}else{
//			callBack&&callBack(url);
//		}
//		
////		var img = new Image();
////		img.src = url;
////		img.addEventListener("load", function(e) {
////			// 创建canvas DOM元素，并设置其宽高和图片一样
////			var canvas = document.createElement("canvas");
////			canvas.width = img.width;
////			canvas.height = img.height;
////			// 坐标(0,0) 表示从此处开始绘制，相当于偏移。
////			var context = canvas.getContext("2d");
////			context.drawImage(img, 0, 0);
////
////			var imgdata = canvas.toDataURL();
////			callback && callback(imgdata);
////		});
//	}

	var download = function(element, fetchUrl) {
		var tagName = element[0].tagName;
		var preKey = "SUPPORT_STORAGE_IMAGE_DATA_";
		if (tagName == "IMG" && fetchUrl != undefined) {
			cache.file(http.toUrl(fetchUrl), function(ret, state){
				element.attr("src", ret);
			});
//			// 检测本本地缓存
//			var key = preKey + fetchUrl;
//			var obj = cache.get(key);
//			if (obj == undefined) {
//				loadImageData(fetchUrl, function(ret) {
//
//					// 存入本地,默认存储30天
//					if (platform.category() == "app") {
//						// 只有APP才进行图片缓存
//						cache.set(key, ret, new Date(new Date().getTime() + 30 * 24 * 60 * 60 * 1000));
//					}
//
//					element.attr("src", ret);
//				});
//			} else {
//				element.attr("src", obj);
//			}
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
				scope.$watch(function(){
					return JSON.stringify(scope.cuiStorage);
				}, function(o, n){
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