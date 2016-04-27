define(['module', 'app', '$'], function(module, app, $) {
	var moduleConfig = module.config();

	// 初始化工作
	if (moduleConfig.platform == "app") {

	}

	var retObj = {
		__windows: {},
		open: function(context) {
			if (context == undefined) {
				throw new Error("context is required!");
			}

			if (typeof(context) == "string") {
				context = {
					url: context
				}
			}

			if (moduleConfig.platform == "app") {

				var options = {
					styles: {
						// top: newpage - top - position, //新页面顶部位置
						// bottom: newage - bottom - position, //新页面底部位置
						// width: newpage - width, //新页面宽度，默认为100%
						// height: newpage - height, //新页面高度，默认为100%
						// ......
					},
					extras: {
						// ..... //自定义扩展参数，可以用来处理页面间传值
					},
					createNew: false, // 是否重复创建同样id的webview，默认为false:不重复创建，直接显示
					show: {
						autoShow: true, // 页面loaded事件发生后自动显示，默认为true
						aniShow: 'slide-in-right',
						// //页面显示动画，默认为”slide-in-right“；
						// duration: animationTime
						// //页面动画持续时间，Android平台默认100毫秒，iOS平台默认200毫秒；
					},
					waiting: {
						autoShow: false, // 自动显示等待框，默认为true
						title: '加载中...', // 等待对话框上显示的提示内容
						options: {
							// width: waiting - dialog - widht,
							// //等待框背景区域宽度，默认根据内容自动计算合适宽度
							// height: waiting - dialog - height,
							// //等待框背景区域高度，默认根据内容自动计算合适高度
							// ......
						}
					},
					// online: (online == true) //是否校验登录
				};
				// mui的open
				var mui = require("mui");
				var pg = this.__windows[context.url];
				if (pg != undefined) {
					if (pg.options != undefined) {
						options = $.extend(true, options, pg.options);
					}
				}

				options = $.extend(true, options, context);

				if (pg == undefined) {
					// 没有配置pages设置，直接使用html
					options.url = context.url;
					options.id = context.id == undefined ? context.url : context.id;
				} else {
					options.url = pg.tpl;
					options.id = pg.uri;
				}

				mui.openWindow(options);
			} else {
				$("div[ng-controller='appcmd']").find("a").each(function(i, obj) {
					var o = $(obj);
					if (o.text() == context.url) {
						o.trigger("click");
					}
				})
			}
		},
		pages: function() {
			return moduleConfig.pages;
		}
	}

	for (var i in moduleConfig.pages) {
		var pg = moduleConfig.pages[i];
		retObj.__windows[pg.uri] = pg;
	}

	console.log(retObj);

	return retObj;
});