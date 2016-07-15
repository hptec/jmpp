define([ 'app', '/api/queryform/query','angular'], function(app, forms,angular) {

	app.directive('queryForm', function() {
		return {
			restrict : 'E',
			templateUrl : '/api/classpath/query/support/web/console/template/queryform.html',
			compile : function(element, attrs) {

				// 给自己分配一个UUID
				var uuid= Math.uuid(32);
				element.attr("cui-uuid",uuid);

				var formId = attrs.cuiForm;
				if (formId == undefined || formId == "") {
					throw new Error("formId is undefined!");
				}
				var formDef = forms[formId];
				if (formDef == undefined) {
					throw new Error("form definition is missing!");
				}
				
				//数据初始化
				
				

				// 添加搜索条件
				if (formDef.terms.length > 0) {
					var contTerm = element.find("query-form-term");
					for ($ln in formDef.terms) {
						var term = formDef.terms[$ln];
						switch (term.type) {
						case "ENUM":
							str = "<div class='col-md-2'>";
							str += "<select class='form-control' ng-change='$$ceresQueryForm.search()' ng-model='$$ceresQueryForm.terms." + term.key + "'  ng-options='s.key as s.desc for  s in "
									+ term.enumClass + "'>";
							str += "<option value>" + term.title + "</option></select>";
							str += "</div>";
							contTerm.append(str);
							break;
						case "DATE":

							break;
						case "daterange":
							str = "<div class='col-md-4'>";
							str += "<div class='form-group'>";
							str += "<div class='input-group'>";
							str += "<div class='input-group-addon'>";
							str += "<i class='fa fa-calendar'></i>"
							str += "</div>";
							str += "<input type='text' readonly date-range-picker ceres-queryform-term-type='DATERANGE' ng-model='$$ceresQueryForm.terms." + term.key + "' class='form-control pull-right date-picker' placeholder='"
									+ term.title + "'>";
							str += "</div>";
							str += "</div>";
							str += "</div>";
							contTerm.append(str);
							break;
						case "REMOTELIST":
							str = "<div class='col-md-2'>";
							str += "<select class='form-control' ng-change='$$ceresQueryForm.search()' ng-model='$$ceresQueryForm.terms." + term.key + "'  ng-options='s." + term.remoteList.keyName
									+ " as s." + term.remoteList.descName + " for  s in " + term.remoteList.listVarName + "'>";
							str += "<option value>" + term.title + "</option></select>";
							str += "</div>";
							contTerm.append(str);
							break;
						default:
							var str = "<div class='col-md-2'>";
							str += "<div class='form-group'>";
							// str += "<label>"+col.title+"</label>";
							str += "<input type='text' class='form-control' ng-model='$$ceresQueryForm.terms." + term.key + "' placeholder='" + term.title + "'>";
							str += "</div>";
							str += "</div>";
							contTerm.append(str);
						}
					}

					var str = "<div class='col-md-2 pull-right'><button class='btn btn-block btn-default' ng-click='$$ceresQueryForm.search()'><i class='fa fa-fw fa-search'></i>搜索</button></div>";
					contTerm.append(str);
				}
				
				
				// 添加列
				for ($ln in formDef.columns) {
					break;
					var col = formDef.columns[$ln];
					// 添加列名
					element.find("thead > tr").append("<th>" + col.title + "</th>");
					// 添加行字段
					if (col.key != undefined && col.key != "") {
						switch (col.type) {
						case "DATE":
							element.find("tbody > tr").append("<td ng-bind=\"row." + col.key + " | toDate | date:'" + col.pattern + "'\"></td>");
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
						//初始化默认参数
						var formId=element.attr("cui-form");
						//保存状态：location.href+formId
						console.log(location.href);
						
						
						
//						// 添加枚举
//						if (formDef.terms.length > 0) {
//							for ($ln in formDef.terms) {
//								var term = formDef.terms[$ln];
//								if (term.type == "ENUM") {
//									scope[term.enumClass] = $c.enums[term.enumClass];
//								} else if (term.type == "REMOTELIST") {
//									(function() {
//										$c.load({
//											url : term.remoteList.remoteUrl,
//											data : {
//												listVarName : term.remoteList.listVarName
//											},
//											context : term,
//											success : function(ret, context) {
//												if (ret.data != undefined) {
//													scope[context.remoteList.listVarName] = ret.data;
//												} else {
//													scope[context.remoteList.listVarName] = ret;
//												}
//
//												scope.$apply();
//											}
//										});
//									}(this));
//
//								}
//
//							}
//						}

						// 初始化默认条件
						if(scope.__queryform==undefined){
							scope.__queryform={};
						}
						scope.__queryform[formId] = {
							targetId :formId,
							form : formDef,
							terms : {},// 搜索条件
							dataOriginal : [],
							page : {
								size : 15,
								cur : 1,
								numbers : [],
								sizes : [ 5, 15, 30, 50, 100 ]
							},
							data : [],
							toPage : function(page) {
								if (this.dataOriginal == undefined || this.dataOriginal.length == undefined) {
									return;
								}
								var maxPage = this.pageTotal();
								if (page > maxPage) {
									page = maxPage;
								}
								if (page <= 0) {
									page = 1;
								}

								var from = this.page.size * (page - 1);
								var to = this.page.size * page;
								this.page.cur = page;
								// 复制数组
								this.data = this.dataOriginal.slice(from, to);
								// 居中页号
								this.page.numbers = [];
								var halfMaxNumberLength = 3;// 左右各多显示3个

								// 计算左右两边的页面个数
								var left = 0, right = 0;
								if (page - halfMaxNumberLength >= 1) {
									left = page - halfMaxNumberLength;
								} else {
									left = 1;
								}

								if (page + halfMaxNumberLength <= maxPage) {
									right = page + halfMaxNumberLength;
								} else {
									right = maxPage;
								}

								var patch = right - left + 1;
								if (patch < 2 * halfMaxNumberLength + 1) {
									// 缺少页码
									var lack = 2 * halfMaxNumberLength + 1 - patch;

									if (right == maxPage) {
										// 右边已满，补足左边
										if (left - lack >= 1) {
											left = left - lack;
										} else {
											left = 1;
										}
									} else {
										// 补足右边
										if (right + lack > maxPage) {
											right = maxPage;
										} else {
											right = right + lack;
										}
									}

								}

								for (var $p = left; $p <= right; $p++) {
									this.page.numbers.push($p);
								}

								if (localStorage && attrs.form) {
									localStorage.setItem("PAGESTATUS_PAGENUMBER_" + attrs.form, page);
								}

								if (scope.$$phase == undefined) {
									scope.$apply();
								}

							},
							pageTotal : function() {
								if (this.dataOriginal == undefined) {
									return 0;
								}
								return Math.ceil(this.dataOriginal.length / this.page.size);
							},
							search : function() {
								$("#" + scope.$$ceresQueryForm.targetId).trigger("search");
							}
						}
					},
					post : function(scope, element, attrs) {
						var e = $(element);

						// 设置默认值

						for ($ln in scope.$$ceresQueryForm.form.terms) {
							var term = scope.$$ceresQueryForm.form.terms[$ln];
							if (term.value != undefined) {
								scope.$$ceresQueryForm.terms[term.key] = term.value;
							}
						}

						// 加载数据
						e.on("search", function(e) {
							if (attrs.dataurl == undefined || attrs.dataurl == "") {
								// 本地数据
								if (attrs.datamodel != undefined && attrs.datamodel != "") {
									scope.$$ceresQueryForm.dataOriginal = $c.fn.val(scope, attrs.datamodel);
									var toPage = 1;
									if (localStorage && attrs.form) {
										var p = localStorage.getItem("PAGESTATUS_PAGENUMBER_" + attrs.form);
										if (p != undefined && p != "") {
											toPage = parseInt(p);
										}
									}

									scope.$$ceresQueryForm.toPage(toPage);

								}
							} else {
								// 远程加载数据
								$(".overlay").removeClass("hide");
								$c.load({
									url : attrs.dataurl,
									data : scope.$$ceresQueryForm.terms,
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
						});

						e.find("input[ceres-queryform-term-type='DATERANGE']").daterangepicker({
							"locale" : {
								"format" : "YYYY-MM-DD",
								"separator" : " - ",
								"applyLabel" : "确定",
								"cancelLabel" : "取消",
								"fromLabel" : "从",
								"toLabel" : "至",
								"customRangeLabel" : "Custom",
								"daysOfWeek" : [ "日", "一", "二", "三", "四", "五", "六" ],
								"monthNames" : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" ],
								"firstDay" : 1
							},
							format : "YYYY-MM-DD"
						}, function(start, end, label) {
						});

						$("#" + scope.$$ceresQueryForm.targetId).trigger("search");
					}
				}
			},
		};
	});


});