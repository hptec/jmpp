consoleApp.controller('positionCtrl', [ '$scope', 'Upload', '$location', '$routeParams', function($scope, Upload, $location, $routeParams) {

	$scope.add = function() {
		var name = prompt("请输入岗位名称");
		if (name != undefined && name != "") {
			$c.load({
				url : 'console/position/add',
				data : {
					name : name
				},
				success : function(ret) {
					if (ret.isSuccess) {
						console.log($scope);
						$scope.$$ceres_target.trigger("search");
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}

	$scope.rename = function(pos) {
		if (pos == undefined) {
			return;
		}
		var name = prompt("请输入岗位名称", pos.name);
		if (name != undefined && name != "") {
			$c.load({
				url : 'console/position/rename',
				data : {
					id : pos.id,
					name : name
				},
				success : function(ret) {
					if (ret.isSuccess) {
						$scope.$$ceres_target.trigger("search");
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}

	$scope.remove = function(pos) {
		if (pos == undefined) {
			return;
		}
		if (confirm("岗位删除后将不可恢复，是否删除?")) {
			$c.load({
				url : 'console/position/remove',
				data : {
					id : pos.id
				},
				success : function(ret) {
					if (ret.isSuccess) {
						$scope.$$ceres_target.trigger("search");
					} else {
						alert(ret.message);
					}
				}
			});
		}
	}

} ]);
