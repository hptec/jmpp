consoleApp.directive('ceresSelectMenuCallback', function() {
	return {
		restrict : 'A',
		templateUrl : '/$$ceres_sys/console/base/res/cp?id=console/res/templates/enterprize/authorize/select_menu.html',
		replace : true,
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
							url : 'console/enterprize/authorize/menu/select',
							data : {
								id : id
							},
							success : function(ret) {
								var $$ceres_listform = {
									fulldata : ret.menus,
									page : {
										size : 15,
										curPage : 1
									}
								}

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