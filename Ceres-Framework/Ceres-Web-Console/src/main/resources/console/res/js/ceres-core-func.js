$.extend($c.fn, {
	httpUri : function(uri, param) {
		var httpUri = $c.config.host;

		var isHttpReg = new RegExp("^http[s]?://", "i");// 判断是否以http://开头,
		// 判断是否以http 或者https
		// 协议开始的地址
		var endReg = new RegExp("/$", "i");// 判断是否以"/"结尾
		var startWith = new RegExp("^/", "i");// 判断是否以"/"开始
		var paramsReg = new RegExp("\\?|&|=", "i");// 判断URL中是否带有参数

		// 判断是否有http 或者 https 协议标准地址
		if (isHttpReg.test(uri)) {
			httpUri = uri;
		}

		if (!isHttpReg.test(httpUri)) {
			httpUri = "http://" + httpUri;
		}

		// 判断是否以"/"结尾
		if (!endReg.test(httpUri)) {
			httpUri += "/";
		}

		// 配置资源路径
		if (uri != null && uri != "" && !isHttpReg.test(uri)) {
			if (startWith.test(uri)) {
				uri = uri.substr(1);
			}
			httpUri += uri;
		}

		// 其实参数事件戳
		if (!paramsReg.test(httpUri)) {
			httpUri += "?time=" + new Date().getTime();
		}

		// 添加用户传递的参数
		if (param != null && param != "" && param != "undefined" && param != undefined) {
			for ( var k in param) {
				httpUri += "&" + k + "=" + param[k];
			}
		}

		return httpUri;
	},
	path2Key : function(path) {
		return path.replace(".", "");
	},
	res : function(fileid) {
		var name = "";
		var width = "";
		var height = "";
		var ext = "";

		if (fileid.indexOf("@") == -1) {
			// 没有指定size
			if (fileid.indexOf(".") == -1) {
				// 没有扩展名
				name = fileid;
			} else {
				var extPos = fileid.indexOf(".");
				name = fileid.substring(0, extPos);
				ext = fileid.substring(extPos + 1, fileid.length);
			}
		} else {
			var atPos = fileid.indexOf("@");
			name = fileid.substring(0, atPos);
			str2 = fileid.substring(atPos + 1, fileid.length);
			if (fileid.indexOf(".") == -1) {
				// 没有扩展名
				var xPos = str2.indexOf("x");
				width = str2.substring(0, xPos);
				height = str2.substring(xPos + 1, str2.length);
			} else {
				var dotPos = str2.indexOf(".");
				ext = str2.substring(dotPos + 1, str2.length);
				str3 = str2.substring(0, dotPos);
				var xPos = str3.indexOf("x");
				width = str3.substring(0, xPos);
				height = str3.substring(xPos + 1, str3.length);
			}
		}

		var ret = {
			name : name,
			ext : ext,
			width : width,
			height : height,
			toString : function() {
				var str = this.name;
				if (this.width != "" && this.width != undefined && this.height != undefined && this.height != "") {
					str += "@" + this.width + "x" + this.height
				}
				if (this.ext != undefined && this.ext != "") {
					str += "." + this.ext;
				}
				return str;
			}
		}
		return ret;
	},
	remember : function(rememberText) {
		// 指定了参数则保存，未指定则返回
		if (rememberText != null) {
			var res = rememberText;
			if (typeof rememberText == "object") {
				res = JSON.stringify(rememberText);
			}
			$c.localstorage.set("remember", res);
		} else {
			var rememberStr = $c.localstorage.get("remember");
			try {
				return JSON.parse(rememberStr);
			} catch (e) {
				return rememberStr;
			}
		}
	},
	isHide : function(ele) {
		if (typeof ele == "object") {
			$c.log("object judg");
			return $(ele).height() == 0 || $(ele).css("visibility") == "hidden" || $(ele).css("display") == "none";
		}
		$c.log("not object");
		return false;
	},
	dateFn : {
		config : {
			startHour : 9,
			endHour : 17
		},
		getNext7Day : function() {
			// 返回未来7天日期(包括当天)数组数据
			// ＝＝＝＝[{day:"2015-04-01",weekDays:"星期一"},......]格式json数组
			var days = [];
			var weekDays = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];
			var date = new Date();
			for (i = 0; i < 7; i++) {

				var newDate = {
					day : date.format("yyyy-MM-dd"),
					weekDays : weekDays[date.getDay()]
				}

				// 如果当前时间已经过了营业时间了。则不提供选择
				if (i == 0) {
					if (date.getHours() < $c.fn.dateFn.config.endHour) {
						days.push(newDate);
					}
				} else {
					days.push(newDate);
				}
				date.setDate(date.getDate() + 1)
			}
			return days;
		},
		getOpenHour : function(day) {
			var startHour = $c.fn.dateFn.config.startHour, endHour = $c.fn.dateFn.config.endHour;

			var hour_strs = [], date = new Date();// 当前时间
			var times1 = new Date(date.format("yyyy-MM-dd")).getTime(), tmies2 = new Date(day.format("yyyy-MM-dd")).getTime();

			// 如果传入的日期比当前的日期小。 则返回null
			if (times1 > tmies2) {
				return hour_strs;
			}

			if (times1 == tmies2) {
				startHour = date.getHours();
			}

			for (i = startHour; i < endHour; i++) {
				var h = {
					text : i + "时-" + (i + 1) + "时",
					val : i
				}
				hour_strs.push(h);
			}
			return hour_strs;
		}
	}
})

/**
 * 格式化时间
 */
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
};
// 其他基础函数类扩充
String.prototype.startWith = function(str) {
	var reg = new RegExp("^" + str);
	return reg.test(this);
};

String.prototype.endWith = function(str) {
	var reg = new RegExp(str + "$");
	return reg.test(this);
};
String.prototype.isNullOrEmpty = function(str) {
	if (str == null || str == undefined || str == "") {
		return true;
	} else {
		return false;
	}
};
String.prototype.nullToEmpty = function(str) {
	if (str == null || str == undefined) {
		return "";
	} else {
		return str;
	}
};