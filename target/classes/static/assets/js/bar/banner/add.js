/**
 * 新增-bannner管理js
 */
var vm = new Vue({
	el:'#bannerAddVue',
	data: {
		roleList:{},
		banner:{
			image: '',
			roleIdList:[],
			position: 1
		},
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
			var d = vm.banner;
		    $.SaveForm({
		    	url: baseUrl + '/shop/banner/save?_' + $.now(),
		    	param: vm.banner,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	},
	created : function() {
		
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
			$("#imageView").attr('src' , data.data.imageUrl);
			$("#imageView").show();
		}
	});
}
