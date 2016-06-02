define([ 'module', '$' ], function(module, $) {

	var obj = {
		success : function(data) {
		},
		alert : function(data) {
		},
		toast : function() {

		}
	}

	if (module.config() != undefined) {
		obj = $.extend(true, obj, module.config());
	}

	return obj;
});