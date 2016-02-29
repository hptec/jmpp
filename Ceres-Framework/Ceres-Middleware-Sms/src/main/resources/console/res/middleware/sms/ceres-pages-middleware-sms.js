consoleApp.controller('sysSmsIcpCtrl', [ '$scope', function($scope) {

	$c.load({
		url : 'console/middleware/sms/icp/init',
		success : function(ret) {
			$.extend($scope, ret);

			$scope.$apply();
			
			
			$scope.changeProvider();
		}
	});

	$scope.doUpdate = function() {
		var data = {
			provider_name : $scope.provider_name,
		}

		if ($scope.authkeys != undefined) {
			for (ln in $scope.authkeys) {
				var key = $scope.authkeys[ln];
				data[key.key] = key.value;
			}
		}

		$c.load({
			url : 'console/middleware/sms/icp/update',
			data : data,
			success : function(ret) {
				if (ret.isSuccess) {
					// 保存成功
					alert("保存成功");
				} else {
					alert(ret.message);
				}
			}
		});
	}
	$scope.makeOpen = function(state) {
		$c.load({
			url : 'console/middleware/sms/icp/enable',
			data : {
				enable : state
			},
			success : function(ret) {
				if (ret.isSuccess) {
					if (state == "N") {
						$scope.enable = "true";
					} else {
						$scope.enable = "false";
					}
					$scope.$apply();
				} else {
					alert(ret.message);
				}
			}
		})
	}
	$scope.changeProvider = function() {
		$c.load({
			url : 'console/middleware/sms/icp/changeProvider',
			data : {
				provider_name : $scope.provider_name
			},
			success : function(ret) {
				$.extend($scope, ret);
				$scope.$apply();
			}
		})
	}
} ]);

consoleApp.controller('sysSmsRecordsCtrl', [ '$scope', function($scope) {

} ]);