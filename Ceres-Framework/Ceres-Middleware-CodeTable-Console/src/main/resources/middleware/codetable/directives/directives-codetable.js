define([ 'app', 'codetable', 'modal', 'angular' ], function(app, codetable, modal, angular) {

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
				var cate = codetable.getCategory(category);
				if (cate != undefined && cate.codes != undefined) {
					var values = codetable.getCategory(category).codes;
					for (_$ln in values) {
						var o = values[_$ln];
						tElement.append("<option value='" + o.value + "'>" + o.desc + "</option>");
					}
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
			var c = codetable.getCode(category, code);
			c == undefined ? "" : c.desc;
		}
	});
});