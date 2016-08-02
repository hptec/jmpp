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
			scope: {
				model:"=ngModel"
			},
			controller : [ "$scope", "$state", function($scope, $state) {

			} ],
			link : function(scope, element, attrs, controller) {
				element.bind("keydown", function(e) {
					var keyCode = e.keyCode;
					console.log("当前按键："+keyCode);
					if((keyCode >= 37 && keyCode <= 40) || keyCode == 8){//上下左右 删除键
						event.returnValue=true;
						return;
					}
					if(!((keyCode >= 48 && keyCode <= 57) || keyCode == 110 || keyCode == 190)){//(keyCode >= 65 && keyCode <= 90) || 
						event.returnValue=false;
						return;
					}
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
	app.directive('numberLimit', function($rootScope, $timeout) {
		return {
			restrict : "AC",
			scope: {
				precision: "@precision",
				scale: "@scale",
				model: "=ngModel"
			},
			controller : [ "$scope", "$state", function($scope, $state) {
			}],
			link : function(scope, element, attrs, controller) {
				if(!scope.precision){
					scope.precision = 99;
				}
				if(!scope.scale){
					scope.scale = 2;
				}
				element.bind("keydown", function(e) {
					var keyCode = e.keyCode;
					console.log("当前按键："+keyCode+"    : event.target.selectionStart:"+event.target.selectionStart + "  input cur value :"+ $(e.target).val());
					if((keyCode >= 37 && keyCode <= 40) || keyCode == 8){//上下左右 删除键
						event.returnValue=true;
						return;
					}
					//(keyCode >= 65 && keyCode <= 90) 字母  (keyCode == 110 || keyCode == 190) 小数点
					if(!((keyCode >= 48 && keyCode <= 57) || keyCode == 110 || keyCode == 190)){// || 
						event.returnValue=false;
						return;
					}
					if((keyCode == 110 || keyCode == 190) && ((scope.model+"").indexOf(".") != -1 || scope.model == "")){
						event.returnValue=false;
						return;
					}
					var keyVal = event.key;
					if(!/[0-9\\.]/.test(keyVal)){
						event.returnValue=false;
						return;
					}
					var curIdx = event.target.selectionStart;//光标位置
					var legalCursorIdx = curIdx;
					var curVal = scope.model+"";
					var pIdx = curVal.indexOf(".");//实际小数点位置
					if(pIdx == -1){//全是整数
						if(scope.precision <= curVal.length){
							if(keyVal == "."){
								if(scope.scale > 0){
									legalCursorIdx++;
								}else{//设置不能使用小数
									event.returnValue=false;
									return;
								}
							}else{
								event.returnValue=false;
								return;
							}
						}else{
							if(keyVal == "."){
								if(scope.scale > 0){
									legalCursorIdx++;
								}else{//设置不能使用小数
									event.returnValue=false;
									return;
								}
							}else{
								legalCursorIdx++;
							}
						}
					}else{//本身是小数
						//小数前，小数后
						if(pIdx >= curIdx){//在小数点之前
							if(scope.precision <= curVal.substring(0, curVal.indexOf(".")).length){
								event.returnValue=false;
								return;
							}else{
								legalCursorIdx++;
							}
						}else{//小数点之后
							if(scope.scale <= curVal.substring(curVal.indexOf(".")+1).length){
								event.returnValue=false;
								return;
							}else{
								legalCursorIdx++;
							}
						}
					}
					
					var legalVal = curVal.substring(0,curIdx)+keyVal+curVal.substring(curIdx);
					console.log("正确的合法数据："+legalVal);
					scope.model = legalVal;
				});
			}
		}
	});

});