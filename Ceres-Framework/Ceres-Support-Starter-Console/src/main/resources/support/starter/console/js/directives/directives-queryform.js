define([ 'app', 'angular', 'md5', 'http' ], function(app, angular, md5, http) {

	/**
	 * 得到窗口的唯一识别标识: md5(窗口连接+form名称)
	 */
	var getFormUid = function(formName) {
		var uri = location.href + formName;
		var uid = md5(uri);
		return uid;
	}

	var getFormDefinition = function(attrs) {
		var formId = attrs.cuiForm;
		if (formId == undefined || formId == "") {
			throw new Error("formId is undefined!");
		}
		var formDef = require(formId)
		if (formDef == undefined) {
			throw new Error("form definition is missing!");
		}
		return formDef;
	}

	app.directive('queryForm', function() {
		return {
			restrict : 'E',
			scope : {
				param : "=cuiParam"
			},
			templateUrl : '/api/classpath/query/support/starter/console/template/queryform.html',
			controller : [ '$scope', '$attrs', function($scope, $attrs) {
				$scope.searching = false;
				$scope.search = function() {
					$scope.searching = true;
//					 $(".splash").show();
					var formDef = getFormDefinition($attrs);

					if (formDef.url == undefined || formDef.url == "") {
						throw new Error("数据查询连接url不存在");
						// 暂时不考虑本地数据
						// // 本地数据
						// if (attrs.datamodel != undefined &Quar&
						// attrs.datamodel
						// != "") {
						// scope.$$ceresQueryForm.dataOriginal =
						// $c.fn.val(scope, attrs.datamodel);
						// var toPage = 1;
						// if (localStorage && attrs.form) {
						// var p = localStorage.getItem("PAGESTATUS_PAGENUMBER_"
						// + attrs.form);
						// if (p != undefined && p != "") {
						// toPage = parseInt(p);
						// }
						// }
						//
						// scope.$$ceresQueryForm.toPage(toPage);
						// }
					} else {
						// 远程加载数据
						// $(".overlay").removeClass("hide");
						var data = cui.extend(true, {}, $scope.param);
						var data = cui.extend(true, data, $scope.terms);

						// 考虑将daterange的数据转换为符合要求的格式。
						for ($ln in formDef.terms) {
							var term = formDef.terms[$ln];
							switch (term.type) {
							case "daterange":
								if (data[term.prop] != undefined) {
									var range = data[term.prop];
									var startDate = range.startDate == undefined ? undefined : new Date(range.startDate);
									var endDate = range.endDate == undefined ? undefined : new Date(range.endDate);
									data[term.prop + ".startDate"] = startDate;
									data[term.prop + ".endDate"] = endDate;
									data[term.prop] = undefined;
								}
							}
						}

						// 组合page参数
						if (data.page != undefined) {
							data["page.pageSize"] = data.page.pageSize;
							data["page.pageNumber"] = data.page.pageNumber - 1;
							if (data["page.pageNumber"] < 0) {
								data["page.pageNumber"] = 0;
							}
							data.page = undefined;
						}

						for ( var i in data) {
							var v = data[i];
							if (cui.isFunction(v)) {
								// 排除掉函数类型的param
								data[i] = undefined;
							}
						}

						http.load({
							url : formDef.url,
							data : data,
							success : function(ret) {
								if (ret.isSuccess != undefined && ret.isSuccess == false) {
									alert(ret.message);
								} else if (ret instanceof Array) {
									$scope.result = ret;
								} else {
									cui.extend($scope, {
										result : ret.object.page.data
									});
									cui.extend(true, $scope, {
										terms : {
											page : ret.object.page
										}
									});

									// 因为服务器使用0作为第一页，uibPagination使用1作为第一页，这里进行匹配
									$scope.terms.page.pageNumber += 1;

									// 如果因为操作的原因，导致页面超出范围，需要显示出那个本不应该存在的页码好让用户能够切换到正确的页码。
									if ($scope.terms.page.numberOfElements == 0) {
										// $scope.search();
										$scope.terms.page.totalPages += 1;
									}
									// cui.log($scope.terms);
									// $scope.result = ret.object.page.data;
									// angular.extend($scope, ret);
								}

								var phase = $scope.$root.$$phase;
								if (phase == '$apply' || phase == '$digest') {

								} else {
									$scope.$apply();
								}

								// var toPage = 1;
								// if (localStorage && attrs.form) {
								// var p =
								// localStorage.getItem("PAGESTATUS_PAGENUMBER_"
								// + attrs.form);
								// if (p != undefined && p != "") {
								// toPage = parseInt(p);
								// }
								// }
								//
								// scope.$$ceresQueryForm.toPage(toPage);
							},
							complete : function() {
								// setTimeout(function() {
								// $(".splash").hide();
								// }, 2000);
								$scope.searching = false;
							}
						});
					}
				}

				$scope.$watch("terms.page.pageNumber", function(n, o) {
					// cui.log("新:" + n + " => 旧:" + o);
					if (!$scope.searching && n != o) {
						$scope.search();
					}
				});
			} ],
			compile : function(element, attrs) {

				var formId = attrs.cuiForm;
				if (formId == undefined || formId == "") {
					throw new Error("formId is undefined!");
				}
				var formDef = require(formId)
				if (formDef == undefined) {
					throw new Error("form definition is missing!");
				}

				// 给自己分配一个UUID
				var uid = getFormUid(formId);

				// 数据初始化

				// 添加搜索条件
				if (formDef.terms != undefined && formDef.terms.length > 0) {
					var contTerm = element.find("div[term-box]");
					for ($ln in formDef.terms) {
						var term = formDef.terms[$ln];
						var width = term.width == undefined ? 3 : term.width;
						switch (term.type) {
						case "enum":
							if (term.category == undefined || term.category == "") {
								throw new Error("枚举未指定", term);
							}

							str = "<div class='col-md-" + width + "'>";
							str += "<select class='form-control' cui-enum=" + term.category + " ng-model='terms." + term.prop + "' cui-desc='" + (term.desc == undefined ? "" : term.desc) + "' >";
							str += "</select></div>";
							contTerm.append(str);
							break;
						case "codetable":
							if (term.category == undefined || term.category == "") {
								throw new Error("字典表未指定", term);
							}

							str = "<div class='col-md-" + width + "'>";
							str += "<select class='form-control' ng-model='terms." + term.prop + "' cui-codetable='" + term.category + "' cui-desc='" + (term.desc == undefined ? "" : term.desc)
									+ "' >";
							str += "</select></div>";
							contTerm.append(str);
							break;
						case "date":
							throw new Error("Date 尚未实现");
							break;
						case "daterange":
							str = "<div class='col-md-" + width + "'>";
							str += "<div class='form-group'>";
							str += "<div class='input-group'>";
							str += "<div class='input-group-addon'>";
							str += "<i class='fa fa-calendar'></i>"
							str += "</div>";
							str += "<input type='text' readonly date-range-picker  ng-model='terms." + term.prop + "' class='form-control pull-right date-picker' placeholder='" + term.title + "'>";
							str += "</div>";
							str += "</div>";
							str += "</div>";
							contTerm.append(str);
							break;
						case "empty":
							str = "<div class='col-md-" + width + "'><div class='form-group'><input type='text' class='form-control' style='visibility:hidden;'></div></div>";
							contTerm.append(str);
							break;
						case "remote":
							var uuid = "KEY_" + Math.uuid(32);
							term.uuid = uuid;

							str = "<div class='col-md-" + width + "'>";
							str += "<select class='form-control' cui-uuid='" + uuid + "' ng-model='terms." + term.prop + "' ng-options='s.id as s.desc for s in " + uuid + "'>";
							str += "<option value>" + term.desc + "</option></select>";
							str += "</div>";
							contTerm.append(str);
							break;
						default:
							var str = "<div class='col-md-" + width + "'>";
							str += "<div class='form-group'>";
							// str += "<label>"+col.title+"</label>";
							str += "<input type='text' class='form-control' ng-model='terms." + term.prop + "' placeholder='" + term.desc + "'>";
							str += "</div>";
							str += "</div>";
							contTerm.append(str);
						}
					}

					// var str = "<div class='col-md-2 pull-right'><button
					// class='btn btn-block btn-default'
					// ng-click='$$ceresQueryForm.search()'><i class='fa fa-fw
					// fa-search'></i>搜索</button></div>";
					// contTerm.append(str);
				}

				// 添加列
				for ($ln in formDef.columns) {
					var col = formDef.columns[$ln];
					// 添加列名
					element.find("thead > tr").append("<th>" + col.title + "</th>");
					// 添加行字段
					if (col.prop != undefined && col.prop != "") {
						switch (col.type) {
						case "date":
						case "datetime":
						case "time":
							var pattern = "yyyy-MM-dd";
							if (col.type == "datetime") {
								pattern = "yyyy-MM-dd HH:mm:ss";
							} else if (col.type == "time") {
								pattern = "HH:mm:ss";
							}

							element.find("tbody > tr").append("<td ng-bind=\"row." + col.prop + " | toDate | date:'" + pattern + "'\"></td>");
							break;
						case "codetable":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.prop + " | codetable:'" + col.category + "'\"></td>");
							break;
						case "CURRENCY":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.prop + " | currency\"></td>");
							break;
						case "CURRENCYTENK":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.prop + " | currencyTenK\"></td>");
							break;
						case "enum":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.prop + " | enums: '" + col.category + "'\"></td>");
							break;
						case "IMAGE":
							element.find("tbody > tr").append("<td><img resfs='{{row." + col.prop + "}}' class='user-image img-circle'  width='" + col.width + "' height='" + col.height + "' ></td>");
							break;
						case "IDIN":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.prop + " | idInList: " + col.pattern + " : '" + col.extra + "'\"></td>");
							break;
						case "ICON":
							element.find("tbody > tr").append("<td><i class='{{row." + col.prop + "}}' ng-if='row." + col.prop + "'></i></td>");
							break;
						case "PERCENTAGE":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.prop + " | percentage \"></td>");
							break;
						default:
							// 默认TEXT
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.prop + " \"></td>");
						}

					} else {
						element.find("tbody > tr").append("<td>&nbsp;</td>");
					}
				}

				// 添加顶部工具按钮
				if (formDef.toolBtns != undefined && formDef.toolBtns.length > 0) {
					for ($ln in formDef.toolBtns) {
						var btn = formDef.toolBtns[$ln];
						var tag = "button";
						if (btn.href != undefined && btn.href != "") {
							// 如果设置了href，就使用a标签
							tag = "a";
						}

						var str = "<" + tag + " class=\"btn btn-default btn-sm\" ";
						if (btn.action != undefined && btn.action != "") {
							str += " ng-click=\"" + btn.action + "\" ";
						}
						if (btn.href != undefined && btn.href != "") {
							str += " ng-href=\"" + btn.href + "\" ";
						}
						if (btn.comment != undefined && btn.comment != "") {
							str += " title=\"" + btn.comment + "\" ";
						}
						if (btn.disableIf != undefined && btn.disableIf != "") {
							str += " ng-disabled=\"" + btn.disableIf + "\" ";
						}

						str += ">";
						if (btn.icon != undefined && btn.icon != "") {
							str += "<i class=\"" + btn.icon + "\"></i>";
						}
						if (btn.title != undefined && btn.title != "") {
							str += btn.title;
						}
						str += "</" + tag + ">";
						e.find("div.box-tools").append(str);
					}
				}

				// 添加行级按钮
				if (formDef.rowButtons != undefined && formDef.rowButtons.length > 0) {
					element.find("thead > tr").append("<th>操作</th>");
					var td = $("<td/>");
					element.find("tbody > tr").append(td);
					for ($ln in formDef.rowButtons) {
						var btn = formDef.rowButtons[$ln];

						var tag = "button";
						if (btn.href != undefined && btn.href != "") {
							// 如果设置了href，就使用a标签
							tag = "a";
						}

						var str = "<" + tag + " ";
						if (btn.href != undefined && btn.href != "") {
							str += " ng-href=\"" + btn.href + "\"";
						}
						if (btn.action != undefined && btn.action != "") {
							str += " ng-click=\"param." + btn.action + "\" ";
						}
						if (btn.comment != undefined && btn.comment != "") {
							str += " title=\"" + btn.comment + "\" ";
						}
						if (btn.disableIf != undefined && btn.disableIf != "") {
							str += " ng-disabled=\"" + btn.disableIf + "\" ";
						}
						str += "class=\"btn btn-default btn-xs\" >";
						if (btn.icon != undefined && btn.icon != "") {
							str += "<i class=\"" + btn.icon + "\"></i>";
						}
						if (btn.title != undefined && btn.title != "") {
							str += btn.title;
						}
						str += "</" + tag + ">";
						td.append(str);
					}
				}

				return {
					pre : function(scope, element, attrs) {
						// 初始化默认参数
						var formId = element.attr("cui-form");

						// 保存状态：location.href+formId

						// scope.__queryform[formId] = {
						// targetId : formId,
						// form : formDef,
						// terms : {},// 搜索条件
						// dataOriginal : [],
						// page : {
						// size : 15,
						// cur : 1,
						// numbers : [],
						// sizes : [ 5, 15, 30, 50, 100 ]
						// },
						// data : [],
						// }

						// 初始化默认条件
						if (scope.terms == undefined) {
							scope.terms = {
								page : {
									pageNumber : 0,
									pageSize : 15,
									sizeArray : [ 5, 15, 30, 50, 100 ]
								}
							};
						}
						var formId = attrs.cuiForm;
						var formDef = require(formId)
						// 为Term绑定选项值
						for ($ln in formDef.terms) {
							var term = formDef.terms[$ln];
							switch (term.type) {
							case "codetable":
								if (term.uuid != undefined && term.uuid != "") {
									var codetable = require("codetable-data");
									for ($ct in codetable) {
										var ct = codetable[$ct];
										if (ct.category == term.category) {
											scope[term.uuid] = ct.codes;
										}
									}
								}
								if (term.value != undefined) {
									if (cui.isFunction(term.value)) {
										scope.terms[term.prop] = term.value();
									} else {
										scope.terms[term.prop] = term.value;
									}
								}

								break;
							case "daterange":
								var v = term.value;
								if (cui.isFunction(v)) {
									scope.terms[term.prop] = v();
								} else if (v != undefined) {
									scope.terms[term.prop] = v;
								} else {
									scope.terms[term.prop] = {
										startDate : new Date(),
										endDate : new Date()
									}
								}
								break
							case "remote":

								http.load({
									url : term.url,
									data : {
										uuid : term.uuid
									},
									success : function(ret) {
										scope[this.data.uuid] = ret;
										scope.$apply();
									}
								});
								if (term.value != undefined) {
									if (cui.isFunction(term.value)) {
										scope.terms[term.prop] = term.value();
									} else {
										scope.terms[term.prop] = term.value;
									}
								}

								break;
							default:
								if (term.value != undefined) {
									if (cui.isFunction(term.value)) {
										scope.terms[term.prop] = term.value();
									} else {
										scope.terms[term.prop] = term.value;
									}
								}

							}
						}

						// 执行配置管理
						scope.config = {};
						if (formDef.config != undefined) {
							// 是否显示搜索按钮
							if (formDef.config.search != undefined) {
								scope.config.search = formDef.config.search;
							}
							// 表格样式
							var tableClass = "table";
							if (formDef.config.style != undefined) {
								if (formDef.config.style.condensed && formDef.config.style.condensed == true) {
									tableClass += " table-condensed";
								}
								if (formDef.config.style.striped && formDef.config.style.striped == true) {
									tableClass += " table-striped";
								}
								if (formDef.config.style.responsive == undefined || formDef.config.style.responsive == true) {
									tableClass += " table-responsive";
								}
								if (formDef.config.style.bordered == undefined && formDef.config.style.bordered == true) {
									tableClass += " table-bordered";
								}
							}
							scope.config.tableClass = tableClass;

							// Page设置
							if (formDef.config.page != null) {
								cui.extend(true, scope.terms.page, formDef.config.page);
							}

						}

						scope.def = formDef;
					},
					post : function(scope, element, attrs) {
						var auto = attrs.cuiAuto == undefined || attrs.cuiAuto == "true" ? true : false;
						if (auto) {
							scope.search();
						}
					}
				}
			},
		};
	});

});