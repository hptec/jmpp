define([ 'app', 'codetable', 'modal' ], function(app, codetable, modal) {

	app.directive('cuiCodetable', function() {
		return {
			restrict : 'A',
			controller : [ "$scope", "$uibModal", function($scope, $modal) {
				$scope.openDialog = function() {
					var modalInstance = $modal.open({
						templateUrl : "/api/classpath/query/middleware/codetable/template/codetable.html",
						size : 'lg',
						scope : $scope,
						controller : function($scope, $uibModalInstance) {
							$scope.cancel = function() {
								$uibModalInstance.dismiss('cancel');
							};
						}
					});
				};
				
				
			} ],
			compile : function(element, attrs) {
				var empty = {
					pre : function(scope, element, attrs) {
					},
					post : function(scope, element, attrs) {
					}
				}
				// 获取hashTag
				var ownerType = attrs.cuiCodetable;
				if (ownerType == undefined || ownerType == "") {
					return empty;
				}
				// 从服务器获取
				codetable.listMine(ownerType, {
					success : function(ret) {
						if (ret.isSuccess) {
							element.data("data", ret.object);
						} else {
							modal.toast({
								title : ret.message
							});
						}
					}
				});

				var onDataReady = function(callback) {
					var data = element.data("data")
					if (data == undefined) {
						setTimeout(function() {
							onDataReady(callback);
						}, 10);
					} else {
						callback && callback(data);
					}
				}

				return {
					pre : function(scope, element, attrs) {

					},
					post : function(scope, element, attrs) {
						onDataReady(function(data) {
							// 初始化数据
							scope.__codetable = {
								categorys : data
							}

							// 绑定事件
							element.on("click", function() {
								scope.openDialog();
							});

						});
					}
				}
			},
		};
	});
});