define([ 'http://api.map.baidu.com/getscript?v=2.0&ak=Lze0SUAqOAebwUuNP0TEIf43' ], function() {

	return function(element) {
		return new BMap.Map(element); // 创建Map实例
	}

});