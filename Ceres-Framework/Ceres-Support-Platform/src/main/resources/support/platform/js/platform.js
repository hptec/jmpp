
define([ 'module', 'cache', 'http', 'modal' ], function(module, cache, http, modal) {
	var platform = {
		__initState : undefined,
		get : get,
		doSync : doSync,
		category : function() {
			return module.config().platform;
		},
		ready : ready
	};
	function ready(callFunc) {
		if (platform.__initState == 'loaded') {
			callFunc && callFunc(platform.get());
			return;
		} else if (platform.__initState == 'failed') {
			console.log("platform 加载失败");
			return;
		} else {// (platform.__initState == 'loading') || platform.__initState
				// == undefined
			setTimeout(function() {
				platform.ready(callFunc);
			}, 200);
			return;
		}
	}
	function get() {
		return cache.get(module.config().platform + "_platform");
	}
	function doSync() {
		var async = false;
		var obj = get();
		if (obj != undefined && obj.lastUpdate != undefined && obj.lastUpdate + (24 * 60 * 60 * 1000) > new Date().getTime()) {
			platform.__initState = 'loaded';
			// 每24小时更新一次
			return;
		}

		if (obj != undefined) {
			// 如果缓存中已经有数据，则使用异步更新
			async = true;
		}
		platform.__initState = 'loading';

		http.load({
			url : '/api/platform/query',
			server : true,
			async : async,
			data : module.config(),
			success : function(content) {
				content.object.lastUpdate = new Date().getTime();
				cache.set(module.config().platform + "_platform", content.object);

				if (content.object == undefined) {
					var errMsg = "未识别或者未授权的Key，请联系管理员!";
					platform.__initState = 'failed';
					modal.toast(errMsg);
					return;
				}

				require.config({
					config : {
						"http" : {
							authcode : content.object.platformAuthCode
						}
					}
				});
				platform.__initState = 'loaded';
			},
			onError : function(ctx, cnt) {
				platform.__initState = 'failed';
			}
		});
	}
	doSync();
	return platform;
});