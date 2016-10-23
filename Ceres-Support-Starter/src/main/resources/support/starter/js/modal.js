define([ 'module', 'jquery' ,'toastr'], function(module, $, toastr) {

	var obj = {
		info:function(msg){
			toastr.info(msg);
		},
		success : function(msg) {
			toastr.success(msg);
		},
		warn: function(msg){
			toastr.warning(msg);
		},
		error: function(msg){
			 toastr.error(msg);
		},
		toast : function(msg) {
			toastr.success(msg);
		},
		alert : function(data) {
			var __opts = {
				text: data,
				showCancelButton: false,
			};
			obj.confirm(__opts);
		},
		confirm:function(options){
			var __default = {
				title: "提示",
				text: "",
				type:"",
				showCancelButton: true,
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				closeOnConfirm: true,
                closeOnCancel: true
			};
			var cur = {
				text:""
			}
			if(typeof options === 'string'){
				cur.text = options;
				cur.closeOnconfirm = true;
				cur.closeOncancel = true;
				cur = $.extend({}, __default, cur);
			}else{
				cur = $.extend({}, __default, cur, options);
			}
			return swal(cur, function(isConfirm){
				if (isConfirm) {
                    options && options.confirm&& options.confirm();
                } else {
                	options && options.cancel&& options.cancel();
                }
			});
		}
	}
	/*
	 * 
	 * swal({
                        title: "Are you sure?",
                        text: "Your will not be able to recover this imaginary file!",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Yes, delete it!",
                        cancelButtonText: "No, cancel plx!",
                        closeOnConfirm: false,
                        closeOnCancel: false },
                    function (isConfirm) {
                        if (isConfirm) {
                            swal("Deleted!", "Your imaginary file has been deleted.", "success");
                        } else {
                            swal("Cancelled", "Your imaginary file is safe :)", "error");
                        }
                    });
	 */

	if (module.config() != undefined) {
		obj = $.extend(true, obj, module.config());
	}

	return obj;
});