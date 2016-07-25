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
			scope : false,
			templateUrl : '/api/classpath/query/support/web/console/template/queryform.html',
			controller : [ '$scope', '$attrs', function($scope, $attrs) {
				$scope.search = function() {
					var formDef = getFormDefinition($attrs);

					if (formDef.url == undefined || formDef.url == "") {
						throw new Error("数据查询连接url不存在");
						// 暂时不考虑本地数据
						// // 本地数据
						// if (attrs.datamodel != undefined && attrs.datamodel
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
						http.load({
							url : formDef.url,
							data : $scope.terms,
							success : function(ret) {
								if (ret instanceof Array) {
									scope.$$ceresQueryForm.dataOriginal = ret;
								} else {
									scope.$$ceresQueryForm.dataOriginal = ret.data;
									$.extend(scope, ret);
								}

								var toPage = 1;
								if (localStorage && attrs.form) {
									var p = localStorage.getItem("PAGESTATUS_PAGENUMBER_" + attrs.form);
									if (p != undefined && p != "") {
										toPage = parseInt(p);
									}
								}

								scope.$$ceresQueryForm.toPage(toPage);
							},
							complete : function() {
								$(".overlay").addClass("hide");
							}
						});
					}
				}
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
				if (formDef.terms.length > 0) {
					var contTerm = element.find("div[term-box]");
					for ($ln in formDef.terms) {
						var term = formDef.terms[$ln];
						var width = term.width == undefined ? 3 : term.width;
						switch (term.type) {
						case "enum":
							if (term.category == undefined || term.category == "") {
								throw new Error("枚举未指定", term);
							}

							var uuid = "KEY_" + Math.uuid(32);
							term.uuid = uuid;
							str = "<div class='col-md-" + width + "'>";
							str += "<select class='form-control' ng-model='terms." + term.prop + "' ng-options='s.key as s.desc for s in " + uuid + "'>";
							if (term.defaultDesc != undefined && term.defaultDesc != "") {
								str += "<option value=''>" + term.defaultDesc + "</option>";
							}
							str += "</select></div>";
							contTerm.append(str);
							break;
						case "codetable":
							if (term.category == undefined || term.category == "") {
								throw new Error("字典表未指定", term);
							}

							var uuid = "KEY_" + Math.uuid(32);
							term.uuid = uuid;
							str = "<div class='col-md-" + width + "'>";
							str += "<select class='form-control' ng-model='terms." + term.prop + "' ng-options='s.value as s.desc for s in " + uuid + "'>";
							if (term.defaultDesc != undefined && term.defaultDesc != "") {
								str += "<option value=''>" + term.defaultDesc + "</option>";
							}
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
						case "REMOTELIST":
							str = "<div class='col-md-" + width + "'>";
							str += "<select class='form-control' ng-change='$$ceresQueryForm.search()' ng-model='$$ceresQueryForm.terms." + term.key + "' ng-options='s." + term.remoteList.keyName
									+ " as s." + term.remoteList.descName + " for s in " + term.remoteList.listVarName + "'>";
							str += "<option value>" + term.title + "</option></select>";
							str += "</div>";
							contTerm.append(str);
							break;
						default:
							var str = "<div class='col-md-" + width + "'>";
							str += "<div class='form-group'>";
							// str += "<label>"+col.title+"</label>";
							str += "<input type='text' class='form-control' ng-model='terms." + term.prop + "' placeholder='" + term.defaultDesc + "'>";
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
					if (col.key != undefined && col.key != "") {
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

							element.find("tbody > tr").append("<td ng-bind=\"row." + col.key + " | toDate | date:'" + pattern + "'\"></td>");
						case "codetable":

							break;
						case "CURRENCY":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.key + " | currency\"></td>");
							break;
						case "CURRENCYTENK":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.key + " | currencyTenK\"></td>");
							break;
						case "ENUM":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.key + " | enums: '" + col.pattern + "'\"></td>");
							break;
						case "IMAGE":
							element.find("tbody > tr").append("<td><img resfs='{{row." + col.key + "}}' class='user-image img-circle'  width='" + col.width + "' height='" + col.height + "' ></td>");
							break;
						case "IDIN":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.key + " | idInList: " + col.pattern + " : '" + col.extra + "'\"></td>");
							break;
						case "ICON":
							element.find("tbody > tr").append("<td><i class='{{row." + col.key + "}}' ng-if='row." + col.key + "'></i></td>");
							break;
						case "PERCENTAGE":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.key + " | percentage \"></td>");
							break;
						default:
							// 默认TEXT
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.key + " \"></td>");
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
				if (formDef.rowBtns != undefined && formDef.rowBtns.length > 0) {
					e.find("thead > tr").append("<th>操作</th>");
					var td = $("<td/>");
					e.find("tbody > tr").append(td);
					for ($ln in formDef.rowBtns) {
						var btn = formDef.rowBtns[$ln];

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
							str += " ng-click=\"" + btn.action + "\" ";
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

						// // 添加枚举
						// if (formDef.terms.length > 0) {
						// for ($ln in formDef.terms) {
						// var term = formDef.terms[$ln];
						// if (term.type == "ENUM") {
						// scope[term.enumClass] = $c.enums[term.enumClass];
						// } else if (term.type == "REMOTELIST") {
						// (function() {
						// $c.load({
						// url : term.remoteList.remoteUrl,
						// data : {
						// listVarName : term.remoteList.listVarName
						// },
						// context : term,
						// success : function(ret, context) {
						// if (ret.data != undefined) {
						// scope[context.remoteList.listVarName] = ret.data;
						// } else {
						// scope[context.remoteList.listVarName] = ret;
						// }
						//
						// scope.$apply();
						// }
						// });
						// }(this));
						//
						// }
						//
						// }
						// }
						// 初始化默认条件
						if (scope.terms == undefined) {
							scope.terms = {
//								page : {
//									pageNumber : 0,
//									pageSize : 15
//								}
							};
						}
						var formId = attrs.cuiForm;
						var formDef = require(formId)
						// 为Term绑定选项值
						for ($ln in formDef.terms) {
							var term = formDef.terms[$ln];
							switch (term.type) {
							case "enum":
								if (term.uuid != undefined && term.uuid != "") {
									var enums = require("enums");
									scope[term.uuid] = enums.get(term.category).values();
								}
								break;
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
								break;
							case "daterange":
								scope.terms[term.prop] = {
									startDate : new Date(),
									endDate : new Date()
								}
							}
						}

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
					},
					post : function(scope, element, attrs) {
						var e = $(element);

						// 设置默认值

						// for ($ln in scope.$$ceresQueryForm.form.terms) {
						// var term = scope.$$ceresQueryForm.form.terms[$ln];
						// if (term.value != undefined) {
						// scope.$$ceresQueryForm.terms[term.key] = term.value;
						// }
						// }

						// 加载数据

						// $("#" +
						// scope.$$ceresQueryForm.targetId).trigger("search");
					}
				}
			},
		};
	});

});