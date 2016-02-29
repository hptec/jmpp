define([ 'module', 'jquery' ], function(module, $) {

	return {
		get : function(key) {
			if (key == undefined || key == "") {
				return null;
			}

			var value = undefined;
			if (this.isLocalStorageSupport()) {
				value = localStorage[key];
			} else {
				value = $.cookie("CeresCache_" + key);
			}
			if (value != undefined) {
				return JSON.parse(value);
			} else {
				return null;
			}

		},
		set : function(key, value) {
			// 保存
			if (this.isLocalStorageSupport()) {
				// 如果支持localStorage就保存在localStorage
				localStorage[key] = JSON.stringify(value);
			} else {
				// 不支持localStorage就保存在Cookie里
				$.cookie("CeresCache_" + key, JSON.stringify(value), {
					expires : 30 * 3,
					path : '/'
				});
			}

		},
		isLocalStorageSupport : function() {
			// 检测是否可以缓存
			if (localStorage != null) {
				return true;
			} else {
				return false;
			}
		}
	}
});