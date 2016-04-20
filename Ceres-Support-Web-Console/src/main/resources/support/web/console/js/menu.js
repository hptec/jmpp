define([ 'http', 'cache', 'platform' ], function(http, cache, platform) {

	var obj = {
		/**
		 * 获取本人的菜单
		 */
		getMine : function(func) {
			var key = "EMPLOYEE_MINE_MENUS_" + platform.category();
			if (func != undefined) {
				http.load({
					url : '/api/menu/mine',
					success : function(result) {
						cache.set(key, result)
						if (func) {
							func(result);
						}
					}
				});
			}

			var ret = cache.get(key);
			return ret;
		}
	}

	return obj;
});