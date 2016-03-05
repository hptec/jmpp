consoleApp.controller('sysAliyunOssIcpCtrl', [ '$scope', function($scope) {

	$c.load({
		url : 'console/middleware/aliyunoss/icp/init',
		success : function(ret) {
			$.extend($scope, ret);

			$scope.$apply();
		}
	});

	$scope.doUpdate = function() {

		$c.load({
			url : 'console/middleware/aliyunoss/icp/update',
			data : $scope.init,
			success : function(ret) {
				if (ret.isSuccess) {
					// 保存成功
					alert("保存成功");
				} else {
					alert(ret.message);
				}
			}
		});
	};

	$scope.makeOpen = function(state) {
		$c.load({
			url : 'console/middleware/aliyunoss/icp/enable',
			data : {
				enable : state
			},
			success : function(ret) {
				if (ret.isSuccess) {
					if (state == "N") {
						$scope.init.enable = 'true';
					} else {
						$scope.init.enable = 'false';
					}
					$scope.$apply();
				} else {
					alert(ret.message);
				}
			}
		})
	}
} ]);

consoleApp.controller('sysAliyunOssRecordsCtrl', [ '$scope', function($scope) {

} ]);