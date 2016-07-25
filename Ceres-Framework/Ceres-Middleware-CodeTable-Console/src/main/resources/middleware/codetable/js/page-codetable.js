define([ 'app', 'codetable' ], function(app, codetable) {

	app.controller('codetableCtrl', [ '$scope', "$uibModal", function($scope, $modal) {
		$scope.refreshList = function() {
			// 从服务器获取
			codetable.listMine({
				success : function(ret) {
					if (ret.isSuccess) {
						$scope.categorys = ret.object;
						$scope.$apply();
					} else {
						modal.toast({
							title : ret.message
						});
					}
				}
			});
		}

		$scope.editCodeTable = function(category) {
			if (category == undefined) {
				return;
			}
			$scope.category = category;

			var modalInstance = $modal.open({
				templateUrl : "/api/classpath/query/middleware/codetable/tpl/detail.html",
				size : 'lg',
				scope : $scope,
				controller : function($scope, $uibModalInstance) {
					$scope.cancel = function() {
						$uibModalInstance.dismiss('cancel');
					};
					$scope.editCodeTable = function(cate) {
						$scope.category = cate;
					}
					$scope.backToList = function() {
						$scope.category = undefined;
					}
					$scope.removeCode = function(code) {
						if (confirm("删除该选项后将不可恢复，是否继续?")) {
							// 删除前关闭修改对话框
							$scope.cancelSaveCode();

							codetable.removeCode({
								codeId : code.id,
								categoryId : $scope.category.id
							}, {
								success : function(ret) {
									for (i in $scope.category.codes) {
										var c = $scope.category.codes[i];
										if (c.id == code.id) {
											$scope.category.codes.splice(i, 1);
										}
									}
									$scope.$apply();
								}
							});
						}
					}
					$scope.addCode = function() {
						$scope.code = {
							value : "NEWCODE",
							desc : "新建选项"
						};
					}
					$scope.editCode = function(code) {
						$scope.code = {};
						angular.extend($scope.code, code);
					}
					$scope.saveCode = function() {
						if ($scope.code != undefined) {
							var data = {};
							angular.extend(data, $scope.code);

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
							angular.extend(data.category, $scope.category);

							data.category.codes = undefined;
							codetable.updateCode(data, {
								success : function(ret) {
									if (ret.isSuccess) {
										// 更新原始数据
										if (data.id != undefined) {
											// 编辑
											for (i in $scope.category.codes) {
												var c = $scope.category.codes[i];
												if (c.id == ret.object.id) {
													c.value = ret.object.value;
													c.desc = ret.object.desc;
												}
											}
										} else {
											// 新增
											if ($scope.category.codes == undefined) {
												$scope.category.codes = new Array();
											}
											$scope.category.codes.push(ret.object);
										}

										$scope.code = undefined;
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
					$scope.cancelSaveCode = function() {
						$scope.code = undefined;
					}
				}
			});
		}

		$scope.refreshList();
	} ]);

});