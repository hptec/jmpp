define([ 'app', 'http' ], function(app, http) {

	app.directive('cuiRemote', function($compile) {
		return {
			restrict : "E",
			scope : false,
			priority : 1000,
			terminal : true,
			replace : true,
			template : "<select class='form-control'></select>",
			compile : function(tElement, tAttrs, transclude) {
				var url = tAttrs.cuiUrl;
				if (url == undefined && url == "") {
					throw new Error("RemoteList必须指定Url");
				}
				var uuid = "KEY_" + Math.uuid(32);
				var select = angular.element(tElement).attr("ng-options", "c.id as c.desc for c in " + uuid + "");
				// angular.element(tElement).attr("ng-model", tAttrs.ngModel);
				tAttrs.uuid = uuid;

				// 设置默认描述
				var defaultDesc = tAttrs.cuiDesc;
				if (defaultDesc != undefined && defaultDesc != "") {
					tElement.append("<option value=''>" + defaultDesc + "</option>");
				}
				return function(scope, element, attrs) {
					$compile(select)(scope);
				}
			},
			controller : [ '$scope', '$attrs', function($scope, $attrs) {
				var uuid = $attrs.uuid;
				// 加载远程数据
				http.load({
					url : $attrs.cuiUrl,
					success : function(ret) {
						$scope[uuid] = ret;
						$scope.$apply();
					}
				});

			} ]
		};
	});

});