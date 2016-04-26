define([ 'module', '$' ], function(module, $) {

	var obj = {
		success : function(data) {
		},
		alert : function(data) {
		}
	}

	if (module.config() != undefined) {
		obj = $.extend(true, obj, module.config());
	}

	return obj;
});