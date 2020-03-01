/**
 * 文件上传
 */
(function($) {
	var noop = function(){ return true; };
	var frameCount = 0;
	$.uploadDefault = {
		url: '',
		//fileElementId: 'file' ,
		selector:"#file",
		dataType: 'json',
		params: {},
		extensions:undefined, //文件扩展名,数组：['.jpg','.png','.gif']
		onMessage:noop,  //提示消息
		onSend: noop,
		onSubmit: noop,
		onComplete: noop
	};

	$.upload = function(options) {
		var opts = $.extend({},jQuery.uploadDefault, options);
		if (opts.url == '') {
			opts.onMessage("url不能为空") ;
			return ;
		}
		var canSend = opts.onSend();
		if (!canSend) {
			return;
		}
		
		if(opts.extensions !== undefined){
			var filepath = $(opts.selector).val();
			var extStart=filepath.lastIndexOf(".");
			var flag = false ;
			var ext = null ;
			if(extStart > 0){
				ext =filepath.substring(extStart,filepath.length).toLowerCase();
				if($.inArray(ext , opts.extensions) > -1){
					flag = true ;
				}
			}
			
			if(!flag){
				$(opts.selector).val("") ;
				opts.onMessage("请选择"+opts.extensions.join(",")+"类型文件上传") ;
				return ;
			}
			
		}
		
		var frameName = 'upload_frame_' + (frameCount++);
		var iframe = $('<iframe style="position:absolute;top:-9999px" />').attr('name', frameName);
		var form = $('<form method="post" style="display:none;" enctype="multipart/form-data" />').attr('name', 'form_' + frameName);
		form.attr("target", frameName).attr('action', opts.url);
		
//		var oldElement = $('#'+opts.fileElementId);
		var oldElement = $(opts.selector);
		var newElement = $(oldElement).clone(true);
		oldElement.attr('id' ,'file_'+frameName ) ;
		oldElement.before(newElement);
		
		var formHtml = "";
		for (key in opts.params) {
			formHtml += '<input type="hidden" name="' + key + '" value="' + opts.params[key] + '">';
		}
		form.append(oldElement).append(formHtml);
		
		iframe.appendTo("body");
		form.appendTo("body");
		
		form.submit(opts.onSubmit);
		
		// iframe 在提交完成之后
		iframe.load(function() {
			var contents = $(this).contents().get(0);
			var data = $(contents).find('body').text();
			if ('json' == opts.dataType) {
				data = window.eval('(' + data + ')');
			}
			opts.onComplete(data);
			setTimeout(function() {
				iframe.remove();
				form.remove();
			}, 5000);
		});
		
		form.submit();
	};
	
})(jQuery);
