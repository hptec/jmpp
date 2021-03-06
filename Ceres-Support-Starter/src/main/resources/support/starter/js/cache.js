define([ 'module', 'jquery' , 'md5'], function(module, $, md5) {
	return {
		get : function(key) {
			var __key = "CUI_CACHE_" + key

			if (key == undefined || key == "") {
				return undefined;
			}

			var __value = undefined;
			if (this.isLocalStorageSupport()) {
				__value = localStorage.getItem(__key);//[__key];
				if (__value == undefined) {
					return undefined;
				} else {
					if(typeof __value != 'object'){
						try{
							__value = JSON.parse(__value);
						}catch(e){}
					}
					// 检查是否过期，默认存储30天
					if (__value.__expired != undefined && new Date().getTime() > __value.__expired) {
						// 没有记录过期时间，或者超过默认过期时间，则删除此记录
						localStorage.removeItem(__key);
						return undefined;
					} else {
						return __value.__data;//JSON.parse(__value).__data;
					}
				}
			} else {
				__value = $.cookie(__key);
				try{
					__value = JSON.parse(value);
				}catch(e){}
			}

//			if (value != undefined) {
//				return JSON.parse(value);
//			} else {
//				return null;
//			}
			return __value;

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
					var __value = {
						__data : value,
						__expired : expired&&expired.getTime?expired.getTime():null
					// 保存出入时间用来检查是否过期
					}

					localStorage.setItem(__key, JSON.stringify(__value));//[__key] = JSON.stringify(__value);
				}

			} else {
				// 不支持localStorage就保存在Cookie里

				if (value == undefined) {
					$.removeCookie(__key, {
						path : '/'
					});
				}
				var v = JSON.stringify(value);
				if(/^"/.test(v)){
					v = v.substring(1);
				}
				if(/"$/.test(v)){
					v = v.substring(0, v.length -1);
				}

				$.cookie(__key, v, {
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
		},
		/**
		 * @Param url： 带HTTP头的全路径URL
		 * @Param back: back(本地路径/字符串数据, state)
		 */
		file: function(url, back){
			if(!url){
				back&&back(url, -1);
				return;
			}
			var key = md5(url);
			
			var FDBS = this.get("CERES_FILE_DB");
			if(!FDBS){
				FDBS = {};
				FDBS[key] = new Date().getTime();
			}
			if(!FDBS[key]){
				FDBS[key] = new Date().getTime();
			}
			var expired = FDBS[key]?(FDBS[key]+30*24*3600*1000 < new Date().getTime()):false;
			
			this.set("CERES_FILE_DB",  FDBS);
			
			var platform = require('platform');
			if(platform && platform.get().category == "app"){
				var Files =  require(['Files'],function(Files){
					Files&&Files.lining(url, back, expired);
				}, function(){
					back&&back(url, -1);
				});
			}else{
				back&&back(url, 0);
			}
		}
	}
});