define([ 'app', 'enums' ], function(app, enums) {

	app.directive('cuiEnum', function($compile) {
		return {
			restrict : "E",
			scope : false,
			priority : 1000,
			terminal : true,
			replace : true,
			template : "<select class='form-control'></select>",
			compile : function(tElement, tAttrs, transclude) {
				var category = tAttrs.cuiCategory;
				if (category == undefined && category == "") {
					throw new Error("枚举类指令必须指定枚举名称");
				}
				var uuid = "KEY_" + Math.uuid(32);
				var select = angular.element(tElement).attr("ng-options", "c.key as c.desc for c in " + uuid + "");
				// angular.element(tElement).attr("ng-model", tAttrs.ngModel);
				tAttrs.uuid = uuid;

				// 设置默认描述
				var defaultDesc = tAttrs.cuiDefaultDesc;
				if (defaultDesc != undefined && defaultDesc != "") {
					tElement.append("<option value=''>" + defaultDesc + "</option>");
				}
				return function(scope, element, attrs) {
					$compile(select)(scope);
				}
			},
			controller : [ '$scope', '$attrs', function($scope, $attrs) {
				var uuid = $attrs.uuid;
				$scope[uuid] = enums.get($attrs.cuiCategory).values();
			} ]
		};
	});

	app.filter("enums", function() {
		return function(code, category) {
			if (code == undefined || code == "" || category == undefined || category == "") {
				return "";
			}
			var cate = enums.get(category).values();
			if (cate == undefined) {
				return "";
			}

			for (_$ln in cate) {
				var de = cate[_$ln];
				if (de.key == code) {
					return de.desc;
				}
			}
			return ""

		}
	});

});