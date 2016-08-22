define([ 'app', 'platform', 'cache', 'http', '/api/console/workbench/config' ], function(app, platform, cache, http, config) {

	var combineMenu = function(menu) {
		var menuStr = "<li cui-id='" + menu.uuid + "'>";

		menuStr += "<a";
		if (menu.submenus == undefined || menu.submenus.length == 0) {
			// 非父级菜单，可以点击
			menuStr += " ng-click='jump(\"" + menu.uri + "\")' ";
		}
		menuStr += " style='font-size:14px;'>";
		menuStr += "<i class='" + menu.icon + "' ></i>";
		menuStr += "<span class='nav-label'>" + menu.caption + "</span>";
		if (menu.submenus != undefined && menu.submenus.length > 0) {
			menuStr += "<span class='fa arrow'></span>";
		}
		menuStr += "</a>";

		if (menu.submenus != undefined && menu.submenus.length > 0) {
			menuStr += "<ul class='nav nav-second-level'>";
			for (_$2 in menu.submenus) {
				var strSub = combineMenu(menu.submenus[_$2]);
				menuStr += strSub;
			}
			menuStr += "</ul>";
		}
		return menuStr;
	}

	app.directive('cuiNavigation', function() {
		return {
			restrict : "A",
			templateUrl : '/api/theme/query/views/common/navigation.html',
			controller : [ "$scope", "$state", function($scope, $state) {
				$scope.refresh = function() {
					// 从本地缓存加载配置
					var data = cache.get(key);
					if (data != undefined) {
						$scope.data = data;
					}

					// 刷新服务器刷新导航配置
				}
			} ],
			compile : function(element, attrs) {
				var menuEle = element.find("#side-menu");
				for (_$i in config.menus) {
					var m = config.menus[_$i];
					menuEle.append(combineMenu(m));
				}
				return {
					post : function(scope, element, attrs) {
						element.find("#side-menu").metisMenu();
					}
				}
			}
		}
	});

});