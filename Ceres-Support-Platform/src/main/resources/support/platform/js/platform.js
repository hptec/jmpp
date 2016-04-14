define([ 'module', 'cache', 'jquery' ], function(module, cache, $) {
	function doSync() {
		$.ajax({
			url : '/api/platform/query',
			async : false,
			data : module.config(),
			success : function(content) {
				cache.set(module.config().appid + "_platform", content.object);

				require.config({
					config : {
						"http" : {
							authcode : content.object.platformAuthCode
						}
					}
				})
			}
		});
	}
	doSync();

	return {
		get : function() {
			return cache.get(module.config().appid + "_platform");
		},
		doSync : doSync
	}
});