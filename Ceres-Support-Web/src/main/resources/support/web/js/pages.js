define([ 'module', 'app' ], function(module, app) {
	var moduleConfig = module.config();

	// 初始化工作
	if (moduleConfig.platform == "app") {

	}

	var retObj = {
		__windows : {},
		open : function(context) {
			if (context == undefined) {
				throw new Error("context is required!");
			}
			if (moduleConfig.platform == "app") {
				// mui的open
				var mui = require("mui");

			} else {
				var $ = require("jquery");
				if (typeof (context) == "string") {
					context = {
						url : context
					}
				}

				$("div[ng-controller='appcmd']").find("a").each(function(i, obj) {
					var o = $(obj);
					if (o.text() == context.url) {
						o.trigger("click");
					}
				})
			}
		},
		pages : function() {
			return moduleConfig.pages;
		}
	}

	for ( var i in moduleConfig.pages) {
		var pg = moduleConfig.pages[i];
		retObj.__windows[pg.uri] = pg;
	}

	console.log(retObj);

	return retObj;
});