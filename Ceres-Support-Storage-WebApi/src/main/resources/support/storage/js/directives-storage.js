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
						element.attr("src", "");

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