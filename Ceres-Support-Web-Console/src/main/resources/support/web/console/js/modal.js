define([ 'jquery', 'sweet-alert' ], function($) {

	return {
		success : function(data) {
			data.type = "success";

			swal({
				title : "Good job!",
				text : "You clicked the button!",
				type : "success"
			});
		},
		alert : function(data) {
			var defaultOptions = {
				confirmButtonText : "好的"
			};

			var options = $.extend(true, defaultOptions, data);
			swal(options, function(isConfirm) {
			});

		}
	}
});