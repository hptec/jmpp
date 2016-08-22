define([ 'app', 'http', 'angular', 'pages' ], function(app, http, angular, pages) {

//	var combineMenu = function(menu) {
//		var menuStr = "<li cui-id='" + menu.uuid + "'>";
//
//		menuStr += "<a";
//		if (menu.submenus == undefined || menu.submenus.length == 0) {
//			// 非父级菜单，可以点击
//			menuStr += " ng-click='jump(\"" + menu.uri + "\")' ";
//		}
//		menuStr += " style='font-size:14px;'>";
//		menuStr += "<i class='" + menu.icon + "' ></i>";
//		menuStr += "<span class='nav-label'>" + menu.caption + "</span>";
//		if (menu.submenus != undefined && menu.submenus.length > 0) {
//			menuStr += "<span class='fa arrow'></span>";
//		}
//		menuStr += "</a>";
//
//		if (menu.submenus != undefined && menu.submenus.length > 0) {
//			menuStr += "<ul class='nav nav-second-level'>";
//			for (_$2 in menu.submenus) {
//				var strSub = combineMenu(menu.submenus[_$2]);
//				menuStr += strSub;
//			}
//			menuStr += "</ul>";
//		}
//		return menuStr;
//	}

	app.directive('cuiNavigation', function() {
		return {
			restrict : "A",
			templateUrl : '/api/theme/query/views/common/navigation.html',
			scope : false,
			controller : [ "$scope", "$state", function($scope, $state) {
				$scope.jump = function(url) {
//					angular.element("li[cui-id]").removeClass("active");
//					angular.element("li[cui-id='"+url+"']").addClass("active");
					
					
					pages.open({
						url : url
					});
				}
				$scope.refresh = function() {
					http.load({
						url : '/api/console/workbench/config',
						success : function(ret) {
							$scope.menus = ret.menus

							$scope.$apply();
							var menuEle = angular.element("#side-menu");
							menuEle.metisMenu();
						}
					});

				}
				$scope.refresh();
			} ],
			compile : function(element, attrs) {
				return {
					post : function(scope, element, attrs) {
					}
				}
			}
		}
	});

});