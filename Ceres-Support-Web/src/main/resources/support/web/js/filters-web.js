define([ 'app' ], function(app) {

	// consoleApp.filter("enums", function() {
	// return function(key, clazzName) {
	// if (clazzName == undefined || clazzName == "") {
	// return "";
	// }
	//
	// var enums = $c.enums[clazzName];
	// if (enums == undefined) {
	// return "";
	// }
	//
	// for ($i in enums) {
	// if (enums[$i].key == key) {
	// return enums[$i].desc;
	// }
	// }
	//
	// return "";
	// }
	// });
	// consoleApp.filter("currencyTenK", function() {
	// return function(key) {
	// var k = parseInt(key);
	// k = k / 10000;
	//
	// return k + " 万";
	// }
	// });
	app.filter("toDate", function() {
		return function(dt) {
			if (dt == undefined) {
				return undefined;
			} else if (dt == "") {
				return undefined;
			} else if (typeof (dt) == "string") {
				return new Date(dt);
			} else {
				return dt;
			}
		}
	});
	// consoleApp.filter("idInList", function() {
	// return function(id, list, prop) {
	// if (id == undefined || id == "") {
	// return "";
	// }
	// if (prop == undefined || prop == "") {
	// return "";
	// }
	// if (!(list instanceof Array)) {
	// return "";
	// }
	//
	// for ($i in list) {
	// var obj = list[$i];
	// if (obj.id == id) {
	// return obj[prop];
	// }
	// }
	//
	// return "";
	// }
	// });
	// consoleApp.filter("percentage", function() {
	// return function(p) {
	// if (p == undefined || isNaN(p)) {
	// return "0%";
	// }
	// p = parseInt(p * 10000);
	// p = p / 100
	// return p + "%";
	// }
	// });
	// consoleApp.directive('resfs', function() {
	// return {
	// link : function(scope, element, attrs) {
	// var e = $(element);
	// var uuid = Math.uuid();
	// e.attr("id", uuid);
	// var func = "$$ceres_resfs_bind('" + uuid + "');";
	// setTimeout(func, 500);
	// }
	// };
	// });
	// consoleApp.directive('formatPercentage', [ '$filter', function($filter) {
	// return {
	// require : 'ngModel',
	// link : function(scope, elm, attrs, ctrl) {
	//
	// function formatter(value) {
	// // return parseInt(value * 10000) / 100;
	// return parseFloat(value * 100);
	// }
	//
	// function parser(s) {
	// var v = parseFloat(s) / 100;
	// return v;
	// }
	//
	// ctrl.$formatters.push(formatter);
	// ctrl.$parsers.unshift(parser);
	//
	// }
	// };
	// } ]);
	// consoleApp.filter('reverse', function() {
	// return function(items) {
	// if (items == undefined || items.length == 0) {
	// return new Array();
	// } else {
	// return items.slice().reverse();
	// }
	//
	// };
	// });
	// consoleApp.directive('formatDate', [ '$filter', function($filter) {
	// var dateFilter = $filter('date');
	// return {
	// require : 'ngModel',
	// link : function(scope, elm, attrs, ctrl) {
	//
	// function formatter(value) {
	// var dt = undefined;
	// if (value == undefined || value == "") {
	// return undefined;
	// } else if (typeof (value) == "string") {
	// dt = new Date(value);
	// } else {
	// dt = value;
	// }
	// if (attrs.formatDate != undefined && attrs.formatDate != "") {
	// return dateFilter(dt, attrs.formatDate);
	// } else {
	// return dt;
	// }
	//
	// }
	//
	// function parser(s) {
	// return new Date(s);
	// }
	//
	// ctrl.$formatters.push(formatter);
	// ctrl.$parsers.unshift(parser);
	//
	// }
	// };
	// } ]);
	// function $$ceres_resfs_bind(id) {
	// var e = $("#" + id);
	// if (e.length == 0) {
	// return;
	// }
	//
	// var func = "$$ceres_resfs_bind('" + id + "');";
	//
	// var fileid = e.attr("resfs");
	// var oldFileid = e.attr("old_resfs");
	// if (fileid == undefined || fileid == "") {
	// // 文件ID还未加载，延时半秒
	// setTimeout(func, 500);
	// return;
	// }
	//
	// if (fileid == oldFileid) {
	// // fileid没有发生变更，延时半秒继续
	// setTimeout(func, 500);
	// return;
	// }
	//
	// // oss_path和fileid都已准备好
	//
	// var fileObj = $c.fn.res(fileid);
	//
	// var w = e.attr("width");
	// var h = e.attr("height");
	// if (w != undefined && w != "" && h != undefined && h != "") {
	// fileObj.width = w;
	// fileObj.height = h;
	// }
	//
	// var base_path = "/$$ceres_sys/console/base/res/fs";
	// e.attr("src", base_path + "/" + fileObj.toString());
	//
	// e.attr("old_resfs", fileid);
	//
	// setTimeout(func, 500);
	// };

});