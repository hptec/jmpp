// 加载各种配置和启动前文件
define([ 'platform' ], function(platform) {
	console.log("平台信息", platform.get());

	console.log("后台启动器加载");

	$(".splash").hide();


});
