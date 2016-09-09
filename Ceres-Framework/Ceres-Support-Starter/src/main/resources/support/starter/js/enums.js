define([ 'http', 'cache' ], function(http, cache) {
	// 加载初始数据
	http.load({
		url : "/api/web/enums",
		server : true,
		success : function(ret) {
			var storeKey = "ENUM_DATASET";
			cache.set(storeKey, ret);
		}
	});

	return {
		/**
		 * 从缓存中获取枚举集合
		 */
		__getDataSet : function() {
			var storeKey = "ENUM_DATASET";
			return cache.get(storeKey);
		},
		get : function(name) {
			var set = this.__getDataSet();
			set = set == undefined ? [] : set[name];
			return {
				values : function() {
					return set;
				},
				keyOf : function(key) {
					for (i in set) {
						var obj = set[i];
						if (obj != undefined && obj.key == key) {
							return obj;
						}
					}
					return undefined;
				}
			}
		}
	};
});