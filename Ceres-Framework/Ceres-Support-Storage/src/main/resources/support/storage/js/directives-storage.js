define([ 'app', 'cache', 'platform', 'module', 'http' ], function(app, cache, platform, module, http) {
	var moduleConfig = module.config();
	// 得到文件扩展名
	var getExtension = function(filename) {
		var point = filename.lastIndexOf(".");
		return point == -1 ? "" : filename.substr(point);
	}


	var download = function(element, fetchUrl, done) {
		var tagName = element[0].tagName;
		var preKey = "SUPPORT_STORAGE_IMAGE_DATA_";
		if (tagName == "IMG" && fetchUrl != undefined) {
			cache.file(http.toUrl(fetchUrl), function(ret, state) {
				element.attr("src", ret);
				done&&done();
			});
		} else {
			console.log("发现未识别标记："+tagName+ "fetchUrl:"+fetchUrl);
		}
	}
	
	app.service('domService', ['$window', '$timeout', function($window, $timeout) {
        'use strict';
        
        var screen = {
				'w' : $window.innerWidth || document.documentElement.clientWidth,
                'h' : $window.innerHeight || document.documentElement.clientHeight,
                'x' : $window.devicePixelRatio || 1
			};
        function topRelative(element){
        	 if (typeof element.offset == 'function') {
                 return element.offset().top - $container.offset().top;
             }
             return element.getBoundingClientRect().top - parent(element).getBoundingClientRect().top;
        }
        function debounce(call, delay) {
            var preventCalls = false;
            var timeout;

            return function() {
              if (!preventCalls) {
                call();

                preventCalls = true;
                if(timeout){
                	$timeout.cancel(timeout);
                }
                timeout = $timeout(function() {
                  preventCalls = false;
                }, delay);
              }
            };
          }
        function painted(element){
        	if(!element){
        		return false;
        	}else{
        		return (element.getBoundingClientRect().top - document.documentElement.clientHeight) <= 50 ;
        	}
        }
        
        function parent(element){
        	return element.parentNode || document;
        }
        window.utils = {
            	screen: screen,
            	parent: parent,
            	painted: painted,
            	debounce: debounce
            };
        return window.utils;
	}]);
	
	
	// 服务器存储对象格式
	/**
	 * 可以传StorageFile 对象样式， 或者传入一个url 字符串
	 *  cuiStorage 如果是空则不解析，当为字符串时，按照url 进行解析 
	 *  cuiFilter 如果为空，则不解析过滤器， 不管是否是url 或者 对象都加入过滤
	 */
	app.directive('cuiStorage', function($rootScope, $timeout, domService, $window) {
		return {
			restrict : "A",
			scope : {
				cuiStorage : "=",
				cuiFilter: "@",
				cuiState: "@"
			},
			controller : [ "$scope", function($scope) {

			} ],
			link : function(scope, element, attrs, controller) {
				function init(){
					if(domService.painted(element[0]) == true && (scope.cuiState == 0 || scope.cuiState == undefined)){
						scope.cuiState = 1;
						var tagName = element[0].tagName;

						if (scope.cuiStorage == undefined) {
							console.log("[cui-storage] 未发现存储文件");
							return;
						}
						element.attr("src", "data:image/gif;base64,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");

						var obj = scope.cuiStorage;

						// 拼装资源文件的标识key
						var fetchUrl = undefined;
						if (typeof (obj) == "object" && obj.localUri != undefined) {
							// 对象方式
							fetchUrl = obj.localUri;
						}else if(typeof (obj) == "string"){
							fetchUrl = obj;
						}
						
						if(fetchUrl != null && fetchUrl != ""){
							// 检查是否指定filter
							if (scope.cuiFilter != undefined && scope.cuiFilter != "") {
								var ext = getExtension(fetchUrl);
								var filenameWithoutExt = fetchUrl.substring(0, fetchUrl.length - ext.length);
								fetchUrl = filenameWithoutExt + "@" + scope.cuiFilter + ext;
							}

							// 内部url
							var url = "/api/storage/query";
							if (fetchUrl.substring(0, 1) != "/") {
								url += "/"
							}
							fetchUrl = url + fetchUrl;
						}
						scope.cuiLoaded = true;
						scope&&scope.$applyAsync();
						
						download(element, fetchUrl, function(){
							scope.cuiState = 2;
							angular.element($window).off('scroll', init);
							scope&&scope.$applyAsync();
						});
					}
				}
				
				scope.$watch(function() {
					return JSON.stringify(scope.cuiStorage);
				}, function(o, n) {
					scope.cuiState = 0;
					angular.element($window).on('scroll', domService.debounce(init, 300));
					init();
				});
				scope.$watch(function(){
					return domService.painted(element[0]);
				}, function(o,n){
					init();
				});
			}
		}
	});
});