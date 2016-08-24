define([ 'http', 'modal' ], function(http, modal) {
	return {

		// 参数: transId: 交易ID
		// 参数: product: 产品类别。float，embed，popup
		// 参数: targetId: 绑定的对象元素
		// 参数: valueId: 接受验证参数的hidden inputid
		init : function(option) {
			var handleFunc = function(captchaObj) {
				var targetId = option.targetId;
				$(targetId).click(function() {
					var validate = captchaObj.getValidate();
					if (!validate) {
						alert('请先完成验证！');
						return;
					}
					http.load({
						url : "/api/geetest/verify",
						data : {
							challenge : validate.geetest_challenge,
							validate : validate.geetest_validate,
							seccode : validate.geetest_seccode,
							transId : option.transId
						},
						success : function(ret) {
							if (ret.isSuccess) {
								if (option && option.success) {
									option.success(ret);
								}
							} else {
								modal.toast({
									title : ret.message
								});
							}
						}
					});
				});
				// 弹出式需要绑定触发验证码弹出按钮
				if (option.product == "popup") {
					captchaObj.bindOn(targetId);
					// 将验证码加到id为captcha的元素里
					captchaObj.appendTo(targetId);
				} else {
					captchaObj.appendTo(targetId);
				}
			}

			http.load({
				url : "/api/geetest/init?dt" + (new Date()).getTime(),
				data : {
					transId : option.transId
				},
				server : true,
				context : option,
				success : function(ret) {
					initGeetest({
						gt : ret.gt,
						challenge : ret.challenge,
						product : option.product,
						offline : !ret.success
					}, handleFunc);
				}
			});
			return option;
		}
	};
});
