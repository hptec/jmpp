define([ 'app', 'enums' ], function(app, enums) {

	app.directive('cuiEnum', function($compile) {
		return {
			restrict : "A",
			scope : false,
			compile : function(tElement, tAttrs, transclude) {
				var category = tAttrs.cuiEnum;
				if (category == undefined && category == "") {
					throw new Error("枚举类指令必须指定枚举名称");
				}

				// 设置默认描述
				var defaultDesc = tAttrs.cuiDesc;
				if (defaultDesc != undefined && defaultDesc != "") {
					tElement.append("<option value=''>" + defaultDesc + "</option>");
				}
				//设置内容
				var values=enums.get(category).values();
				for(_$ln in values){
					var o=values[_$ln];
					tElement.append("<option value='"+o.key+"'>" + o.desc + "</option>");
				}
				return function(scope, element, attrs) {
				}
			}
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