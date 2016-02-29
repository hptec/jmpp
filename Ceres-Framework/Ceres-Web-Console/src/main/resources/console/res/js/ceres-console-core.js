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
			},
			val : function(obj, str) {
				if (obj == undefined) {
					return false;
				}
				var target = obj;
				if (str == undefined) {
					str = "";
				}
				$.each(str.split("."), function(i, v) {
					if (target != null) {
						target = target[v];
					}
				});
				return target;
			},
			cache : function(key, value) {
				if (key == undefined) {
					return null;
				}

				if (value != undefined) {
					// 保存
					if (this.supports.localStorage()) {
						// 如果支持localStorage就保存在localStorage
						localStorage[key] = JSON.stringify(value);
					} else {
						// 不支持localStorage就保存在Cookie里
						$.cookie("CeresAttr_" + key, JSON.stringify(value), {
							expires : 30 * 3,
							path : '/'
						});
					}
				} else {
					var value = undefined;
					if (this.supports.localStorage()) {
						value = localStorage[key];
					} else {
						value = $.cookie("CeresAttr_" + key);
					}
					if (value != undefined) {
						return JSON.parse(value);
					} else {
						return null;
					}
				}
			},
			supports : {
				localStorage : function() {
					// 检测是否可以缓存
					if (localStorage != null) {
						return true;
					} else {
						return false;
					}
				}
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
		// Object.prototype.valueExist = function(str) {
		// console.log(this);
		// }

		// 到这里，jQuery对象构造完成，后边的代码都是对jQuery或jQuery对象的扩展
		return Ceres;

	})();

	window.Ceres = window.$C = window.$c = Ceres;

})(window);
$.extend($c, {
	isLogEnabled : true,// 是否开启调试日期
	log : function(logdata, invokerObj) {
		if (this.isLogEnabled) {
			var time = new Date().toISOString() + "";
			if (invokerObj != null) {
				console.log("[" + time + "] " + logdata, invokerObj);
			} else {
				console.log("[" + time + "] " + logdata);
			}
		}
		return this;
	},
	error : function(logdata, invokerObj) {
		$c.log(logdata, invokerObj);
		throw logdata;
	},
	load : function(param) {
		// 系统请求,用户加载系统相关信息(非用户行为请求，不用校验会话信息)
		var url = param.url;
		var data = param.data == null ? {} : param.data;
		if (url == null) {
			return;
		} else {
		}

		// 校验数据类型
		if (param.dataType == undefined) {
			param.dataType = "text";
		}

		// 提交remember信息
		var r = $c.fn.cache("_currentUser");
		if (r != undefined && r.remember_token != undefined) {
			// 附加登录信息
			$.extend(data, {
				_remember_token : r.remember_token,
				_uid : r.id
			});
		}

		$.ajax({
			url : param.url,
			type : "POST",
			async : true,
			dataType : "text",
			data : data,
			context : param.context == undefined ? param : param.context,
			error : function(xhr, errorText, srcObject) {
				$c.log("error:" + errorText, srcObject);
				alert("错误:" + srcObject);
				throw errorText;
			},
			timeout : function() {
				alert(timeout);
			},
			success : function(content, statusText, xhr) {
				if (xhr.status == "404" || statusText == "error") {
					alert("当前页面未找到");
					return;
				} else if (content == "login") {
					// 如果服务器要求客户端重登录，则要清除本地的remember字符串，因为字符串已经过期，便于显示登录页面
					$c.fn.cache("_currentUser", {});
					location = $c.loginUrl;
				} else if (content.startWith("error:")) {
					alert(content);
					return;
				} else {
					var dataObj = JSON.parse(content);
					// 回调成功消息
					$c.log("请求页面 [" + param.url + "] 返回结果:", dataObj);

					if (param.success != null) {
						param.success(dataObj, this);
					}
				}
			},
			complete : function(xhr, textStatus) {
				if (param.complete != null) {
					param.complete(xhr, textStatus);
				}
				/*
				 * 暂时取消进度条 hide_loading_bar();
				 */
			}

		});
	},
	pages : {},
	enums : {
		desc : function(name, key) {
			var enums = $c.enums[name];
			if (enums == undefined) {
				return "";
			}

			for ($i in enums) {
				if (enums[$i].key == key) {
					return enums[$i].desc;
				}
			}
			return "";
		}
	},
	dialogs : {},
	queryForms : {}
});
