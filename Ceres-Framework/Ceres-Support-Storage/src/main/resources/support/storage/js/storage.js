define([ 'module', 'cache', 'http', 'modal' ], function(module, cache, http, modal) {

	return {
		get : function(option) {
		},
		upload : function(Upload, files, opt) {
			opt = opt == undefined ? {} : opt;
			if (files && files.length) {
				var up = Upload.upload({
					url : '/api/storage/upload',
					data : {
						file : files
					}
				});
				up.then(opt.success, opt.error, opt.progress);
			}
		}
	}

});