define([ 'module', 'cache', '$', 'http', 'modal' ], function(module, cache, $, http, modal) {

	return {
		get : function(option) {
		},
		/**
		 * Sample:<button class="btn btn-default btn-sm " type="file"
		 * ngf-select="onPicSelect($files,namespace)" accept="image/*"
		 * ngf-max-size="10MB"><i class="fa fa-upload"></i> 上传</button>
		 */
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