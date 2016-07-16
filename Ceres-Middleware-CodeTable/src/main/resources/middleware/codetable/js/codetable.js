define([ 'http' ], function(http) {

	return {
		/**
		 * 获得当前可用的字典表分类列表
		 */
		listMine : function(ownerType, opt) {
			opt.url = "/api/codetable/category/list";
			if (opt.data == undefined) {
				opt.data = {};
			}
			opt.data.ownerType = ownerType;

			http.load(opt);
		}
	}
});