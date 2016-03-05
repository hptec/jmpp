consoleApp.controller('sysSiteCtrl', [ '$scope', 'Upload', '$timeout', function($scope, Upload, $timeout) {

	$c.load({
		url : 'console/sys/site/init',
		success : function(ret) {
			$scope.setting = ret;
			$scope.$apply();
		}
	});

	$scope.doUpdate = function() {
		$c.load({
			url : 'console/sys/site/update',
			data : $scope.setting,
			success : function(ret) {
				if (ret.isSuccess) {
					alert("保存成功!");
				} else {
					alert(ret.message);
				}
			}
		});
	}
	$scope.onLogoSelect = function($file) {
		console.log($file);
		var file = $file;
		$scope.f = file;
		if (file && !file.$error) {
			file.upload = Upload.upload({
				url : '/$$ceres_sys/console/base/res/upload',
				file : file
			});

			file.upload.progress(function(evt) {
				file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
			}).success(function(data, status, headers, config) {
				$scope.setting.SITE_LOGO = data[0].file_id;
			}).error(function(data, status, headers, config) {
				console.log('error status: ' + status);
			})
		}
	}
} ]);

consoleApp.controller('sysConfigCtrl', [ '$scope', 'Upload', '$timeout', function($scope, Upload, $timeout) {

	$c.load({
		url : 'console/sys/config/getall',
		success : function(ret) {
			$scope.configall = ret;
			$scope.$apply();
		}
	});

	$scope.doUpdate = function() {
		$c.load({
			url : 'console/sys/config/update',
			data : {
				key : $scope.key,
				value : $scope.value
			},
			success : function(ret) {
				if (ret.isSuccess) {
					alert("保存成功!");
					$scope.key = undefined;
					$scope.value = undefined;
					$scope.$apply();
				} else {
					alert(ret.message);
				}
			}
		});
	}
	$scope.pickvalue = function($file) {
		$c.load({
			url : 'console/sys/config/get',
			data : {
				key : $scope.key
			},
			success : function(ret) {
				$.extend($scope, ret);
				$scope.$apply();
			}
		});
	}
} ]);