var consoleApp = angular.module('consoleApp', [ 'ngRoute', 'ngFileUpload', 'datatables', 'ngKeditor' ]);
consoleApp.controller('workbenchCtrl', function($scope, $route, $routeParams, $location) {
	$c.load({
		url : 'console/welcome/init',
		success : function(ret) {

			if (ret.employee.create_time != undefined) {
				ret.employee.create_time = new Date(ret.employee.create_time);
			}

			$.extend($scope, ret);

			$scope.$apply();
		}
	});

	$scope.forwardLink = function(menu) {
		if (menu == undefined) {
			return;
		}
		if (menu.link == undefined || menu.link == "") {
			return;
		}
		$location.path(menu.link);
	}
	$scope.modifyPassword = function() {
		$(".content").load('console/employee/modipwd', function() {
			alert("加载完成");
		});

	}
	$scope.logOut = function() {
		$.ajax({
			url : "console/employee/doLogout",
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
					location.href = $c.loginUrl;
				} else {
					alert(dataObj.message);
				}
			}
		});
	}
});