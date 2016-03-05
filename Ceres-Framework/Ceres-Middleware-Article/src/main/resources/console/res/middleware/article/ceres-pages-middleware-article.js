consoleApp.controller('middlewareArticleUpdateCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {

	$c.load({
		url : '/$$ceres_sys/console/middleware/article/get?id=' + ($routeParams.id == undefined ? "" : $routeParams.id),
		success : function(ret) {
			$.extend($scope, ret);

			if ($scope.article == undefined) {
				$scope.article = {};
			}

			if ($scope.article.content != undefined) {
				$scope.editor.html($scope.article.content);
			}

			$scope.$apply();

			$("#publish_time").datetimepicker({
				format : 'yyyy-mm-dd hh:ii:ss',
				language : 'zh-CN',
				autoclose : true
			});
		}
	});

	// 富文本框编辑
	$scope.editor = $c.editor.create('#desc_content', {
		width : "100%",
		height : "600px",
		uploadJson : '/$$ceres_sys/console/base/image/kindeditor/upload'
	});

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
				if ($scope.article == undefined) {
					$scope.article = {};
				}
				$scope.article.cover_img = data[0].file_id;
			}).error(function(data, status, headers, config) {
				console.log('error status: ' + status);
			});
		}
	}

	$scope.confirm = function() {
		$scope.article.content = $scope.editor.html();
		if ($scope.article.publish_time != undefined && $scope.article.publish_time != "") {
			$scope.article.publish_time = new Date($scope.article.publish_time);
		}

		$c.load({
			url : '/$$ceres_sys/console/middleware/article/update',
			data : $scope.article,
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

consoleApp.controller('middlewareArticleSearchCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {
	$scope.remove = function(row) {
		if (confirm("是否确认删除该文章?")) {
			$c.load({
				url : '/$$ceres_sys/console/middleware/article/remove',
				data : {
					id : row.id
				},
				success : function(ret) {
					if (ret.isSuccess) {
						alert("删除成功");
					} else {
						alert(ret.message);
					}
					$("query-form").trigger("search");
				}
			});
		}
	}
} ]);
