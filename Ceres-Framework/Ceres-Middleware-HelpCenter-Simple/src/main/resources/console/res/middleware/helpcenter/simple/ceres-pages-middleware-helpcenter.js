consoleApp.controller('helpcenterCategorySearchCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {

} ]);
consoleApp.controller('helpcenterCategoryUpdateCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {
	$c.load({
		url : 'console/helpcenter/category/get',
		data : {
			id : $routeParams.id
		},
		success : function(ret) {
			$.extend($scope, ret);
			$scope.$apply();
		}
	});

	console.log(1);

	$scope.confirm = function() {
		$c.load({
			url : 'console/helpcenter/category/confirm',
			data : $scope.category,
			success : function(ret) {
				if (ret.isSuccess) {
					alert("保存成功!");
				} else {
					alert(ret.message);
				}
			}
		});
	}

} ]);
consoleApp.controller('helpcenterCategorySearchCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {

	$scope.remove = function(row) {
		if (confirm("是否确认删除该分类?")) {
			$c.load({
				url : 'console/helpcenter/category/remove',
				data : {
					id : row.id
				},
				success : function(ret) {
					if (ret.isSuccess) {
						alert("删除成功!");
						$("query-form").trigger("search")
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}
} ]);
consoleApp.controller('helpcenterQuestionUpdateCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {
	$c.load({
		url : 'console/helpcenter/question/get',
		data : {
			id : $routeParams.id
		},
		success : function(ret) {
			if (ret.categorys != undefined) {
				for (ln in ret.categorys) {
					var cate = ret.categorys[ln];
					if (cate.parent_id != undefined && cate.parent_id != -1) {
						cate.name = " - " + cate.name;
					}
				}
			}

			$.extend($scope, ret);
			$scope.$apply();
		}
	});

	$scope.confirm = function() {
		$c.load({
			url : 'console/helpcenter/question/confirm',
			data : $scope.question,
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
consoleApp.controller('helpcenterQuestionSearchCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {

	$scope.remove = function(row) {
		if (confirm("是否确认删除该问题?")) {
			$c.load({
				url : 'console/helpcenter/question/remove',
				data : {
					id : row.id
				},
				success : function(ret) {
					if (ret.isSuccess) {
						alert("删除成功!");
						$("query-form").trigger("search")
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}
} ]);