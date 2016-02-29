$(document).ready(function() {
	var loginApp = angular.module('loginApp', []);

	loginApp.controller('LoginCtrl', function($scope) {
		// 初始化
		$c.load({
			url : '/$$ceres_sys/console/employee/login/init',
			success : function(ret) {
				$.extend($scope,ret);
				$scope.$apply();
			}
		});

		$scope.loginInfo = {
			usr : "",
			pwd : "",
			remember : false
		}
		$scope.doLogin = function() {
			$scope.loginInfo.remember = $("#remember").prop('checked');

			var info = $scope.loginInfo;
			if (info.usr == "" || info.pwd == "") {
				alert("请输入用户名/密码");
				return;
			}

			$.ajax({
				url : "/$$ceres_sys/console/employee/doLogin",
				type : "POST",
				async : true,
				dataType : "text",
				data : $scope.loginInfo,
				error : function(xhr, errorText, srcObject) {
					$c.log("error:" + errorText, srcObject);
					throw errorText;
				},
				timeout : function() {
					alert("网络连接超时!");
				},
				success : function(content, statusText, xhr) {
					var dataObj = JSON.parse(content);
					if (dataObj.isSuccess) {
						location.href = "/$$ceres_sys/console";
					} else {
						alert(dataObj.message);
					}
					$scope.$apply();
				}
			});
		};
	});
});