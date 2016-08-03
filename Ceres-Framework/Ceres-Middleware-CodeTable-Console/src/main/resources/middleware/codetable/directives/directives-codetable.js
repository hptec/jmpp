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
			restrict : "A",
			scope : false,
			compile : function(tElement, tAttrs, transclude) {
				var category = tAttrs.cuiCodetable;
				if (category == undefined && category == "") {
					throw new Error("字典表指令必须指定字典名称");
				}

				// 设置默认描述
				var defaultDesc = tAttrs.cuiDesc;
				if (defaultDesc != undefined && defaultDesc != "") {
					tElement.append("<option value=''>" + defaultDesc + "</option>");
				}
				// 添加枚举值
				var values = findByCategory(category).codes;
				for (_$ln in values) {
					var o = values[_$ln];
					tElement.append("<option value='" + o.value + "'>" + o.desc + "</option>");
				}

				return function(scope, element, attrs) {
					// $compile(select)(scope);
				}
			}
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