var __bmapKey = bmapKey == undefined ? "Lze0SUAqOAebwUuNP0TEIf43" : bmapKey;
define([ 'division', 'address', 'http://api.map.baidu.com/getscript?v=2.0&ak=' + __bmapKey ], function(division, address) {

	return {
		/**
		 * 等待加载完成以后才返回
		 */
		ready : function(func) {
			if (func == undefined) {
				return;
			}

			if (BMap != undefined) {
				func(BMap)
				return;
			}

			var watchThread = function() {
				if (BMap != undefined) {
					func(BMap)
				} else {
					setTimeout(watchThread, 10)
				}
			}
			setTimeout(watchThread, 10);
		},
		/**
		 * 根据坐标点得到街道地址
		 * 
		 * @param point
		 * @param opt
		 * @returns
		 */
		__geoc : new BMap.Geocoder(),
		getAddress : function(point, opt) {
			this.__geoc.getLocation(point, function(rs) {
				console.log("百度地址查询结果", rs);
				var addComp = rs.addressComponents;
				var retObj = {
					province : {
						name : addComp.province,
						code : division.nameOf(addComp.province).code
					},
					city : {
						name : addComp.city,
						code : division.nameOf(addComp.city).code
					},
					county : {
						name : addComp.district,
						code : division.nameOf(addComp.district).code
					},
					coordinate : {
						standard : "BD09",
						latitude : rs.point.lat,
						longitude : rs.point.lng
					},
					street : addComp.street,
					streetNumber : addComp.streetNumber == undefined ? "" : addComp.streetNumber
				}
				var ret = address.fromObject(retObj);
				if (opt && opt.success) {
					opt.success(ret);
				}
			});
		}
	}

});