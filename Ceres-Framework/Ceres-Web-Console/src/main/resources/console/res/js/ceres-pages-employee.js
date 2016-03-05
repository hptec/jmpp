consoleApp.controller('employeeModiProfileCtrl', [ '$scope', 'Upload', '$location', '$routeParams', '$http', function($scope, Upload, $location, $routeParams, $http) {
	$c.load({
		url : 'console/employee/profile/init',
		data : {
			id : $routeParams.id
		},
		success : function(ret) {
			$.extend($scope, ret);
			$scope.$apply();
		}
	});
	$scope.confirmModifyProfile = function() {
		if($scope.info.usr==undefined || $scope.info.usr==""){
			alert("请填写登录名");
			return;
		}
		
		$c.load({
			url : 'console/employee/doModiProfile',
			data : $scope.info,
			success : function(ret) {
				if (ret.isSuccess) {
					$("[ceres-action='return']").trigger('click');
				} else {
					alert(ret.message);
				}
			}
		})
	}

	$scope.modipwd = function() {
		var newpwd = prompt("请输入新的密码");
		if (newpwd != undefined && newpwd != "") {
			$c.load({
				url : 'console/employee/doModiPwd',
				data : {
					id : $scope.info.id,
					pwd : newpwd
				},
				success : function(ret) {
					if (ret.isSuccess) {
						$("[ceres-action='return']").trigger('click');
					} else {
						alert(ret.message);
					}
				}
			})
		}
	}

	$scope.onCoverSelect = function($file) {
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
				if ($scope.info == undefined) {
					$scope.info = {};
				}
				console.log(data);
				$scope.info.avatar_img = data[0].file_id;
			}).error(function(data, status, headers, config) {
				console.log('error status: ' + status);
			});
		}
	}
} ]);
consoleApp.controller('employeeSearchCtrl', function($scope, $route, $routeParams, $location) {
});
