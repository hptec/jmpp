define([ 'app', 'codetable-data', 'modal', 'angular' ], function(app, codetableData, modal, angular) {

	var findByCategory = function(cateName) {
		for ($ln in codetableData) {
			var data = codetableData[$ln];
			if (data.category == cateName) {
				return data;
			}
		}
	}

	var findByCode = function(category, code) {
		if (category == undefined || category.codes == undefined) {
			return "";
		}

		for (_$ln in category.codes) {
			var codeRec = category.codes[_$ln];
			if (codeRec.value == code) {
				return codeRec.desc;
			}
		}
		return "";

	}

	app.directive('cuiCodetable', function($compile) {
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
					throw new Error("字典表指令必须指定字典名称");
				}
				var uuid = "KEY_" + Math.uuid(32);
				var select = angular.element(tElement).attr("ng-options", "c.value as c.desc for c in " + uuid);
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
				for ($ln in codetableData) {
					var data = codetableData[$ln];
					if (data.category == $attrs.cuiCategory) {
						$scope[uuid] = data.codes;
					}
				}
			} ]
		};
	});

	app.filter("codetable", function() {
		return function(code, category) {
			if (code == undefined || code == "" || category == undefined || category == "") {
				return "";
			}
			var cate = findByCategory(category);
			if (cate == undefined) {
				return "";
			}
			return findByCode(cate, code);
		}
	});
});