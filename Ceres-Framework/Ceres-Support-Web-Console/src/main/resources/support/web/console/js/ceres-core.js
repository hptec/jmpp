(function(window, undefined) {

	var Ceres = (function() {
		// 构建jQuery对象
		var Ceres = function(selector, context) {
			return new Ceres.fn.init(selector, context);
		};

		// jQuery对象原型
		Ceres.fn = Ceres.prototype = {
			constructor : Ceres,
			init : function(selector, context) {

				// selector有以下7种分支情况：
				// DOM元素
				// body（优化）
				// 字符串：HTML标签、HTML字符串、#id、选择器表达式
				// 函数（作为ready回调函数）
				// 最后返回伪数组
			}
		};

		// Give the init function the jQuery prototype for later instantiation
		Ceres.fn.init.prototype = Ceres.fn;

		// 合并内容到第一个参数中，后续大部分功能都通过该函数扩展
		// 通过jQuery.fn.extend扩展的函数，大部分都会调用通过jQuery.extend扩展的同名函数
		Ceres.extend = Ceres.fn.extend = function() {
		};

		// 在jQuery上扩展静态方法
		Ceres.extend({});

		// 到这里，jQuery对象构造完成，后边的代码都是对jQuery或jQuery对象的扩展
		return Ceres;

	})();

	window.Ceres = window.$C = window.$c = Ceres;

})(window);