define([ 'app', 'codetable', 'modal', 'angular' ], function(app, codetable, modal, angular) {

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
							$scope.__codetable.cancel = function() {
								$uibModalInstance.dismiss('cancel');
							};
							$scope.__codetable.editCodeTable = function(cate) {
								$scope.__codetable.category = cate;
							}
							$scope.__codetable.backToList = function() {
								$scope.__codetable.category = undefined;
							}
							$scope.__codetable.editCode = function(code) {
								$scope.__codetable.code = {};
								angular.extend($scope.__codetable.code, code);
							}
							$scope.__codetable.saveCode = function() {
								if ($scope.__codetable.code != undefined) {
									var data = {};
									angular.extend(data, $scope.__codetable.code);

									if (data.value == undefined || data.value == "") {
										modal.toast({
											title : "字典选项必须输入代码"
										});
										return;
									}
									if (data.desc == undefined || data.desc == "") {
										modal.toast({
											title : "字典选项必须输入名称"
										});
										return;
									}
									data.category = {};
									angular.extend(data.category, $scope.__codetable.category);

									data.category.codes = undefined;
									codetable.updateCode(data, {
										success : function(ret) {
											if (ret.isSuccess) {
												// 更新原始数据
												for (i in $scope.__codetable.category.codes) {
													var c = $scope.__codetable.category.codes[i];
													if (c.id == ret.object.id) {
														c.value = ret.object.value;
														c.desc = ret.object.desc;
													}
												}

												$scope.__codetable.code = undefined;
												$scope.$apply();
											} else {
												modal.toast({
													title : ret.message
												});
											}
										}
									})
								}
							}
							$scope.__codetable.cancelSaveCode = function() {
								$scope.__codetable.code = undefined;
							}
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
				// 从服务器获取
				codetable.listMine({
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