var __bmapKey = bmapKey == undefined ? "Lze0SUAqOAebwUuNP0TEIf43" : bmapKey;
define([ 'http://api.map.baidu.com/getscript?v=2.0&ak=' + __bmapKey ], function() {

	return function(element) {
		return new BMap.Map(element); // 创建Map实例
	}

});