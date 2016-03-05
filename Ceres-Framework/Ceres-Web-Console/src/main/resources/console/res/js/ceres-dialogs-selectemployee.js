consoleApp.directive('selectEmployee', function() {
	return {
		restrict : 'E',
		templateUrl : '/$$ceres_sys/console/base/res/cp?id=console/res/templates/employee/select_employee.html',
		compile : function(element, attrs) {
			var e = $(element);
			e.find("[ceres-save]").attr("ng-click", attrs.ceresEditDepartmentCallback + "($$ceres_value)");

			return {
				pre : function(scope, element, attrs) {

				},
				post : function(scope, element, attrs) {
					var e = $(element);
					e.data("scope", scope);
					e.data("attrs", attrs);
					scope.$$ceres_target = e;
					// 打开窗口
					e.on("open", function(e, id) {
						$c.load({
							url : 'console/department/edit',
							data : {
								id : id
							},
							success : function(ret) {
								scope.$$ceres_value = ret;
								scope.$apply();
								scope.$$ceres_target.modal({
									backdrop : true
								});
							}
						})

					});
				}
			}
		},
	};
});