consoleApp.controller('middlewareCarouselAdvertisingUpdateCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {

	$c.load({
		url : 'console/carouseladvertising/get?namespace=' + ($routeParams.id == undefined ? "" : $routeParams.id),
		success : function(ret) {
			$.extend($scope, ret);

			$scope.$apply();

		}
	});

	$scope.onPicSelect = function($file, namespace) {
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
				// 图片上传成功以后，交给 product/pic/add登记
				$c.load({
					url : 'console/carouseladvertising/pic/add',
					data : {
						namespace : namespace,
						uri : data[0].file_id
					},
					success : function(ret) {
						if (ret.isSuccess) {
							// 添加到展示图片数组
							if ($scope.data == undefined) {
								$scope.data = new Array();
							}
							$scope.data.push(ret.object);
							$scope.$apply();
						} else {
							alert(ret.message);
						}
					}
				});
			}).error(function(data, status, headers, config) {
				console.log('error status: ' + status);
			})
		}
	}
	$scope.movePic = function(namespace, picid, direction) {
		$c.load({
			url : 'console/carouseladvertising/pic/move',
			data : {
				namespace : namespace,
				picid : picid,
				direction : direction
			},
			success : function(ret) {
				if (ret.isSuccess) {
					// 添加到展示图片数组
					$.extend($scope, ret.object);
					$scope.$apply();
				} else {
					alert(ret.message);
				}
			}
		})
	}

	$scope.removePic = function(namespace, picId) {
		if (confirm("是否删除此张展示图?")) {
			$c.load({
				url : 'console/carouseladvertising/pic/remove',
				data : {
					namespace : namespace,
					picid : picId
				},
				success : function(ret) {
					if (ret.isSuccess) {
						for (ln in $scope.data) {
							if ($scope.data[ln].id == picId) {
								$scope.data.splice(ln, 1);
							}
						}
						$scope.$apply();
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}
	$scope.configPic = function(namespace, pic) {
		$scope.currentPic = pic;
		ngDialog.open({
			template : 'console/base/res/cp?id=console/res/middleware/carouseladvertising/config.html',
			className : 'ngdialog-theme-plain',
			scope : $scope,
		});
	}

} ]);
consoleApp.controller('middlewareCarouselAdvertisingConfigCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {

	$c.load({
		url : 'console/carouseladvertising/getpic',
		data : {
			id : $routeParams.id
		},
		success : function(ret) {
			$.extend($scope, ret);
			$scope.$apply();
		}
	});

	$scope.confirmConfig = function() {
		$c.load({
			url : 'console/carouseladvertising/pic/confirmconfig',
			data : $scope.currentPic,
			success : function(ret) {
				if (ret.isSuccess) {
					$("[ceres-action='return']").trigger("click");
				} else {
					alert(ret.message);
				}
			}
		});
	}

} ]);
