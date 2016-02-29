consoleApp.controller('balanceWithdrawSearchCtrl', function($scope, $route, $routeParams, $location) {
	$scope.approve = function(row) {
		if (confirm("是否确认通过该提现请求?")) {
			$c.load({
				url : 'console/balance/withdraw/approve',
				data : {
					id : row.id
				},
				success : function(ret) {
					if (ret.isSuccess) {
						$("query-form").trigger("search");
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}
	$scope.deny = function(row) {
		if (confirm("是否拒绝该提现请求?")) {
			$c.load({
				url : 'console/balance/withdraw/deny',
				data : {
					id : row.id
				},
				success : function(ret) {
					if (ret.isSuccess) {
						$("query-form").trigger("search");
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}
});
consoleApp.controller('balanceConfigCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {
	$c.load({
		url : 'console/balance/config/get',
		success : function(ret) {
			$.extend($scope, ret);
			console.log(ret);
			$scope.$apply();

		}
	});

	$scope.confirmConfig = function() {
		$c.load({
			url : 'console/balance/config/update',
			data : $scope.values,
			success : function(ret) {
				if (ret.isSuccess) {
					alert("保存成功");
				} else {
					alert(ret.message);
				}
			}
		});
	}
} ]);

consoleApp.controller('balanceWithdrawViewCtrl', function($scope, $route, $routeParams, $location) {
	$c.load({
		url : 'console/balance/withdraw/get',
		data : {
			id : $routeParams.id
		},
		success : function(ret) {
			$.extend($scope, ret);
			$scope.$apply();
		}
	});

});