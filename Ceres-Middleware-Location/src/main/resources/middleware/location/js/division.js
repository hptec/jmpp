define([ 'http', 'cache' ], function(http, cache) {

	var cacheKey = "MIDDLEWARE_LOCATION_DIVISION_JSONDATA";
	var data = cache.get(cacheKey);
	if (data == undefined) {
		http.load({
			url : '/api/location/divisions',
			success : function(ret) {

				cache.set(cacheKey, ret);
			}
		});
	}

	return {
		/**
		 * 从缓存中读取
		 */
		__get : function() {
			this.__tmpRoot = undefined;// 每次从缓存读取都要清空内存数据
			return cache.get(cacheKey);
		},
		getProvince : function() {
			var root = this.__get();
			if (root == undefined) {
				return [];
			} else {
				return root;
			}
		},
		__tmpRoot : undefined,
		__combineTmp : function() {
			// 组合成为一个便于快速搜索的对象
			var prefix = "c_";
			if (this.__tmpRoot == undefined) {
				var root = this.__get();
				this.__tmpRoot = {};
				for (lv1 in root) {
					var obj = root[lv1];
					this.__tmpRoot[prefix + obj.code] = obj;
					if (obj.children != undefined) {
						for (lv2 in obj.children) {
							var obj2 = obj.children[lv2];
							this.__tmpRoot[prefix + obj2.code] = obj2;
							if (obj2.children != undefined) {
								for (lv3 in obj2.children) {
									var obj3 = obj2.children[lv3];
									this.__tmpRoot[prefix + obj3.code] = obj3;
								}
							}
						}
					}
				}
			}
			return this.__tmpRoot;
		},
		get : function(code) {
			var prefix = "c_";
			return this.__combineTmp()[prefix + code];
		},
		nameOf : function(name) {
			var tmp = this.__combineTmp();
			for (i in tmp) {
				if (tmp[i].name == name) {
					return tmp[i];
				}
			}
			return undefined;
		}
	}

});