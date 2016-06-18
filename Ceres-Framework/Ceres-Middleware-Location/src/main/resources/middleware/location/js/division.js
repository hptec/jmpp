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
			return cache.get(cacheKey);
		},
		getProvince : function() {
			var root=this.__get();
			if(root==undefined){
				return [];
			}else{
				return root;
			}
		}
	}

});