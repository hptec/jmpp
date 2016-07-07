define([ 'module', '$' ], function(module, $) {
	var DEFAULT_EXPIRED = 30 * 24 * 60 * 60 * 1000;
	return {
		get : function(key) {
			var __key = "CUI_CACHE_" + key

			if (key == undefined || key == "") {
				return undefined;
			}

			var __value = undefined;
			if (this.isLocalStorageSupport()) {
				__value = localStorage[key];
				if (__value == undefined) {
					return undefined;
				} else {
					// 检查是否过期，默认存储30天
					if (__value.__lastUpdate == undefined || new Date().getTime() - __value.__lastUpdate.getTime() >= DEFAULT_EXPIRED) {
						// 没有记录过期时间，或者超过默认过期时间，则删除此记录
						localStorage.removeItem(__key);
						return undefined;
					} else {
						return __value.__data;
					}
				}
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
			var __key = "CUI_CACHE_" + key
			// 保存
			if (this.isLocalStorageSupport()) {
				// 如果支持localStorage就保存在localStorage

				if (value == undefined) {
					localStorage.removeItem(__key);
				} else {
					// 封装value;
					__value = {
						__data : value,
						__lastUpdate : new Date()
					// 保存出入时间用来检查是否过期
					}

					localStorage[__key] = JSON.stringify(__value);
				}

			} else {
				// 不支持localStorage就保存在Cookie里

				if (value == undefined) {
					$.removeCookie(__key, {
						path : '/'
					});
				}

				$.cookie(__key, JSON.stringify(value), {
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