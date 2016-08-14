define([], function() {
	return {
		url : "/api/employee/search",
		config : {
			style : {
				bordered : true,
				striped : true
			}
		},
		columns : [ {
			title : "登录名",
			prop : "login.id",
			type : "text"
		}, {
			title : "姓名",
			prop : "name",
			type : "text"
		}, {
			title : "性别",
			prop : "gender",
			type : "enum",
			category : "cn_cerestech_framework_core_enums_Gender"
		}, {
			title : "冻结状态",
			prop : "login.frozen",
			type : "enum",
			category : "cn_cerestech_framework_core_enums_YesNo"
		}, {
			title : "联系电话",
			prop : "phone.number",
			type : "text"
		}, {
			title : "电子邮箱",
			prop : "email",
			type : "text"
		} ],
		terms : [ {
			prop : "gender",
			type : "enum",
			category : "cn_cerestech_framework_core_enums_Gender",
			desc : "- 请选择性别 -",
			width : 3
		}, {
			prop : "frozen",
			type : "enum",
			width : 3,
			category : "cn_cerestech_framework_core_enums_YesNo",
			desc : "- 是否冻结 -",
		}, {
			prop : "keyword",
			type : "text",
			width : 6,
			desc : "根据姓名、联系手机、电子邮箱搜索"
		}, ],
		rowButtons : [ {
			icon : "fa fa-fw fa-edit",
			action : "editEmployee(row)"
		} ]

	}

});