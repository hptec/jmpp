define([ 'module', 'cache', 'jquery' ], function(module, cache, $) {
	function get() {
		return cache.get(module.config().appid + "_platform");
	}

	function doSync() {
		var async = false;
		var obj = get();

		if (obj != undefined) {
			// 如果缓存中已经有数据，则使用异步更新
			async = true;
		}

		$.ajax({
			url : '/api/platform/query',
			async : async,
			data : module.config(),
			success : function(content) {
				cache.set(module.config().appid + "_platform", content.object);

				if (content.object == undefined) {
					var errMsg = "未识别或者未授权的Key，请联系管理员!";
					throw new Error("未识别或者未授权的Key，请联系管理员!");
					alert(errMsg);
					return;
				}

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
		get : get,
		doSync : doSync,
		category : function() {
			return module.config().platform;
		}
	}
});