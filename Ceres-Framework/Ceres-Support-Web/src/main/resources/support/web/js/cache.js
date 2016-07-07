define([ 'module', '$' ], function(module, $) {
	return {
		get : function(key) {
			var __key = "CUI_CACHE_" + key

			if (key == undefined || key == "") {
				return undefined;
			}

			var __value = undefined;
			if (this.isLocalStorageSupport()) {
				__value = localStorage[__key];
				if (__value == undefined) {
					return undefined;
				} else {
					// 检查是否过期，默认存储30天
					if (__value.__expired != undefined && new Date().getTime() > __value.__expired.getTime()) {
						// 没有记录过期时间，或者超过默认过期时间，则删除此记录
						localStorage.removeItem(__key);
						return undefined;
					} else {
						return JSON.parse(__value).__data;
					}
				}
			} else {
				value = $.cookie(__key);
			}

			if (value != undefined) {
				return JSON.parse(value);
			} else {
				return null;
			}

		},
		/**
		 * 存储本地缓存到localstorage<br>
		 * key:存储的键值 <br>
		 * value:存储的内容 <br>
		 * expired: 非必须，Date类型，设定过期的时间。如果内容已经被缓存，但get的时候超过设定时间，则该条内容会被清空<br>
		 * 如果不设定expired，则表示永久不过期
		 */
		set : function(key, value, expired) {
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
						__expired : expired
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