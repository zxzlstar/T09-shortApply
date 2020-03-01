/**
 * 编辑-banner管理js
 */
var vm = new Vue({
	el:'#bannerEditVue',
	data: {
		banner:{
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: baseUrl+'/shop/banner/info?_' + $.now(),
		    	param: vm.banner.id,
		    	success: function(data) {
		    		vm.banner = data;
		    		$("#imageView").attr("src",vm.banner.imageUrl);
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: baseUrl+'/shop/banner/modify?_' + $.now(),
		    	param: vm.banner,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})


//图片上传
function uploadImage(){
	$.upload({
		selector: '#imageFile',
		url: baseUrl + '/basic/upload/file?_' + $.now(),
		onMessage:function(msg){
			layer.msg(msg ,2);
		},
		onComplete:function(data){
			vm.banner.image = data.data.file;
			$("#imageView").parent('div').css('display','block');
			$("#imageView").attr('src' , baseUrl + '/basic/upload/images/' + data.data.file);
		}
	});
}