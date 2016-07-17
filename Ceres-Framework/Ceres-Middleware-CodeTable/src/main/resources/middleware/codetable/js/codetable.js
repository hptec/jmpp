define([ 'http' ], function(http) {
	return {
		/**
		 * 获得当前可用的字典表分类列表
		 */
		listMine : function(opt) {
			opt.url = "/api/codetable/category/list";
			http.load(opt);
		},
		updateCode : function(code, opt) {
			opt.url = "/api/codetable/code/update";
			if (code != undefined) {
				opt.data = {
					jsonStr : JSON.stringify(code)
				}
				http.load(opt);
			}
		}
	}
});