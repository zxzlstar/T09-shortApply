$.StickForm = function(options) {
	var defaults = {
		msg : "注：您确定要置顶吗？",
		url : "",
		param : [],
		type : "post",
		dataType : "json",
		contentType : 'application/json',
		success : null
	};
	var options = $.extend(defaults, options);
	dialogConfirm(options.msg, function() {
		dialogLoading(true);
		window.setTimeout(function() {
			var postdata = options.param;
			$.ajax({
				url : options.url,
				data : JSON.stringify(postdata),
				type : options.type,
				dataType : options.dataType,
				contentType : options.contentType,
				success : function(data) {
					if (data.code == '400') {
						dialogAlert(data.message, 'error');
					} else if (data.code == '200') {
						dialogMsg(data.message||'商品置顶成功', 'success');
						options.success(data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					dialogLoading(false);
					dialogMsg(errorThrown, 'error');
				},
				beforeSend : function() {
					dialogLoading(true);
				},
				complete : function() {
					dialogLoading(false);
				}
			});
		}, 500);
	});
}

$.CcelStickForm = function(options) {
	var defaults = {
		msg : "注：您确定要取消置顶吗？",
		url : "",
		param : [],
		type : "post",
		dataType : "json",
		contentType : 'application/json',
		success : null
	};
	var options = $.extend(defaults, options);
	dialogConfirm(options.msg, function() {
		dialogLoading(true);
		window.setTimeout(function() {
			var postdata = options.param;
			$.ajax({
				url : options.url,
				data : JSON.stringify(postdata),
				type : options.type,
				dataType : options.dataType,
				contentType : options.contentType,
				success : function(data) {
					if (data.code == '400') {
						dialogAlert(data.message, 'error');
					} else if (data.code == '200') {
						dialogMsg(data.message||'商品取消置顶成功', 'success');
						options.success(data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					dialogLoading(false);
					dialogMsg(errorThrown, 'error');
				},
				beforeSend : function() {
					dialogLoading(true);
				},
				complete : function() {
					dialogLoading(false);
				}
			});
		}, 500);
	});
}

$.RecommendForm = function(options) {
	var defaults = {
		msg : "注：您确定要优先推荐吗？",
		url : "",
		param : [],
		type : "post",
		dataType : "json",
		contentType : 'application/json',
		success : null
	};
	var options = $.extend(defaults, options);
	dialogConfirm(options.msg, function() {
		dialogLoading(true);
		window.setTimeout(function() {
			var postdata = options.param;
			$.ajax({
				url : options.url,
				data : JSON.stringify(postdata),
				type : options.type,
				dataType : options.dataType,
				contentType : options.contentType,
				success : function(data) {
					if (data.code == '400') {
						dialogAlert(data.message, 'error');
					} else if (data.code == '200') {
						dialogMsg(data.message||'商品推荐成功', 'success');
						options.success(data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					dialogLoading(false);
					dialogMsg(errorThrown, 'error');
				},
				beforeSend : function() {
					dialogLoading(true);
				},
				complete : function() {
					dialogLoading(false);
				}
			});
		}, 500);
	});
}

$.CcelRecommendForm = function(options) {
	var defaults = {
		msg : "注：您确定要取消推荐吗？",
		url : "",
		param : [],
		type : "post",
		dataType : "json",
		contentType : 'application/json',
		success : null
	};
	var options = $.extend(defaults, options);
	dialogConfirm(options.msg, function() {
		dialogLoading(true);
		window.setTimeout(function() {
			var postdata = options.param;
			$.ajax({
				url : options.url,
				data : JSON.stringify(postdata),
				type : options.type,
				dataType : options.dataType,
				contentType : options.contentType,
				success : function(data) {
					if (data.code == '400') {
						dialogAlert(data.message, 'error');
					} else if (data.code == '200') {
						dialogMsg(data.message||'商品取消推荐成功', 'success');
						options.success(data);
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					dialogLoading(false);
					dialogMsg(errorThrown, 'error');
				},
				beforeSend : function() {
					dialogLoading(true);
				},
				complete : function() {
					dialogLoading(false);
				}
			});
		}, 500);
	});
}